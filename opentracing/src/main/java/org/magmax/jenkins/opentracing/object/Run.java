package org.magmax.jenkins.opentracing.object;

import jenkins.YesNoMaybe;

import java.io.IOException;

import org.magmax.jenkins.opentracing.IdMap;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Environment;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import io.opentracing.Span;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class Run extends RunListener<hudson.model.Run<?, ?>> {

    private Span span;

    public Run() {
    }

    public Run(Class<hudson.model.Run<?, ?>> targetType) {
        super(targetType);
    }

    @Override
    public void onStarted(hudson.model.Run<?, ?> run, TaskListener listener) {
        IdMap idmap = new IdMap(run.getQueueId());
        span = idmap.getSpan("Run");
        super.onStarted(run, listener);
    }

    @Override
    public void onFinalized(final hudson.model.Run<?, ?> run) {
        try {
            super.onFinalized(run);
        } finally {
            IdMap idmap = new IdMap(run.getQueueId());
            idmap.closeActiveSpan();
            idmap.finalize();
        }
    }

    @Override
    public Environment setUpEnvironment(AbstractBuild build, Launcher launcher, BuildListener listener)
            throws IOException, InterruptedException {
        IdMap idmap = new IdMap(build.getQueueId()
        Span envSpan = idmap.getSpan("setUpEnvironment");
        try {
            return super.setUpEnvironment(build, launcher, listener);
        } finally {
            idmap.closeActiveSpan();
        }
    }
}