package ru.otus;

public class Person {
    public String name;

    public Person(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Character getSex() {
        return sex;
    }

    public String getJob() {
        return job;
    }

    public Integer age;
    public Character sex;
    public String job;


}
