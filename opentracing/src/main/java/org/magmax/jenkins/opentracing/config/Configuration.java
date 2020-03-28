package org.magmax.jenkins.opentracing.config;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.FreeStyleProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import net.sf.json.JSONObject;

public class Configuration extends Builder {

    public List<AbstractTracerConfig> getTracers() {
        return getDescriptor().getTracers();
    }

    @Override
    public Descriptor getDescriptor() {
        return (Descriptor) super.getDescriptor();
    }

    @Extension
    public static class Descriptor extends BuildStepDescriptor<Builder> {
        private List<AbstractTracerConfig> tracers;

        public Descriptor() {
            load();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            req.bindJSON(this, json.getJSONObject("opentracing"));
            save();
            return true;
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return FreeStyleProject.class.isAssignableFrom(jobType);
        }

        @Override
        public Builder newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return super.newInstance(req, formData);
        }

        @Override
        public String getDisplayName() {
            return "OpenTracing";
        }

        public List<AbstractTracerConfig> getTracers() {
            if (tracers == null) {
                return new ArrayList<AbstractTracerConfig>();
            }
            return tracers;
        }

        public void setTracers(List<AbstractTracerConfig> tracers) {
            this.tracers = tracers;
        }
    }
}