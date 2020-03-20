package org.magmax.jenkins.opentracing.config;

import hudson.ExtensionPoint;
import hudson.model.Action;
import hudson.model.Describable;

public abstract class AbstractConfiguration implements ExtensionPoint, Action, Describable<AbstractConfiguration> {

}