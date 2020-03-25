package org.magmax.jenkins.opentracing.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;


public class OpenTracingImpl extends OpenTracing {
    public OpenTracingImpl() {
    }

    private Boolean foo;
    private List<Tracer> tracers;

    public List<Tracer> getTracers() {
        System.out.println(">>>>>>  Configuration > DescriptorImpl getTracers");
        return Collections.unmodifiableList(tracers == null ? Collections.emptyList() : tracers);
    }

    public Boolean getFoo() {
        return foo;
    }

    public Boolean isFoo() {
        return foo;
    }

    /*
    @DataBoundConstructor
    public OpenTracing(List<Tracer> tracers, Boolean foo) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Bounding: " + tracers);
        this.tracers = tracers == null ? Collections.<Tracer>emptyList()
                : new ArrayList<Tracer>(tracers);
        this.foo = foo;
    }
    */

    @DataBoundConstructor
    public OpenTracingImpl(Boolean foo) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Bounding: " + tracers);
        this.foo = foo;
    }

    @Extension
    public static final class OpenTracingDescriptor extends Descriptor<OpenTracing> {

        public OpenTracingDescriptor() {
            System.out.println(">>>>>>>>>>>>>><  Descriptor Empty Constructor");
            load();
        }


        public OpenTracingDescriptor(OpenTracing ot) {
            System.out.println(">>>>>>>>>>>>>><  Descriptor Constructor");
            load();
        }

        @Override
        public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
            //System.out.println(">>>>>>  DescriptorImpl > save: " + tracers);
            System.out.println("Json: " + json);
            System.out.println("Json: " + json.getJSONObject("opentracing"));
            System.out.println("Json: " + req.getClass());

            req.bindJSON(this, json.getJSONObject("opentracing"));
            //req.bindJSON(this, json);
            save();
            return true;
        }

        protected XmlFile getConfigFile() {
            return new XmlFile(new File(Jenkins.get().getRootDir(), "opentracing.xml"));
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
}