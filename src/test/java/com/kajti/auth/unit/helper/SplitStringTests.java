package com.kajti.auth.unit.helper;

import com.kajti.auth.helper.SplitString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class SplitStringTests {
    @Test
    public void testSplitStringWhenFewItemsIsProvided() {
        Set<String> result = SplitString.execute("x,y,z");
        List<String> list = new ArrayList<>(result);

        assertEquals(3, result.size());
        assertEquals("x", list.get(0));
        assertEquals("y", list.get(1));
        assertEquals("z", list.get(2));
    }

    @Test
    public void testSplitStringWhenOneItemIsProvided() {
        Set<String> result = SplitString.execute("x");
        List<String> list = new ArrayList<>(result);

        assertEquals(1, result.size());
        assertEquals("x", list.get(0));
    }

    @Test
    public void testSplitStringWhenNullIsProvided() {
        assertNull(SplitString.execute(null));
    }
}
