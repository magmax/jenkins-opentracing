package org.magmax.jenkins.opentracing;

import java.util.LinkedList;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.Tracer.SpanBuilder;

class JenkinsTracer implements ILRUItem {
    private Tracer tracer;
    private LinkedList<Span> spanLifo = new LinkedList<>();

    public JenkinsTracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
                .withType(ConstSampler.TYPE).withParam(1);
        Configuration.SenderConfiguration senderConfig = Configuration.SenderConfiguration.fromEnv()
                .withAgentHost("localhost").withAgentPort(5775);
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
                .withSender(senderConfig).withLogSpans(true);
        Configuration config = new Configuration("Jenkins").withSampler(samplerConfig).withReporter(reporterConfig);

        tracer = config.getTracer();
        createSpan("Build");
    }

    public JenkinsTracer(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void finish() {
        while (spanLifo.size() > 0)
            closeActiveSpan();
        tracer.close();
    }

    @Override
    public Span createSpan(String name) {
        SpanBuilder builder = tracer.buildSpan(name);
        Span span = builder.start();
        spanLifo.addFirst(span);
        tracer.activateSpan(span);
        return span;
    }

    @Override
    public void closeActiveSpan() {
        Span span = spanLifo.removeFirst();

        if (spanLifo.size() > 0 ) {
            tracer.activateSpan(spanLifo.getFirst());
        }
        span.finish();
    }
}