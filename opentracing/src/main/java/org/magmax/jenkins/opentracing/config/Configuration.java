package org.magmax.jenkins.opentracing.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;

@Extension
public class Configuration extends Builder {

    private DescribableImpl config;

    public DescribableImpl getConfig() {
        return config;
    }

    public void setConfig(DescribableImpl config) {
        this.config = config;
    }

    @Override
    public Descriptor getDescriptor() {
        return (Descriptor) super.getDescriptor();
    }

    @Extension
    public static class Descriptor extends BuildStepDescriptor<Builder> {

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return FreeStyleProject.class.isAssignableFrom(jobType);
        }
    }

    @Extension
    public static final class DescriptorImpl extends hudson.model.Descriptor<DescribableImpl> {
    }

    public final class DescribableImpl extends AbstractDescribableImpl<DescribableImpl> {

        private final List<AbstractTracerConfig> trackers;

        @DataBoundConstructor
        public DescribableImpl(List<AbstractTracerConfig> trackers) {
            this.trackers = trackers != null ? new ArrayList<AbstractTracerConfig>(trackers) : Collections.<AbstractTracerConfig>emptyList();
        }

        public List<AbstractTracerConfig> geTrackers() {
            return Collections.unmodifiableList(trackers);
        }
    }
}