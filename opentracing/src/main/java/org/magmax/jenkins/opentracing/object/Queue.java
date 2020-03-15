package org.magmax.jenkins.opentracing.object;

import org.magmax.jenkins.opentracing.IdMap;

import jenkins.YesNoMaybe;

import hudson.Extension;
import hudson.model.queue.QueueListener;
import io.opentracing.Scope;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class Queue extends QueueListener {
    private Scope waitingSpan;
    private Scope blockedSpan;
    private Scope buildableSpan;

    @Override
    public void onEnterWaiting(hudson.model.Queue.WaitingItem wi) {
        IdMap idmap = new IdMap();
        waitingSpan = idmap.getSpan(wi.getId(), "Waiting");
        System.out.println("** QUEUE ** onEnterWaiting" + wi.getId());
    }

    @Override
    public void onLeaveWaiting(hudson.model.Queue.WaitingItem wi) {
        super.onLeaveWaiting(wi);
        if (waitingSpan != null) {
            waitingSpan.close();
        }
        System.out.println("** QUEUE ** onLeaveWaiting" + wi.getId());
    }

    @Override
    public void onEnterBlocked(hudson.model.Queue.BlockedItem bi) {
        IdMap idmap = new IdMap();
        blockedSpan = idmap.getSpan(bi.getId(), "Blocked");
        System.out.println("** QUEUE ** onEnterBlocked" + bi.getId());
    }

    @Override
    public void onLeaveBlocked(hudson.model.Queue.BlockedItem bi) {
        super.onLeaveBlocked(bi);
        blockedSpan.close();
        System.out.println("** QUEUE ** onLeaveBlocked" + bi.getId());

    }

    @Override
    public void onEnterBuildable(hudson.model.Queue.BuildableItem bi) {
        IdMap idmap = new IdMap();
        buildableSpan = idmap.getSpan(bi.getId(), "Buildable");
        super.onEnterBuildable(bi);
        System.out.println("** QUEUE ** onEnterBuildable" + bi.getId());

    }

    @Override
    public void onLeaveBuildable(hudson.model.Queue.BuildableItem bi) {
        super.onLeaveBuildable(bi);
        if (buildableSpan != null)
            buildableSpan.close();  
        System.out.println("** QUEUE ** onLeaveBuildable" + bi.getId());

    }

    @Override
    public void onLeft(hudson.model.Queue.LeftItem li) {
        super.onLeft(li);
        System.out.println("** QUEUE ** onLeft" + li.getId());
    }
}
