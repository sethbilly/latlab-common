package com.latlab.common.constants;

public enum PaymentPlatform implements EnumResolvable {

    GHIPSS("GHIPSS", "GHIPSS"),
    VISA_CARD_OR_MASTERCARD("Visa/Master Card", "Visa/Master Card"),
    VISA_CARD("Visa Card", "Visa Card"),
    BANK_TRANSFER("Bank Transfer", "Bank Transfer"),
    BANK_PAYMENT("Bank Payment", "Bank Payment"),
    MASTER_CARD("Master Card", "Master Card"),
    MOBILE_MONEY("MTN Mobile Money", "MTN Mobile Money"),
    AIRTEL_MONEY("Airtel Money", "Airtel Money"),
    M_POWER("MPower", "MPower");

    private final String label;
    private final String code;

    private PaymentPlatform(String label, String code) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return label;
    }

}
