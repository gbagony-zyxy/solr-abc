package com.ruyin.code.solr.bean;

public enum Degree {
    POST_DOCTOR("博士后"),
    DOCTOR("博士"),
    MASTER("硕士"),
    BACHELOR("本科"),
    JUNIOR("大专"),
    SENIOR_HIGH("高中"),
    OTHER("其他");

    private String name;

    private Degree(String name) {
        this.name = name;
    }

    public String getDegreeName() {
        return this.name;
    }

    public static Degree enumOf(String name) {
        Degree[] vars = values();

        for(int var = 0; var < vars.length; ++var) {
            Degree d = vars[var];
            if(d.name.equals(name)) {
                return d;
            }
        }

        return OTHER;
    }
}
