package org.magmax.jenkins.opentracing;

import io.opentracing.Span;

public class IdMap {
    private String myId;

    public IdMap(long id) {
        myId = "" + id;
    }

    public Span getSpan(String name) {
        LRUCache cache = LRUCache.getInstance();
        ILRUItem item = cache.get(myId);

        if (item == null) {
            item = new JenkinsTracer();
            LRUCache.getInstance().put(myId, item);
        }
        Span span = item.createSpan(name);

        return span;
    }

    public void closeActiveSpan() {
        LRUCache cache = LRUCache.getInstance();
        ILRUItem item = cache.get(myId);
        if (item != null) {
            item.closeActiveSpan();
        }
    }

    public void finalize() {
        ILRUItem item = LRUCache.getInstance().get("" + myId);

        if (item != null) {
            item.finish();
        }
    }
}
