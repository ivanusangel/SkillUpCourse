package org.ivan_smirnov.datastructure.map;

import org.ivan_smirnov.datastructure.list.ArrayList;
import org.ivan_smirnov.datastructure.list.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class MapTest {

    private static final int INITIAL_SIZE = 5;
    private Map<Integer, String> map = new ArrayMap<>(INITIAL_SIZE);

    @Before
    public void init() {
        map.put(0, "test0");
        map.put(3, "test3");
        map.put(4, "test4");
    }

    @Test
    public void putWithNewKeyInEmptyBucket() {
        Assert.assertEquals(3, map.size());
        String oldValue = map.put(1, "test1");
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("test1", map.get(1));
        Assert.assertEquals("test1", oldValue);
    }

    @Test
    public void putWithNewKeyInNotEmptyBucket() {
        Assert.assertEquals(3, map.size());
        String oldValue = map.put(INITIAL_SIZE, "test1");
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("test0", map.get(0));
        Assert.assertEquals("test1", map.get(INITIAL_SIZE));
        Assert.assertEquals("test1", oldValue);
    }

    @Test
    public void putWithAlreadyPresentKey() {
        Assert.assertEquals(3, map.size());
        String oldValue = map.put(0, "test1");
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("test1", map.get(0));
        Assert.assertEquals("test0", oldValue);
    }

    @Test
    public void getPositive() {
        Assert.assertEquals("test0", map.get(0));
    }

    @Test
    public void getNegative() {
        Exception exception = Assert.assertThrows(
                "There is no key = 1",
                NoSuchElementException.class,
                () -> map.get(1));
        String expectedMessage = "Index: -2. Expected between 0 and 2";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void containsTest() {
        Assert.assertTrue(map.containsKey(0));
        Assert.assertFalse(map.containsKey(1));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, map.size());
    }

    @Test
    public void removeExisted() {
        Assert.assertEquals(3, map.size());
        Map.Entry<Integer, String> oldValue = map.remove(0);
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("test0", oldValue.value);
    }

    @Test
    public void removeAbsent() {
        Assert.assertEquals(3, map.size());
        Map.Entry<Integer, String> oldValue = map.remove(1);
        Assert.assertEquals(3, map.size());
        Assert.assertEquals(null, oldValue);
    }

//    TODO
//    List<K> keySet();

    @Test
    public void iteratorWithSomeEmptyBuckets() {
        Assert.assertEquals(3, map.size());
        List<String> expectedList = new ArrayList<>(3);
        expectedList.add("test0");
        expectedList.add("test3");
        expectedList.add("test4");
        int index = 0;

        for (Map.Entry<Integer, String> entry : map) {
            Assert.assertEquals(expectedList.get(index++), entry.value);
        }
    }

    @Test
    public void iteratorWithSomeElementsInBucket() {
        Assert.assertEquals(3, map.size());
        map.put(INITIAL_SIZE, "test5");
        map.put(INITIAL_SIZE * 2, "test6");
        Assert.assertEquals(5, map.size());

        List<String> expectedList = new ArrayList<>(5);
        expectedList.add("test0");
        expectedList.add("test5");
        expectedList.add("test6");
        expectedList.add("test3");
        expectedList.add("test4");
        int index = 0;

        for (Map.Entry<Integer, String> entry : map) {
            Assert.assertEquals(expectedList.get(index++), entry.value);
        }
    }

//    TODO
//    @Test
//    public void iteratorRevome() {
//
//    }
}
