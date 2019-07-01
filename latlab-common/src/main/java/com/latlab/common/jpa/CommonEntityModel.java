/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author ICSGH-BILLY
 */
@MappedSuperclass
public class CommonEntityModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    public static final String _id = "id";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Basic(optional = false)
    private Long id;

    public static final String _createdDate = "createdDate";
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = new Date();
    
    public static final String _createdBy = "createdBy";
    @Column(name = "created_by")
    private String createdBy;

    public static final String _lastModifiedBy = "lastModifiedBy";
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public static final String _lastModifiedDate = "lastModifiedDate";
    @Column(name = "last_modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate = new Date();
    
    public static final String _deletedAt = "deletedAt";
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt = null;

    @Transient
    private boolean selected = false;

    @Transient
    private int counter = 0;

    public CommonEntityModel()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getCreatedDate()
    {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy()
    {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy)
    {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    
    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public int getCounter()
    {
        return counter;
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public String getModelInfo()
    {
        return "CommonModel{" + "createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", deletedAt=" + deletedAt + '}';
    }

    public String toFullInsertSQL()
    {
        return null;
    }

    public String toInsertValues()
    {
        return null;
    }

    public String insertHeader()
    {
        return null;
    }

    public String updateHeader()
    {
        return null;
    }

    public String toUpdateValues()
    {
        return null;
    }

    public String toFullUpdateSQL()
    {
        return null;
    }

    public String getTableName()
    {
        return CommonEntityModel.class.getSimpleName();
    }

    public String updateSql()
    {
        return null;
    }

    public String deleteQuerry()
    {
        return null;
    }

    public static void countItems(List<? extends CommonEntityModel> itemsList)
    {
        int count = 0;
        for (CommonEntityModel item : itemsList)
        {
            item.setCounter(++count);
        }
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final CommonEntityModel other = (CommonEntityModel) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

}
