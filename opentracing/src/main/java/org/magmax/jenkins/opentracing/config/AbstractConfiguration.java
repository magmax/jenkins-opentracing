package org.magmax.jenkins.opentracing.config;

import hudson.ExtensionPoint;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Action;

public abstract class AbstractConfiguration extends AbstractDescribableImpl<AbstractConfiguration>
        implements ExtensionPoint, Action {

}