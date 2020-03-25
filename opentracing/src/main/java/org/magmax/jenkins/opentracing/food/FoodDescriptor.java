package org.magmax.jenkins.opentracing.food;

import java.io.File;

import org.kohsuke.stapler.StaplerRequest;

import hudson.XmlFile;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

public abstract class FoodDescriptor extends Descriptor<Food> {
    protected FoodDescriptor() {
        System.out.println(">>>>>> FOOD DESCRIPTOR: constructor");
        load();
    }

    protected XmlFile getConfigFile() {
        System.out.println(">>>>>> FOOD DESCRIPTOR: getConfigFile");

        return new XmlFile(new File(Jenkins.get().getRootDir(), "food.xml"));
    }

    @Override
    public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
        System.out.println(">>>>>> FOOD DESCRIPTOR: configure");
        System.out.println(">>>>>>  Food Descriptor ");
        System.out.println("Json: " + json);
        System.out.println("Json: " + req.getClass());

        //req.bindJSON(this, json.getJSONObject("FoodImpl"));
        req.bindJSON(this, json);
        save();
        return true;
    }


    private String tasty = "on descriptor";

    public String getTasty() {
        System.out.println(">>>>>> FOOD DESCRIPTOR: getTasty");
        return tasty;
    }

    public void setTasty(String tasty) {
        System.out.println(">>>>>> FOOD DESCRIPTOR: setTasty");
        this.tasty = tasty;
    }

    @Override
    public String getDisplayName() {
        return "MyFood";
    }
}