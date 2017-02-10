package com.latlab.common.constants;

/**
 *
 * @author edwin
 */
public enum FormOfPayment {

    MTN_MOBILE_MONEY("MTN Mobile Money Wallet", "MTN"),
    MOBILE_MONEY("Mobile Money", "MM"),
    VISA_MASTER_CARD("Visa Master Card", "Visa/Master Card"),
    BANK_PAYMENT("Pay at Bank / MTN Branch ", "BO"),
    BANKERS_DRAFT("Bankers Draft", "BD"),
    CASH("Cash", "CH"),
    CHEQUE("Cheque", "CHQ"),
    BANK_TRANSFER("Bank Transfer", "BT"),
    CARD_PAYMENT("Card Payment", "Card Payment"),
    OTHER("Others", "Other");

    private String initials;
    private String name;

    private FormOfPayment(String name, String code) {
        this.initials = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
