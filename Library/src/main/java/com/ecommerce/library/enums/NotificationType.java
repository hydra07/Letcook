package com.ecommerce.library.enums;

public enum NotificationType {
    NEW("NEW", "New recipe!"),
    ACCEPT("ACCEPT", "Recipe Accepted!"),

    REJECT("REJECT", "Recipe Rejected!"),

    ACCEPT_ORDER("ACCEPT_ORDER", "Order Accepted!"),

    CANCEL_ORDER("CANCEL_ORDER", "Order Canceled!");

    private final String type;
    private final String title;

    NotificationType(String type, String title) {
        this.type = type;
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public String getTitle() {
        return this.title;
    }
}
