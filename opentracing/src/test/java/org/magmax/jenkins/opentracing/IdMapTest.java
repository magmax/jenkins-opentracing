package org.magmax.jenkins.opentracing;

import hudson.model.AbstractBuild;
import io.jaegertracing.internal.JaegerSpan;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IdMapTest {
    @Before
    public void setup() {
        LRUCache.getInstance().reset();
    }

    @Test
    public void testNoPreviousData() {
        AbstractBuild build = mock(AbstractBuild.class);
        AbstractBuild rootbuild = mock(AbstractBuild.class);
        when(build.getExternalizableId()).thenReturn("example");
        when(build.getRootBuild()).thenReturn(rootbuild);
        when(rootbuild.getExternalizableId()).thenReturn("highway to hell");
        IdMap idmap = new IdMap();
        JaegerSpan span = idmap.getSpan(build, "Test");
        assertNotNull(span);
        assertNotNull(span.context());
    }
}