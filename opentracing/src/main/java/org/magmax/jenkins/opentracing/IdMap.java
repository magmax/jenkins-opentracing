package org.magmax.jenkins.opentracing;

import hudson.model.AbstractBuild;
import hudson.model.Queue.BlockedItem;
import hudson.model.Queue.BuildableItem;
import hudson.model.Queue.WaitingItem;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerSpanContext;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Scope;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapAdapter;

import java.util.HashMap;

public class IdMap {

    private static int MAX_SIZE = 1000;

    Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
            .withType(ConstSampler.TYPE).withParam(1);

    Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv()
            .withAgentHost("localhost").withAgentPort(5775);

    Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
            .withSender(senderConfig).withLogSpans(true);

    Configuration config = new Configuration("Jenkins").withSampler(samplerConfig).withReporter(reporterConfig);

    JaegerTracer tracer = config.getTracer();

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

    public Scope getSpan(long id, String name) {
        String myId = "" + Thread.currentThread().getId();
        LRUCache cache = LRUCache.getInstance();
        JaegerTracer mytracer = (JaegerTracer) cache.get(myId);

        if (mytracer == null) {
            mytracer = config.getTracer();
            LRUCache.getInstance().put(myId, tracer);
        }
        Scope scope = mytracer.buildSpan(name).startActive(true);

        mytracer.buildSpan("FOO").startActive(true).close();
        return scope;
	}

	public JaegerSpan getSpanOld(long id, String name) {
        String myId = "" + id;
        LRUCache cache = LRUCache.getInstance();
        HashMap<String, String> carrier = (HashMap<String, String>) cache.get(myId);

        JaegerSpan span = getSpan(carrier, name);
        if (carrier != null) {
            LRUCache.getInstance().put(myId, carrier);
        }

        return span;
	}
}
