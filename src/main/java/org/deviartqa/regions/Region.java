package org.deviartqa.regions;

public abstract class Region {

    protected String name;
    protected int offer_id;
    protected String code;
    protected int selling_operator_id;
    protected int reminder_operator_id;
    protected String delivery_service;

    public String getName() {
        return name;
    }

    public int getReminder_operator_id() {
        return reminder_operator_id;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public String getCode() {
        return code;
    }

    public int getSelling_operator_id() {
        return selling_operator_id;
    }

    public String getDelivery_service() {
        return delivery_service;
    }
}
