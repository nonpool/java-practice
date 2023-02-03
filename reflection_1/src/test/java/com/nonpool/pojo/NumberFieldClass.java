package com.nonpool.pojo;

public class NumberFieldClass {
    private Long id;
    private Integer age;

    public NumberFieldClass(Long id, Integer age) {
        this.id = id;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
