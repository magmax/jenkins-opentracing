package org.magmax.jenkins.opentracing.object;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.tasks.BuildStep;
import io.jaegertracing.internal.JaegerSpan;
import jenkins.YesNoMaybe;
import org.magmax.jenkins.opentracing.IdMap;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuilderStep extends BuildStepListener {


    private JaegerSpan span;

    @java.lang.Override
    public void started(AbstractBuild abstractBuild, BuildStep buildStep, BuildListener buildListener) {
        IdMap idmap = new IdMap();
        span = idmap.getSpan(abstractBuild.getQueueId(), "Step");
        System.out.println("***** Thread: " + Thread.currentThread().getId());
        System.out.println("** STEP **: " + abstractBuild.getProject().getBuildStatusUrl());
    }

    @java.lang.Override
    public void finished(AbstractBuild abstractBuild, BuildStep buildStep, BuildListener buildListener, boolean b) {
        if (span != null) {
            span.finish();
        }
        System.out.println("***** Thread: " + Thread.currentThread().getId());
        System.out.println("** STEP **: " + abstractBuild.getProject().getBuildStatusUrl());
    }
}
