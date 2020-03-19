package org.magmax.jenkins.opentracing.object;

import hudson.Extension;
import hudson.model.listeners.SCMListener;
import jenkins.YesNoMaybe;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class Scm extends SCMListener{
}