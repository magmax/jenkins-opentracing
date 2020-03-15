package org.magmax.jenkins.opentracing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class LRUCacheTest {
    @Before
    public void setup() {
        LRUCache.getInstance().reset();
    }
    @Test
    public void test_basic_usage() {
        LRUCache cache = LRUCache.getInstance();
        cache.put("one", "whatever");
        assertEquals(cache.get("one"), "whatever");
    }

    @Test
    public void test_rotation() {
        LRUCache cache = LRUCache.getInstance();
        cache.setSize(1);
        cache.put("one", "1");
        cache.put("two", "2");
        assertEquals(1, cache.size());
    }

    @Test
    public void test_overwrite() {
        LRUCache cache = LRUCache.getInstance();
        cache.put("one", "whatever");
        cache.put("one", "1");
        assertEquals(1, cache.size());
        assertEquals("1", cache.get("one"));
    }
}