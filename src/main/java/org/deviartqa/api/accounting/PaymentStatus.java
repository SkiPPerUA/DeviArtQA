package org.deviartqa.api.accounting;

public enum PaymentStatus {

    Paid(2),
    Cancel(0);

    private int title;
    PaymentStatus(int s) {
        this.title = s;
    }
    @Override
    public String toString() {
        return String.valueOf(title);
    }
}
