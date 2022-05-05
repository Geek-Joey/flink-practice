package com.joey.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author joey
 * @create 2022-04-19 3:59 下午
 */
public class Person {
    public String name;
    public Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                '}';
    }
}
