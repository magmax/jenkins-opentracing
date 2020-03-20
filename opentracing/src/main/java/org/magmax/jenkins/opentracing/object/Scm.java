package org.magmax.jenkins.opentracing.object;

import java.io.File;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.SCMListener;
import hudson.scm.ChangeLogSet;
import hudson.scm.SCM;
import hudson.scm.SCMRevisionState;
import jenkins.YesNoMaybe;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class Scm extends SCMListener {

    @Override
    public void onChangeLogParsed(AbstractBuild<?, ?> build, BuildListener listener, ChangeLogSet<?> changelog)
            throws Exception {
        // TODO Auto-generated method stub
        super.onChangeLogParsed(build, listener, changelog);
    }

    @Override
    public void onChangeLogParsed(Run<?, ?> build, SCM scm, TaskListener listener, ChangeLogSet<?> changelog)
            throws Exception {
        // TODO Auto-generated method stub
        super.onChangeLogParsed(build, scm, listener, changelog);
    }

    @Override
    public void onCheckout(Run<?, ?> build, SCM scm, FilePath workspace, TaskListener listener, File changelogFile,
            SCMRevisionState pollingBaseline) throws Exception {
        // TODO Auto-generated method stub
        super.onCheckout(build, scm, workspace, listener, changelogFile, pollingBaseline);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

}