package com.latlab.common.constants;

import java.io.Serializable;

/**
 *
 * @author edwin
 */
public enum FormOfPayment implements Serializable{

    MTN_MOBILE_MONEY("MTN Mobile Money", "MTN"),
    AIRTEL_MONEY("Airtel Money", "Airtel Money"),
    TIGO_CASH("Tigo Cash", "Tigo Cash"),
    VODAFONE_CASH("Vodafone Cash", "Vodafon Cash"),
    MASTER_CARD("Master Card", "Master Card"),
    VISA_CARD("Visa Card", "Visa Card"),
    BANK_PAYMENT("Bank", "Bank"),
    BANKERS_DRAFT("Bankers Draft", "BD"),
    CASH("Cash", "CH"),
    CHEQUE("Cheque", "CHQ"),
    BANK_TRANSFER("Bank Transfer", "BT"),
    CARD_PAYMENT("Card Payment", "Card Payment"),
    PAY_PAL("Pay Pal", "PAY_PAL"),
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
