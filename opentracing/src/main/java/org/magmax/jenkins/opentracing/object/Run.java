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
import io.jaegertracing.internal.JaegerSpan;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class Run extends RunListener<hudson.model.Run<?, ?>> {

    private JaegerSpan span;

    public Run() {
        // Necessary for jenkins
    }

    public Run(Class<hudson.model.Run<?, ?>> targetType) {
        super(targetType);
    }

    @Override
    public void onStarted(hudson.model.Run<?, ?> run, TaskListener listener) {
        System.out.println("** RUN ** onStarted " + run.getQueueId());
        IdMap idmap = new IdMap();
        span = idmap.getSpan(run.getQueueId(), "Run");
        super.onStarted(run, listener);
    }

    @Override
    /**
     * Update the build status and duration.
     */
    public void onFinalized(final hudson.model.Run<?, ?> run) {
        System.out.println("** RUN ** onFinalized" + run.getQueueId());
        System.out.println("***** Thread: " + Thread.currentThread().getId());
        try {
            super.onFinalized(run);
        } finally {
            span.finish();
        }
    }

    @Override
    public Environment setUpEnvironment(AbstractBuild build, Launcher launcher, BuildListener listener)
            throws IOException, InterruptedException {
        System.out.println("** RUN ** setUpEnvironment " + build.getQueueId());
        System.out.println("***** Thread: " + Thread.currentThread().getId());
        IdMap idmap = new IdMap();
        JaegerSpan envSpan = idmap.getSpan(build.getQueueId(), "setUpEnvironment");
        try {
            return super.setUpEnvironment(build, launcher, listener);
        } finally {
            envSpan.finish();
        }
    }
}