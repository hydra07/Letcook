package com.ecommerce.library.enums;

public enum NotificationType {

    //recipe
    NEW("NEW", "New recipe!"),
    ACCEPT("ACCEPT", "Recipe Accepted!"),

    REJECT("REJECT", "Recipe Rejected!"),

    //order
    ACCEPT_ORDER("ACCEPT_ORDER", "Order Accepted!"),

    REJECT_ORDER("REJECT_ORDER", "Order Rejected!"),

    CANCEL_ORDER("CANCEL_ORDER", "Order Canceled!"),

    ORDER_SUCCESS("ORDER_SUCCESS", "Order Successful!"),

    ORDER_UNSUCCESS("ORDER_UNSUCCESS", "Order Unsuccessful!"),
    //admin
    NEW_ORDER("NEW_ORDER", "New Order!"),

    NEW_RECIPE("NEW_RECIPE", "New Recipe!"),

    ;

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
