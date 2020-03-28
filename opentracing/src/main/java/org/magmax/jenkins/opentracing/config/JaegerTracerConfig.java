package org.magmax.jenkins.opentracing.config;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;

public final class JaegerTracerConfig extends AbstractTracerConfig {

    @Extension
    public static class DescriptorImpl extends hudson.model.Descriptor<AbstractTracerConfig> {
        @Override
        public String getDisplayName() {
            return "Jaeger Tracer";
        }
    }

    private String serviceName = "Jenkins";
    private String host = "localhost";
    private Integer port = 5775;

    @DataBoundConstructor
    public JaegerTracerConfig(String serviceName, String host, Integer port) {
        this.serviceName = serviceName;
        this.host = host;
        this.port = port;
    }

    public String serviceName() {
        return serviceName;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public JaegerTracer getTracer() {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = io.jaegertracing.Configuration.SamplerConfiguration
                .fromEnv().withType(ConstSampler.TYPE).withParam(1);
        io.jaegertracing.Configuration.SenderConfiguration senderConfig = io.jaegertracing.Configuration.SenderConfiguration
                .fromEnv().withAgentHost(host).withAgentPort(port);
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration
                .fromEnv().withSender(senderConfig).withLogSpans(true);
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(serviceName)
                .withSampler(samplerConfig).withReporter(reporterConfig);

        return config.getTracer();
    }
}