package com.latlab.common.constants;

public enum PaymentType implements EnumResolvable {

    CASH("CASH", "Cash"),
    CHEQUE("CHEQUE", "Cheque"),
    BANK_TRANSFER("BANK_TRANSFER", "Bank Transfer"),
    OTHER("OTHER", "Other");

    private final String label;
    private final String code;

    private PaymentType(String label, String code) {
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
