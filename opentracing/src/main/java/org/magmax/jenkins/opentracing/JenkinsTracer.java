package org.magmax.jenkins.opentracing;

import java.util.LinkedList;
import java.util.List;

import io.jaegertracing.Configuration;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.Tracer.SpanBuilder;

class JenkinsTracer implements ILRUItem {
    private List<Tracer> tracers;
    private LinkedList<Span> spanLifo = new LinkedList<>();

    /*
    public JenkinsTracer() {
        createSpan("Build");
    }

    @Override
    public void finish() {
        while (spanLifo.size() > 0)
            closeActiveSpan();

		for (Tracer tracer: tracers) {
            tracer.close();
        }
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
    */
}