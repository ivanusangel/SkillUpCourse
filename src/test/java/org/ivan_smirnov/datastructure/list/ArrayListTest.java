package org.ivan_smirnov.datastructure.list;

import org.ivan_smirnov.datastructure.data.Person;
import org.junit.Assert;
import org.junit.Test;

public class ArrayListTest extends ListTest {
    private static final int INITIAL_SIZE = 5;

    @Override
    public List<Person> getList() {
        return new ArrayList<>(INITIAL_SIZE);
    }

    @Test
    public void addToListThisResize() {
        for (int i = list.size(); i < INITIAL_SIZE; i++) {
            list.add(null);
        }
        Assert.assertEquals(INITIAL_SIZE, list.size());
        list.add(new Person("Resizer", 100));
        Assert.assertEquals(INITIAL_SIZE + 1, list.size());
    }

}
