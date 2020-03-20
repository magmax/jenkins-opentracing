package org.magmax.jenkins.opentracing.config;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;

public final class JaegerTracerConfig extends AbstractTracerConfig {

    private final String host;

    @DataBoundConstructor
    public JaegerTracerConfig(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    @Extension
    public static class DescriptorImpl extends hudson.model.Descriptor<AbstractTracerConfig> {
        @Override
        public String getDisplayName() {
            return "Jaeger Tracer";
        }
    }
}