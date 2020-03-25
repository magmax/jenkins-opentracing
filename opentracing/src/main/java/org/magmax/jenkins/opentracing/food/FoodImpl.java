package org.magmax.jenkins.opentracing.food;

import hudson.Extension;

public class FoodImpl extends Food {


    public String getTasty() {
        System.out.println(">>>>> FoodImpl > getTasty");

        return this.getDescriptor().getTasty();
    }

    public void setTasty(String tasty) {
        System.out.println(">>>>> FoodImpl > setTasty");

        this.getDescriptor().setTasty(tasty);
    }

    @Override
    public DescriptorImpl getDescriptor() {
        System.out.println(">>>>> FoodImpl > getDescriptor");
        return (DescriptorImpl) super.getDescriptor();
    }

    @Extension
    public static class DescriptorImpl extends FoodDescriptor {

    }
}