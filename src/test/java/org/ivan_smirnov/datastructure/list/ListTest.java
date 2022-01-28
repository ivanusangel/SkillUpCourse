package org.ivan_smirnov.datastructure.list;

import org.ivan_smirnov.datastructure.data.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class ListTest {
    private static final Person SARA = new Person("Sara", 29);
    private static final Person TERMINATOR = new Person("Terminator", 1000);
    private static final Person IVAN = new Person("Ivan", 39);
    private static final Person DIMON = new Person("Dimon", 25);

    public List<Person> list = getList();

    public abstract List<Person> getList();

    @Before
    public void initList() {
        list.add(IVAN);
        list.add(DIMON);
    }

    @Test
    public void addToEndOfList() {
        Assert.assertEquals(2, list.size());
        list.add(SARA);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(SARA, list.get(2));
    }

    @Test
    public void addIntoMiddleOfListPositive() {
        Assert.assertEquals(DIMON, list.get(1));
        list.add(SARA, 1);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(SARA, list.get(1));
        Assert.assertEquals(DIMON, list.get(2));
    }

    @Test
    public void addIntoMiddleOfListNegative() {
        Assert.assertEquals(2, list.size());

        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(TERMINATOR, -2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 2";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);

        exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(TERMINATOR, 5)
        );
        expectedMessage = "Index: 5. Expected between 0 and 2";
        actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getFromListPositive() {
        Assert.assertEquals(DIMON, list.get(1));
    }

    @Test
    public void getFromListNegativeLowerRange() {
        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 1";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void getFromListNegativeUpperRange() {
        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(5)
        );
        String expectedMessage = "Index: 5. Expected between 0 and 1";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void setIntoListPositive() {
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(DIMON, list.get(1));
        Person replaced = list.set(SARA, 1);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(SARA, list.get(1));
        Assert.assertEquals(DIMON, replaced);
    }

    @Test
    public void setIntoListNegativeLowerRange() {
        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 1";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void setIntoListNegativeUpperRange() {
        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(5)
        );
        String expectedMessage = "Index: 5. Expected between 0 and 1";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void removeFromListPositive() {
        Person beforeRemove = list.get(1);
        Assert.assertEquals(2, list.size());
        Person removed = list.remove(1);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(beforeRemove, removed);
    }

    @Test
    public void removeFromListNegativeLowerRange() {
        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(-2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 1";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void removeFromListNegativeUpperRange() {
        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(5)
        );
        String expectedMessage = "Index: 5. Expected between 0 and 1";
        String actualMessage = exception.getMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void clearList() {
        Assert.assertEquals(2, list.size());
        list.clear();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void isEmptyList() {
        Assert.assertFalse(list.isEmpty());
        list.clear();
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void listContains() {
        Assert.assertTrue(list.contains(IVAN));
        Assert.assertFalse(list.contains(SARA));
    }

    @Test
    public void indexOfList() {
        Assert.assertEquals(1, list.indexOf(DIMON));
        Assert.assertEquals(1, list.lastIndexOf(DIMON));
        list.add(SARA);
        list.add(SARA);
        list.add(DIMON);
        Assert.assertEquals(1, list.indexOf(DIMON));
        Assert.assertEquals(4, list.lastIndexOf(DIMON));
        list.set(DIMON, 0);
        Assert.assertEquals(0, list.indexOf(DIMON));
        Assert.assertEquals(4, list.lastIndexOf(DIMON));
    }

    @Test
    public void listToString() {
        Assert.assertEquals("[Person{name='Ivan', age=39}, Person{name='Dimon', age=25}]", list.toString());

    }
}
