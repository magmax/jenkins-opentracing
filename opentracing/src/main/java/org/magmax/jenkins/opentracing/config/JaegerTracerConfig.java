package org.magmax.jenkins.opentracing.config;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;

public final class JaegerTracerConfig extends AbstractTracerConfig {

    @Extension
    public static class DescriptorImpl extends hudson.model.Descriptor<AbstractTracerConfig> {
        @Override
        public String getDisplayName() {
            return "Jaeger Tracer";
        }
    }

    private String host = "localhost";
    private String port = "5775";

    @DataBoundConstructor
    public JaegerTracerConfig(String host, String port) {
        System.out.println(">>>>>>  JaegerTracerConfig constructor");
        this.host = host;
    }

    public String getHost() {
        System.out.println(">>>>>>  JaegerTracerConfig getHost: " + host);

        return host;
    }

    public String getPort() {
        System.out.println(">>>>>>  JaegerTracerConfig getPort: " + port);

        return port;
    }



/*
    public void sethost(String host) {
        System.out.println(">>>>>>  JaegerTracerConfig setHost");

        this.host = host;
    }
*/
}