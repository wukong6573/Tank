package com.example.demo.test.ShuDu;


public enum  DifficultyLevel {
    EASY("EASY"),MEDIUM("MEDIUM"),DIFFICULT("DIFFICULT"),EVIL("EVIL");
    private final String name;

    DifficultyLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
