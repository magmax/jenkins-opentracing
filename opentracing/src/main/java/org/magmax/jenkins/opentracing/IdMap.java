package org.magmax.jenkins.opentracing;

import hudson.model.AbstractBuild;
import hudson.model.Queue.BlockedItem;
import hudson.model.Queue.WaitingItem;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerSpanContext;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;

import java.util.HashMap;


public class IdMap {

    private static int MAX_SIZE = 1000;
   
    Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
            .withType(ConstSampler.TYPE)
            .withParam(1);

    Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv()
            .withAgentHost("localhost")
            .withAgentPort(5775);

    Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
            .withSender(senderConfig)
            .withLogSpans(true);

    Configuration config = new Configuration("Jenkins")
            .withSampler(samplerConfig)
            .withReporter(reporterConfig);

    JaegerTracer tracer = config.getTracer();

    private Object get(AbstractBuild build) {
        // get for current build
        String id = build.getExternalizableId();
        LRUCache cache = LRUCache.getInstance();
        Object result = cache.get(id);
        if (result != null) {
            return result;
        }
        // get for the parent build
        id = build.getRootBuild().getExternalizableId();
        result = cache.get(id);
        if (result != null) {
            return result;
        }
        // get for any related build
        /*
        AbstractBuild parent = build.getPreviousBuild();
        while (parent != null) {
            id = parent.getExternalizableId();
            result = cache.get(id);
            if (result != null) {
                return result;
            }
            parent = parent.getPreviousBuild();
        }
        */
        // a new identity is required
        return null;
    }

    private void set(AbstractBuild build, Object value) {
        LRUCache.getInstance().put(build.getExternalizableId(), value);
    }

    private JaegerSpan getSpan(HashMap<String, String> carrier, String name) {
        JaegerTracer.SpanBuilder spanBuilder = tracer.buildSpan(name);

        if (carrier != null) {
            JaegerSpanContext context = tracer.extract(Format.Builtin.TEXT_MAP, new TextMapAdapter(carrier));
            spanBuilder.asChildOf(context);
        }

        JaegerSpan span = spanBuilder.start();

        if (carrier != null) {
            tracer.inject(span.context(), Format.Builtin.TEXT_MAP, new TextMapAdapter(carrier));
        }
        return span;
    }

    public JaegerSpan getSpan(AbstractBuild abstractBuild, String name) {
        HashMap<String, String> carrier = (HashMap<String, String>) get(abstractBuild);

        JaegerSpan span = getSpan(carrier, name);
        if (carrier != null) {
            set(abstractBuild, carrier);
        }

        return span;
    }    

	public JaegerSpan getSpan(WaitingItem wi, String name) {
        HashMap<String, String> carrier = (HashMap<String, String>) get(wi);

        JaegerSpan span = getSpan(carrier, name);
        if (carrier != null) {
            set(wi, carrier);
        }

        return span;
	}

	public JaegerSpan getSpan(BlockedItem bi, String name) {
        HashMap<String, String> carrier = (HashMap<String, String>) get(bi);
        JaegerSpan span = getSpan(carrier, name);
        if (carrier != null) {
            set(bi, carrier);
        }
        return span;
    }

}
