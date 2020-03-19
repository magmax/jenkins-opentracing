package org.magmax.jenkins.opentracing;

import java.util.HashMap;

public class LRUCache {
    private static final int MAX_SIZE = 1000;
    private int max_size = MAX_SIZE;
    private static LRUCache _instance = null;
    private HashMap<String, LRUItemContainer> map = new HashMap<>();
    private LRUItemContainer first;
    private LRUItemContainer last;

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

    public ILRUItem get(String id) {
        if (! map.containsKey(id)) {
            return null;
        }
        LRUItemContainer value = map.get(id);

        if (first == value) {
            return value.data;
        }

        removeFromList(value);
        insertInList(value);
        return value.data;
    }

    public void put(String id, ILRUItem data) {
        if (map.containsKey(id)) {
            removeFromList(map.get(id));
        }
        LRUItemContainer item = new LRUItemContainer(id, data);
        map.put(id, item);
        insertInList(item);
        ensureSize();
    }

    private void insertInList(LRUItemContainer value) {
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
            last.data.finish();
            map.remove(last.key);
            last = last.previous;
        }
    }

    private void removeFromList(LRUItemContainer item) {
        LRUItemContainer previous = item.previous;
        LRUItemContainer next = item.next;

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

class LRUItemContainer {
    LRUItemContainer previous;
    LRUItemContainer next;

    String key;
    ILRUItem data;

    public LRUItemContainer(String key, ILRUItem data) {
        this.key = key;
        this.data = data;
    }
}