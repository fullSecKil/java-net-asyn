package com.example.asynctest.pojo;

import lombok.Data;

@Data
public class TestReflectionPojo {
    private int id;
    public String name;
    private String career;

    public TestReflectionPojo() {
    }

    public TestReflectionPojo(int id) {
        this.id = id;
    }

    public TestReflectionPojo(String name) {
        this.name = name;
    }

    public TestReflectionPojo(int id, String name, String career) {
        this.id = id;
        this.name = name;
        this.career = career;
    }
}
