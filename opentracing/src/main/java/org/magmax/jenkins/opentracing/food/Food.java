package org.magmax.jenkins.opentracing.food;

import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;

public abstract class Food extends AbstractDescribableImpl<Food> implements ExtensionPoint {

    @Override
    public FoodDescriptor getDescriptor() {
        System.out.println(">>>>> Food > getDescriptor");
        return (FoodDescriptor) super.getDescriptor();
    }
}