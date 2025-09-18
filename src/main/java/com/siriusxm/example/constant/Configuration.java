package com.siriusxm.example.constant;

public enum Configuration {
    COUNTRIES;

    private final String val;

    Configuration() {
        this.val = this.name().toLowerCase();
    }

    public String getVal() {
        return val;
    }
}
