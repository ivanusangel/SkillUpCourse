import org.junit.Test;
import smirnov_task1.ArrayList;
import smirnov_task1.List;
import smirnov_task1.Person;

public class TestList {

    @Test
    public void test1 () {
        List list1 = new ArrayList(3);

        Person p1 = new Person("Ivan", 20);
        Person p2 = new Person("Dimon", 25);
        list1.add(p1);
        list1.add(p1);
        list1.add(p1);
        list1.add(null);
        list1.add(p1);
        printPersonList(list1);
        list1.add(p2, 4);
        printPersonList(list1);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        list1.remove(0);
        printPersonList(list1);



    }

    public void printPersonList(List list) {
        System.out.print("size:" + list.size() + ". ");
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            if(o == null) {
                System.out.print("null");
            } else {
                System.out.print(((Person) o).getName());
            }
            if(i < list.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
