package org.magmax.jenkins.opentracing.object;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.BuildStepListener;
import hudson.tasks.BuildStep;
import io.opentracing.Span;
import jenkins.YesNoMaybe;
import org.magmax.jenkins.opentracing.IdMap;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class BuilderStep extends BuildStepListener {

    @Override
    public void started(AbstractBuild build, BuildStep bs, BuildListener listener) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finished(AbstractBuild build, BuildStep bs, BuildListener listener, boolean canContinue) {
        // TODO Auto-generated method stub

    }
/*
    private Span span;

    @java.lang.Override
    public void started(AbstractBuild build, BuildStep step, BuildListener buildListener) {
        IdMap idmap = new IdMap(build.getQueueId());
        span = idmap.getSpan("Step");
        span.setTag("type", step.getClass().getName());
    }

    @java.lang.Override
    public void finished(AbstractBuild build, BuildStep step, BuildListener buildListener, boolean canContinue) {
        IdMap idmap = new IdMap(build.getQueueId());
        span.setTag("canContinue", canContinue);
        if (!canContinue) {
            span.setTag("error", "cannot continue");
        }
        idmap.closeActiveSpan();
    }
    */
}
