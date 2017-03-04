package com.latlab.common.jpa;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;

/**
 *
 * @author Edwin Kwame Amoakwa
 */

@MappedSuperclass
public class DateRecord extends TimeUnit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final String _valueDate = "valueDate";
    @Column(name = "value_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date valueDate;
    

    public Date getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(Date valueDate)
    {
        this.valueDate = valueDate;
        setDateDetails(valueDate);
    }
    
    
    
}
