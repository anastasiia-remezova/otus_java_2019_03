package ru.otus;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Programmer {
    private String name;
    private int age;
    private boolean sex;
    private String[] languages;
    private List<String> programming_languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Programmer that = (Programmer) o;
        return age == that.age &&
                sex == that.sex &&
                Objects.equals(name, that.name) &&
                Arrays.equals(languages, that.languages) &&
                Objects.equals(programming_languages, that.programming_languages);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, age, sex, programming_languages);
        result = 31 * result + Arrays.hashCode(languages);
        return result;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }


    public List<String> getProgramming_languages() {
        return programming_languages;
    }

    public void setProgramming_languages(List<String> computer_languages) {
        this.programming_languages = computer_languages;
    }

    public Programmer(String name, int age, String[] languages, List<String> programming_languages) {
        this.name = name;
        this.age = age;
        this.languages = languages;
        this.programming_languages = programming_languages;
    }
}
