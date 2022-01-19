package task1_List;

import data.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(value = Parameterized.class)
public class TestList {
    public static final int INITIAL_SIZE = 5;
    public static final Person SARA = new Person("Sara", 29);
    public static final Person TERMINATOR = new Person("Terminator", 1000);
    public static final Person IVAN = new Person("Ivan", 39);
    public static final Person DIMON = new Person("Dimon", 25);

    public List list;

    @Parameterized.Parameters
    public static Collection<Object> useListTypes() {
        return Arrays.asList(new Object[]{new ArrayList(INITIAL_SIZE), new LinkedList()});
    }

    public TestList(List list) {
        this.list = list;
    }

    @Before
    public void initList() {
        list.clear();
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
    public void addIntoListPositive() {
        Assert.assertEquals(DIMON, list.get(1));
        list.add(SARA, 1);
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(SARA, list.get(1));
        Assert.assertEquals(DIMON, list.get(2));
    }

    @Test
    public void addIntoListNegative() {
        Assert.assertEquals(2, list.size());

        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(TERMINATOR, -2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 2";
        String actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);

        exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(TERMINATOR, 5)
        );
        expectedMessage = "Index: 5. Expected between 0 and 2";
        actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
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


    @Test
    public void getFromListPositive() {
        Assert.assertEquals(DIMON, list.get(1));
    }

    @Test
    public void getFromListNegative() {
        Assert.assertEquals(2, list.size());

        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 1";
        String actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);

        exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(5)
        );
        expectedMessage = "Index: 5. Expected between 0 and 1";
        actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void setIntoListPositive() {
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(DIMON, list.get(1));
        Object replaced = list.set(SARA, 1);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(SARA, list.get(1));
        Assert.assertEquals(DIMON, replaced);
    }

    @Test
    public void setIntoListNegative() {
        Assert.assertEquals(2, list.size());

        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 1";
        String actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);

        exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(5)
        );
        expectedMessage = "Index: 5. Expected between 0 and 1";
        actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void removeFromListPositive() {
        Object beforeRemove = list.get(1);
        Assert.assertEquals(2, list.size());
        Object removed = list.remove(1);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(beforeRemove, removed);
    }

    @Test
    public void removeFromListNegative() {
        Assert.assertEquals(2, list.size());

        Exception exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(-2)
        );
        String expectedMessage = "Index: -2. Expected between 0 and 1";
        String actualMessage = exception.getLocalizedMessage();
        Assert.assertEquals(expectedMessage, actualMessage);

        exception = Assert.assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(5)
        );
        expectedMessage = "Index: 5. Expected between 0 and 1";
        actualMessage = exception.getLocalizedMessage();
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
