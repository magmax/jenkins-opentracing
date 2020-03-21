package org.magmax.jenkins.opentracing.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

@Extension
public class Configuration extends AbstractConfiguration {

    @Extension
    public static final class DescriptorImpl extends Descriptor<AbstractConfiguration> {

        public DescriptorImpl() {
        }

        private List<AbstractTracerConfig> tracers;

        public List<AbstractTracerConfig> getTracers() {
            System.out.println(">>>>>>  Configuration > DescriptorImpl getTracers");
            return Collections.unmodifiableList(tracers == null ? Collections.emptyList() : tracers);
        }

        @DataBoundConstructor
        public DescriptorImpl(List<AbstractTracerConfig> tracers) {
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Bounding: " + tracers);
            this.tracers = tracers == null ? Collections.<AbstractTracerConfig>emptyList()
                    : new ArrayList<AbstractTracerConfig>(tracers);
        }

        @Override
        public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
            System.out.println(">>>>>>  DescriptorImpl > save: " + tracers);
            System.out.println("Json: " + json);
            System.out.println("Json: " + json.getJSONObject("opentracing"));
            System.out.println("Json: " + req.getClass());

            req.bindJSON(this, json.getJSONObject("opentracing"));
            save();
            return true;
        }

        protected XmlFile getConfigFile() {
            return new XmlFile(new File(Jenkins.get().getRootDir(), "opentracing.xml"));
        }

        /*
         * public void setTracers(List<AbstractTracerConfig> tracers) {
         * System.out.println(">>>>>>  Configuration > DescriptorImpl setTracers");
         *
         * this.tracers = tracers; }
         */
    }

    public static final class DescribableImpl extends AbstractDescribableImpl<DescribableImpl> {
        /*
         * private final List<AbstractTracerConfig> tracers;
         *
         * @DataBoundConstructor public DescribableImpl(List<AbstractTracerConfig>
         * tracers) { System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Bounding: " +
         * tracers); this.tracers = tracers == null ?
         * Collections.<AbstractTracerConfig>emptyList() : new
         * ArrayList<AbstractTracerConfig>(tracers); }
         *
         * public List<AbstractTracerConfig> getTracers() { return
         * Collections.unmodifiableList(tracers); }
         *
         * @Extension public static class DescriptorImpl extends
         * Descriptor<DescribableImpl> { }
         */
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

}