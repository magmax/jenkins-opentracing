package org.magmax.jenkins.opentracing.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;

@Extension
public class Configuration extends AbstractConfiguration {

    @Extension
    public static final class DescriptorImpl extends Descriptor<AbstractConfiguration> {
        private List<AbstractTracerConfig> tracers;

        public List<AbstractTracerConfig> getTracers() {
            return Collections.unmodifiableList(tracers == null ? Collections.emptyList() : tracers);
        }

        public void setTracers(List<AbstractTracerConfig> tracers) {
            this.tracers = tracers;
        }
    }

    public static final class DescribableImpl extends AbstractDescribableImpl<DescribableImpl> {

        private final List<AbstractTracerConfig> tracers;

        @DataBoundConstructor
        public DescribableImpl(List<AbstractTracerConfig> tracers) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Bounding: " + tracers);
            this.tracers = tracers == null ? Collections.<AbstractTracerConfig>emptyList()
                    : new ArrayList<AbstractTracerConfig>(tracers);
        }

        public List<AbstractTracerConfig> geTracers() {
            return Collections.unmodifiableList(tracers);
        }

        @Extension
        public static class DescriptorImpl extends Descriptor<DescribableImpl> {
        }
    }

    @Override
    public String getIconFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayName() {
        return "OpenTracing";
    }

    @Override
    public String getUrlName() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @Override public Descriptor getDescriptor() { return (Descriptor)
     * super.getDescriptor(); }
     */

    @Override
    public Descriptor<AbstractConfiguration> getDescriptor() {
        // return (Descriptor<Configuration>) super.getDescriptor();
        return (Descriptor<AbstractConfiguration>) Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

}