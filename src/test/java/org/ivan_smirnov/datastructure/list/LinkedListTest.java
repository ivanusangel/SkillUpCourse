package org.ivan_smirnov.datastructure.list;

import org.ivan_smirnov.datastructure.data.Person;

public class LinkedListTest extends ListTest{
    @Override
    public List<Person> getList() {
        return new LinkedList<>();
    }
}
