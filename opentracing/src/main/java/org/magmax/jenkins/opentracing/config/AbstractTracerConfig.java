package org.magmax.jenkins.opentracing.config;

import hudson.model.AbstractDescribableImpl;
import io.opentracing.Tracer;

public abstract class AbstractTracerConfig extends AbstractDescribableImpl<AbstractTracerConfig> {
    public abstract Tracer getTracer();
}