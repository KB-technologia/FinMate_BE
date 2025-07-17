package org.finmate.product.domain;

public enum ProductType {
    DEPOSIT("DEPOSIT", "예금"),
    SAVINGS("SAVINGS", "적금"),
    FUND("FUND", "펀드");

    private final String code;
    private final String description;

    ProductType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
