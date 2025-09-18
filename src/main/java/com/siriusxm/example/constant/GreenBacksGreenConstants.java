package com.siriusxm.example.constant;

public enum GreenBacksGreenConstants {

    // HTTP methods
    HTTP_METHOD_GET("GET"),
    HTTP_METHOD_POST("POST"),

    // Nested Constant Value
    COUNTRIES(Configuration.COUNTRIES.getVal()),

    // enum values where .name() eq constant String value
    BUSINESS_PLAN_KEY_WORDS,
    PITCH_PLAN_KEY_WORDS,
    BROWSER_ID,

    // Device agent
    BROWSER_DEFAULT_VALUE("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");

    private final String val;

    GreenBacksGreenConstants(String val) {
        this.val = val;
    }

    GreenBacksGreenConstants() {
        this.val = this.name();
    }
}
