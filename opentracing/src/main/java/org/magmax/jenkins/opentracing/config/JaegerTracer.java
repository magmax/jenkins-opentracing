package org.magmax.jenkins.opentracing.config;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;

public final class JaegerTracer extends Tracer {

    @Extension
    public static class JaegerTracerDescriptorImpl extends hudson.model.Descriptor<Tracer> {
        @Override
        public String getDisplayName() {
            return "Jaeger Tracer";
        }
    }

    private String host = "localhost";
    private String port = "5775";

/*
<!--
    <f:entry title="${%Tracers}">
      <f:repeatableHeteroProperty field="tracers" hasHeader="true"/>
    </f:entry>
-->
*/

    @DataBoundConstructor
    public JaegerTracer(String host, String port) {
        System.out.println(">>>>>>  JaegerTracerConfig constructor");
        this.host = host;
        this.port = port;
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