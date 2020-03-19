package org.magmax.jenkins.opentracing.object;

import org.magmax.jenkins.opentracing.IdMap;

import jenkins.YesNoMaybe;

import hudson.Extension;
import hudson.model.queue.QueueListener;
import io.opentracing.Span;

@Extension(dynamicLoadable = YesNoMaybe.YES)
public class Queue extends QueueListener {
    private Span queueSpan;


    @Override
    public void onEnterWaiting(hudson.model.Queue.WaitingItem wi) {
        IdMap idmap = new IdMap(wi.getId());
        if (queueSpan == null) {
            queueSpan = idmap.getSpan("Queue");
        }
        idmap.getSpan("Waiting");
    }

    @Override
    public void onLeaveWaiting(hudson.model.Queue.WaitingItem wi) {
        super.onLeaveWaiting(wi);
        IdMap idmap = new IdMap(wi.getId());
        idmap.closeActiveSpan();
    }

    @Override
    public void onEnterBlocked(hudson.model.Queue.BlockedItem bi) {
        IdMap idmap = new IdMap(bi.getId());
        if (queueSpan == null) {
            idmap.getSpan("Queue");
        }
        idmap.getSpan("Blocked");
    }

    @Override
    public void onLeaveBlocked(hudson.model.Queue.BlockedItem bi) {
        super.onLeaveBlocked(bi);
        IdMap idmap = new IdMap(bi.getId());
        idmap.closeActiveSpan();
    }

    @Override
    public void onEnterBuildable(hudson.model.Queue.BuildableItem bi) {
        IdMap idmap = new IdMap(bi.getId());
        if (queueSpan == null) {
            queueSpan = idmap.getSpan("Queue");
        }
        idmap.getSpan("Buildable");
        super.onEnterBuildable(bi);

    }

    @Override
    public void onLeaveBuildable(hudson.model.Queue.BuildableItem bi) {
        super.onLeaveBuildable(bi);
        IdMap idmap = new IdMap(bi.getId());
        idmap.closeActiveSpan();
    }

    @Override
    public void onLeft(hudson.model.Queue.LeftItem li) {
        super.onLeft(li);
        IdMap idmap = new IdMap(li.getId());
        idmap.closeActiveSpan();
    }
}
