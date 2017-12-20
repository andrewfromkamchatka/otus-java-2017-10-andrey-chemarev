package ru.chemarev.andrey;

import ru.chemarev.andrey.core.DataSet;
import ru.chemarev.andrey.core.Table;

@Table(name="\"User\"")
public class UserDataSet extends DataSet {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", age=" + age +
                "} ";
    }

}
