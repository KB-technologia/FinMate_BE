package org.finmate.product.domain;

public enum InterestType {
    SIMPLE("SIMPLE", "단리"),
    COMPOUND("COMPOUND", "복리");

    private final String code;
    private final String description;

    InterestType(String code, String description) {
        this.code = code;
        this.description = description;
    }

}
