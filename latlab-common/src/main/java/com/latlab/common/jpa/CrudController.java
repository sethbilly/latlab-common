package com.latlab.common.jpa;

import com.latlab.common.formating.ObjectValue;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

public class CrudController implements Serializable {

    private EntityManager em;

    private Enviroment enviroment = Enviroment.JAVA_EE;

    public String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public CrudController(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    private static final Logger LOGGER = Logger.getLogger(CrudController.class.getName());

//    private String lastActivityExceptionMessage = "";
    private String currentUserID;
//    private Exception lastActivityException = null;

    public CrudController() {

    }

    public void setCurrentUserID(String currentUserID) {
        this.currentUserID = currentUserID;
    }

    @SuppressWarnings("unchecked")
    public <T> T findBySingleField(Class<T> clazz, String field, String value) {

        try {
            String qry = "SELECT e FROM " + clazz.getSimpleName() + " e "
                    + "WHERE e." + field + " = '" + value + "'";

            return (T) em.createQuery(qry).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T find(Class<T> t, Object id) {
        if (id == null) {
            return null;
        }

        try {

            if (em == null) {
                LOGGER.info("Entity Manager is not initialised");
                return null;
            }
            return (T) em.find(t, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding " + t.getName() + " with ID " + id, e);
        }

        return null;
    }

    public List findAll(Class t) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e";
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all " + t, e);
        }

        return Collections.EMPTY_LIST;
    }
    
    public List findAll(Class t, boolean deleted) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e "
                    + "WHERE e.deleted = :deleted ";
            return em.createQuery(qry)
                    .setParameter("deleted", deleted).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all " + t, e);
        }

        return Collections.EMPTY_LIST;
    }

    public int count(Class t) {
        return count(t, null);
    }

    public int count(Class t, Map<String, Object> values) {
        try {
            QryHelper qryBuilder = new QryHelper(em, t);
            qryBuilder.addReturnField("COUNT(e)");

            if (values != null) {
                qryBuilder.map(values);
            }

            return ObjectValue.getIntegerValue(qryBuilder.buildQry().getSingleResult());

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding all " + t, e);
        }

        return 0;
    }

    public <T> T save(Object model) {
        try {
            return saveEntity(model);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving model", e);
        }
        return null;
    }

    public List recentlyModified(Class clazz, int maxResult) {
        try {
            QryHelper builder = new QryHelper(em, clazz);
            builder.orderByDesc(CommonModel._lastModifiedDate);

            return builder.buildQry().setFirstResult(0).setMaxResults(maxResult).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving model", e);
        }
        return Collections.EMPTY_LIST;
    }

    public <T> T save(CommonModel model) {

        try {
            if (model.getCreatedDate() == null) {
                model.setCreatedDate(new Date());
            }
            model.setLastModifiedDate(new Date());
            if (currentUserID != null) {
                model.setLastModifiedBy(currentUserID);
                model.setCreatedBy(currentUserID);
            }

            model.setUpdated(false);

            if (enviroment == Enviroment.JAVA_SE) {
                if (em.getTransaction().isActive() == false) {
                    em.getTransaction().begin();
                }
            }
            if (model.getId() == null) {
                model.setId(UUID.randomUUID().toString().replace("-", ""));
                em.persist(model);

            } else if (find(model.getClass(), model.getId()) != null) {
                em.merge(model);

            } else {
                em.persist(model);
            }

            em.flush();

            if (enviroment == Enviroment.JAVA_SE) {
                em.getTransaction().commit();
            }

            return (T) model;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public <T> T saveEntity(Object object) {
        if (enviroment == Enviroment.JAVA_SE) {
            if (em.getTransaction().isActive() == false) {
                em.getTransaction().begin();
            }
        }

        em.merge(object);
        em.flush();

        if (enviroment == Enviroment.JAVA_SE) {
            em.getTransaction().commit();
        }

        return (T) object;
    }

    public boolean update(CommonModel model) {
        try {
            if (model.getCreatedDate() == null) {
                model.setCreatedDate(new Date());
            }
            model.setLastModifiedDate(new Date());
            model.setDeleted(false);
            model.setUpdated(true);

            CommonModel entityModel = saveEntity(model);

            if (entityModel != null) {
                return true;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving model", e);
        }

        return false;
    }

    public boolean update(Object model) {
        try {

            Object entityModel = saveEntity(model);

            if (entityModel != null) {
                return true;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving model", e);
        }

        return false;
    }

    public boolean updateEntity(Object model) {
        try {

            Object entityModel = saveEntity(model);

            if (entityModel != null) {
                return true;
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error saving model", e);
        }

        return false;
    }

    public boolean delete(CommonModel model, boolean permanent) {
        try {
            if (enviroment == Enviroment.JAVA_SE) {
                if (em.getTransaction().isActive() == false) {
                    em.getTransaction().begin();
                }
            }

            if (permanent == true) {
                if (model.getId() != null) {
                    em.remove(em.merge(model));

                    LOGGER.log(Level.INFO, " Deleted : {0}", model);
                }

            } else if (permanent == false) {
                model.setLastModifiedDate(new Date());

                save(model);

                model.setLastModifiedBy(currentUserID);
                model.setDeleted(true);
                model.setUpdated(false);
                em.merge(model);
            }

            em.flush();

            if (enviroment == Enviroment.JAVA_SE) {
                em.getTransaction().commit();
            }

            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting model", e);
        }

        return false;
    }

    public boolean delete(Object model) {
        try {
            if (enviroment == Enviroment.JAVA_SE) {
                if (em.getTransaction().isActive() == false) {
                    em.getTransaction().begin();
                }
            }

            em.remove(em.merge(model));

            LOGGER.log(Level.INFO, " Deleted : {0}", model);

            if (enviroment == Enviroment.JAVA_SE) {
                em.flush();
                em.getTransaction().commit();
            }

            return true;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting model", e);
        }

        return false;
    }

    public int bulkDelete(Class clazz, String field, Object fieldValue) {
        try {

            if (enviroment == Enviroment.JAVA_SE) {
                if (em.getTransaction().isActive() == false) {
                    em.getTransaction().begin();
                }
            }

            String qry = "DELETE FROM " + clazz.getSimpleName() + " e "
                    + "WHERE e." + field + " =:fieldValue";

            System.out.println("Bulk Delete Qry is  " + qry);

            int numberDeleted = em.createQuery(qry)
                    .setParameter("fieldValue", fieldValue)
                    .executeUpdate();

            if (enviroment == Enviroment.JAVA_SE) {
                em.getTransaction().commit();
            }

            return numberDeleted;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error deleting model", e);
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

    public List loadOrderdData(Class c, QryHelper.OrderBy qo, String orderField, int number) {
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

    public List loadOrderdData(Class c, QryHelper.OrderBy qo, String orderField, String selectionField, Object selectionValue, int number) {
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

    public Enviroment getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(Enviroment enviroment) {
        this.enviroment = enviroment;
    }

}
