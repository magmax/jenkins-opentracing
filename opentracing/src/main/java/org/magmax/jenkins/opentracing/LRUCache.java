package org.magmax.jenkins.opentracing;

import java.util.HashMap;

public class LRUCache {
    private static final int MAX_SIZE = 1000;
    private int max_size = MAX_SIZE;
    private static LRUCache _instance = null;
    private HashMap<String, LRUCacheItem> map = new HashMap<>();
    private LRUCacheItem first;
    private LRUCacheItem last;

    private LRUCache() {
    }

    public static LRUCache getInstance() { 
        if (_instance == null) {
            _instance = new LRUCache();
        }
        return _instance;
    }

    public void reset() {
        // just for testing proposes
        map.clear();
        first = null;
        last = null;
        max_size = MAX_SIZE;
    }

    public Object get(String id) {
        if (! map.containsKey(id)) {
            return null;
        }
        LRUCacheItem value = map.get(id);

        if (first == value) {
            return value;
        }
        
        removeFromList(value);
        insertInList(value);
        return value.data;
    }

    public void put(String id, Object data) {
        if (map.containsKey(id)) {
            removeFromList(map.get(id));
        }
        LRUCacheItem item = new LRUCacheItem(id, data);
        map.put(id, item);
        insertInList(item);
        ensureSize();
    }

    private void insertInList(LRUCacheItem value) {   
        value.previous = null;
        value.next = first;
        if (first != null) {
            first.previous = value;
        }
        if (last == null) {
            last = value;
        }
    }

    private void ensureSize() {
        while (map.size() > max_size) {
            map.remove(last.key);
            last = last.previous;
        }
    }

    private void removeFromList(LRUCacheItem item) {
        LRUCacheItem previous = item.previous;
        LRUCacheItem next = item.next;

        if (previous != null ) {
            previous.next = next;
        }
        if (next != null) {
            next.previous = previous;
        }
        if (last == item) {
            last = previous;
        }
        if (first == item) {
            first = next;
        }
    }

    public void setSize(int size) {
        max_size = size;
    }

	public int size() {
		return map.size();
	}
}

class LRUCacheItem {
    LRUCacheItem previous;
    LRUCacheItem next;

    String key;
    Object data;

    public LRUCacheItem(String key, Object data) {
        this.key = key;
        this.data = data;
    }
}