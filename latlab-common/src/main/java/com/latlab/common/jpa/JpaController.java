/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jpa;

import com.latlab.common.jpa.QryHelper.OrderBy;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;


public class JpaController implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JpaController.class.getName());
    private EntityManager em;

    private Enviroment enviroment = Enviroment.JAVA_EE;

    public JpaController(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public JpaController() {

    }

    public <T> T find(Class<T> t, Object id) {
        try {
            if (id != null) {
                return (T) em.find(t, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List findAll(Class t) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e";
            List list = em.createQuery(qry).getResultList();
            if (list.isEmpty()) {
                return new LinkedList();
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List findLast(Class<? extends CommonModel> t, String orderField, int numberOfRecords) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e " + "ORDER BY  e.lastModifiedDate DESC, e." + orderField;

            return this.em.createQuery(qry).setFirstResult(0).setMaxResults(numberOfRecords).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding by last modiefied record", e);
        }

        return Collections.EMPTY_LIST;
    }

    public List findLast(Class<? extends CommonModel> t, String field, Object objectValue, int numberOfRecords) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e "
                    + "WHERE e." + field + " =:val " + "ORDER BY  e.lastModifiedDate DESC";

            return this.em.createQuery(qry)
                    .setParameter("val", objectValue)
                    .setFirstResult(0)
                    .setMaxResults(numberOfRecords).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding by last modiefied record", e);
        }

        return Collections.EMPTY_LIST;
    }

    public boolean delete(Object model) {
        try {

            em.remove(em.merge(model));

            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List loadDataByField(String searchField, Object fieldValue, Class c) {
        try {
            String qry = "SELECT e FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + searchField + " =:fieldValue ";
            return em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List loadOrderdDataByField(String searchField, Object fieldValue, Class c, String orderField) {
        try {
            String qry = "SELECT e FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + searchField + " =:fieldValue "
                    + "ORDER BY e." + orderField;
            return em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public <T> T find(Class<T> t, String field, Object fieldValue) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e "
                    + "WHERE e." + field + " =:fieldValue ";
            return (T) em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue).getSingleResult();
        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Unable to find " + t + " with "+field+" = " + fieldValue, e);
        }

        return null;
    }

    public List loadOrderdData(Class c, OrderBy qo, String orderField, int number) {
        try {
            String qry = "SELECT e FROM " + c.getSimpleName() + " e "
                    + "ORDER BY e." + orderField + " " + qo.getOrderName();
            return em.createQuery(qry)
                    .setFirstResult(0)
                    .setMaxResults(number)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List loadOrderdData(Class c, OrderBy qo, String orderField, String selectionField, Object selectionValue, int number) {
        try {
            String qry = "SELECT e FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + selectionField + " = '" + selectionValue + "' "
                    + "ORDER BY e." + orderField + " " + qo.getOrderName();
            return em.createQuery(qry)
                    .setFirstResult(0)
                    .setMaxResults(number)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List loadAllData(String field, String value, String from, Class c) {
        try {
            String qry = "SELECT e." + from + " FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + field + " = '" + value + "'";
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public List loadAllDataBy(String field, Class c, String where, String criteriaValue) {
        try {
            String qry = "SELECT e." + field + " FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + where + " = '" + criteriaValue + "'";
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public int deleteAll(String field, Object value, Class c) {
        try {
            String qry = "DELETE FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + field + " = :value";
            return em.createQuery(qry)
                    .setParameter("value", value)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Integer count(String field, String value, Class c) {
        try {
            String qry = "SELECT COUNT(e." + field + ") FROM " + c.getSimpleName() + " e "
                    + "WHERE e." + field + " = '" + value + "'";
            Long ct = (Long) em.createQuery(qry).getSingleResult();
            return ct.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List searchByParameterMap(Map<String, Object> params, Class c) {
        String qry = "SELECT e FROM " + c.getSimpleName() + " e WHERE ";

        int itemsCount = params.size();

        int counter = 0;

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            counter++;

            String string = entry.getKey();
            Object object = entry.getValue();
            qry += "e." + string + " LIKE '%" + object + "%' ";

            if (counter < itemsCount) {
                qry += " AND ";
            }

        }

        System.out.println(qry);

        try {
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public Enviroment getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(Enviroment enviroment) {
        this.enviroment = enviroment;
    }

}
