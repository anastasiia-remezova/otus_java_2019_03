package ru.otus;

public class Person {
    private String name;
    private Integer age;
    private Character sex;
    private String job;

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


    public boolean equals(Person otherPerson){
        return this.name.equals(otherPerson.name) && this.age.equals(otherPerson.age);

    }
}
