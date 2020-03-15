package org.magmax.jenkins.opentracing.object;

import org.magmax.jenkins.opentracing.IdMap;

import hudson.model.queue.QueueListener;
import io.jaegertracing.Configuration;
import io.jaegertracing.internal.JaegerSpan;
import io.jaegertracing.internal.JaegerTracer;
import io.jaegertracing.internal.samplers.ConstSampler;
import io.opentracing.Scope;

public class Queue extends QueueListener {
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

    private JaegerSpan span;
    private JaegerSpan blockedSpan;


    @Override
    public void onEnterWaiting(hudson.model.Queue.WaitingItem wi) {
        IdMap idmap = new IdMap();
        span = idmap.getSpan(wi, "Step");
    }

    @Override
    public void onLeaveWaiting(hudson.model.Queue.WaitingItem wi) {
        super.onLeaveWaiting(wi);
        if (span != null) {
            span.finish();
        }
        
    }

    @Override
    public void onEnterBlocked(hudson.model.Queue.BlockedItem bi) {
        IdMap idmap = new IdMap();
        //blockedSpan = idmap.getSpan(abstractBuild, "Blocked");
    }

    @Override
    public void onLeaveBlocked(hudson.model.Queue.BlockedItem bi) {
        super.onLeaveBlocked(bi);
        //blockedSpan.finish();
    }

    @Override
    public void onEnterBuildable(hudson.model.Queue.BuildableItem bi) {
        super.onEnterBuildable(bi);
    }

    @Override
    public void onLeaveBuildable(hudson.model.Queue.BuildableItem bi) {
        super.onLeaveBuildable(bi);
        
    }

    @Override
    public void onLeft(hudson.model.Queue.LeftItem li) {
        super.onLeft(li);
    }
}
