/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jpa;

import com.latlab.common.dateutils.DateRange;
import com.latlab.common.formating.ObjectValue;
import com.latlab.common.model.NumberRange;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.joda.time.LocalDate;

/**
 *
 * @author ICSGH-BILLY
 */
public class QryHelper implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(QryHelper.class.getName());
    private EntityManager em;
    private boolean showLog = false;

    private String qryString;
    private String className;

    private String currentQryGroup = null;

    public static enum DDML {

        SELECT, COUNT,
    }

    private List<String> keys = Arrays.asList("SUM", "COUNT", "AVG", "GROUP");

    private List<QryParam> qryParams = new LinkedList<>();
//    private Map<String, List<QryParam>> qryMaps = new LinkedHashMap<>();
    private List<QryOrder> ordering = new LinkedList<>();
    private List<String> returnFieldsList = new LinkedList<>();
    private List<String> groupFields = new LinkedList<>();

    private String var = "e";

    private DDML ddml = DDML.SELECT;

    public QryHelper() {
    }

    public QryHelper(EntityManager em) {
        this.em = em;
    }

    public QryHelper(EntityManager em, Class rootClass) {
        this.em = em;
        setQryClass(rootClass);
    }

    public QryHelper(EntityManager em, Class rootClass, String returnVariable) {
        this.em = em;
        setQryClass(rootClass);
        returnFieldsList.add(var + "." + returnVariable);

    }

    public static QryHelper get(EntityManager em) {
        return new QryHelper(em);
    }

    public String getCurrentQryGroup() {
        return currentQryGroup;
    }

    public void setCurrentQryGroup(String currentQryGroup) {
        this.currentQryGroup = currentQryGroup;
    }

    private void setQryClass(Class qryClass) {
        className = qryClass.getSimpleName();
    }

    public QryHelper add(QryParam param) {
        if (currentQryGroup != null) {
            if (showLog) {
                LOGGER.log(Level.INFO, "Current QryGroup is not null, adding {0}", currentQryGroup);
            }
            param.setQryGroup(currentQryGroup);
        }
        if (showLog) {
            System.out.println("QRY_GROUP = " + currentQryGroup);
        }
        qryParams.add(param);
        return this;
    }

    public void map(Map<String, Object> values) {
        map(values, false);
    }

    public void map(Map<String, Object> values, boolean order) {
        if (values == null) {
            return;
        }

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                addStringQryParam(field, value, QryHelper.ComparismCriteria.EQUAL);
            } else if (value instanceof Date) {
                addDateParam(field, (Date) value, ComparismCriteria.EQUAL);
            } else if (value instanceof LocalDate) {
                //TODO localdate instance
                addLocalDateParam(field, (LocalDate) value, ComparismCriteria.LIKE);
            } else {
                addObjectParam(field, value);
            }

            if (order) {
                orderByAsc(field);
            }

        }
    }

    public QryHelper addStringQryParam(String field, Object value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.String, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryHelper addStringQryParam(String field, Object value, ComparismCriteria comparismCriteria, IncludeCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.String, comparismCriteria, includeCriteria);
        if (showLog) {
            System.out.println("going to add = " + qryParam);
        }
        add(qryParam);
        return this;
    }

    public QryHelper addDateParam(String field, Date value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.Date, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryHelper addLocalDateParam(String field, LocalDate value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.LocalDate, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryHelper addNumberParam(String field, Number value, ComparismCriteria includeCriteria) {
        QryParam qryParam = new QryParam(field, value, FieldType.Number, includeCriteria);
        add(qryParam);
        return this;
    }

    public QryHelper addDateRange(DateRange dateRange, String fieldName) {
        if (dateRange != null) {
            if (dateRange.getFromDate() != null) {
                addDateParam(fieldName, dateRange.getFromDate(), QryHelper.ComparismCriteria.GREATER_THAN_OR_EQUAL);
            }
            if (dateRange.getToDate() != null) {
                addDateParam(fieldName, dateRange.getToDate(), QryHelper.ComparismCriteria.LESS_THAN_OR_EQUAL);
            }
        }
        return this;
    }

    public QryHelper addRange(Number start, Number end, String field) {

        if (start != null) {
            addNumberParam(field, start, ComparismCriteria.GREATER_THAN_OR_EQUAL);
        }

        if (end != null) {
            addNumberParam(field, end, ComparismCriteria.LESS_THAN_OR_EQUAL);
        }

        return this;
    }

    public QryHelper addNumberRange(NumberRange numberRange, String field) {
        if (numberRange == null) {
            return this;
        }

        if (numberRange.getMinimum() != null) {
            addNumberParam(field, numberRange.getMinimum(), ComparismCriteria.GREATER_THAN_OR_EQUAL);
        }

        if (numberRange.getMaximum() != null) {
            addNumberParam(field, numberRange.getMaximum(), ComparismCriteria.LESS_THAN_OR_EQUAL);
        }

        return this;
    }

    public QryHelper addObjectParam(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.OBJECT, ComparismCriteria.EQUAL);
        add(qryParam);
        return this;
    }

    public QryHelper addInParam(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.OBJECT, ComparismCriteria.IN);
        add(qryParam);
        return this;
    }

    public QryHelper addObjectParamNotEqual(String field, Object value) {
        QryParam qryParam = new QryParam(field, value, FieldType.OBJECT, ComparismCriteria.NOT);
        add(qryParam);
        return this;
    }

    public QryHelper addReturnField(String field) {
        returnFieldsList.add(field);
        return this;
    }

    /*
     * the field eg. valueDate
     */
    public QryHelper addGroupBy(String field) {
        groupFields.add(field);
        return this;
    }

    public QryHelper orderBy(QryOrder qryOrder) {
        ordering.add(qryOrder);
        return this;
    }

    public QryHelper orderByAsc(String field) {
        ordering.add(new QryOrder(field, OrderBy.ASC));
        return this;
    }

    public QryHelper orderByDesc(String field) {
        ordering.add(new QryOrder(field, OrderBy.DESC));
        return this;
    }

//    private void buildQryMap()
//    {
//        qryMaps.clear();
//        for (QryParam qryParam : qryParams)
//        {
//            if (!qryMaps.containsKey(qryParam.getQryGroup()))
//            {
//                List<QryParam> params = new LinkedList<>();
//                qryMaps.put(qryParam.getQryGroup(), params);
//            }
//            List<QryParam> params = qryMaps.get(qryParam.getQryGroup());
//            params.add(qryParam);
//        }
//    }
    public String getQryInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(getQryString()).append("\n");
        builder.append(getParamString());

        return builder.toString();
    }

    public String getParamString() {
        StringBuilder builder = new StringBuilder();

        for (QryParam qryParam : qryParams) {
            builder.append(qryParam.getField()).append(" : ").append(qryParam.getValue()).append("\n");
        }

        return builder.toString();
    }

    public String getFinalString() {
        return qryString;
    }

    public String getQryString() {
        //forming select group

        if (returnFieldsList.isEmpty()) {
            qryString = "SELECT e FROM " + className + " e ";
        } else {
            String obj = "";
            int size = returnFieldsList.size();
            int counter = 0;
            for (String object : returnFieldsList) {
                counter++;
                obj += object;

                if (counter != size) {
                    obj += ",";
                }
            }
            qryString = "SELECT " + obj + " FROM " + className + " e ";
        }

        buildParameterString();

        return qryString;

    }

    public String buildParameterString() {

        if (qryParams.size() > 0) {
            qryString = qryString + " WHERE ";
        }

        if (showLog) {
            System.out.println("LIST IS " + qryParams);
            System.out.println("param size = " + qryParams.size());
        }

//        buildQryMap();
//            whereGroupCounter = whereGroupCounter + 1;
//            String string = entry.getKey();
//            List<QryParam> groupQryParams = entry.getValue();
//            if(groupQryParams.isEmpty())
//            {
//                continue;
//            }
//            
//            int criteriaParamSize = groupQryParams.size();
        int counter = 0;
//            qryString = qryString + "(";

        for (QryParam qryParam : qryParams) {
            if (qryParam.getValue() == null && qryParam.comparism == ComparismCriteria.NOT) {
                qryString += "e." + qryParam.field + " IS NOT NULL ";
            } else if (qryParam.getValue() == null) {
                qryString += "e." + qryParam.field + " IS NULL ";
            } else {
                if (qryParam.fieldType == FieldType.String) {
                    if (qryParam.comparism == ComparismCriteria.LIKE) {
                        qryString += "e." + qryParam.field + " LIKE '%" + qryParam.value + "%' ";
                    }

                    if (qryParam.comparism == ComparismCriteria.EQUAL) {
                        qryString += "e." + qryParam.field + " = '" + qryParam.value + "' ";
                    }
                }
                if (qryParam.fieldType == FieldType.Number) {
                    if (qryParam.comparism != ComparismCriteria.LIKE) {
                        qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + " " + qryParam.value + " ";
                    }
                }

                if (qryParam.fieldType == FieldType.Date) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                if (qryParam.fieldType == FieldType.LocalDate) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                if (qryParam.fieldType == FieldType.OBJECT) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }
            }

            qryParam.used = true;
            counter++;

            if (counter < qryParams.size()) {
                qryString += " AND ";
            }
        }

        //forming group
        int groupCounter = 0;
        int groupSize = groupFields.size();
        if (groupFields.isEmpty() == false) {
            qryString += " GROUP BY ";

            for (String groupField : groupFields) {
                qryString += " e." + groupField + " ";

                groupCounter++;

                if (groupCounter != groupSize) {
                    qryString += " , ";
                }
            }
        }

        //forming order by
        int orderCounter = 0;
        int orderSize = ordering.size();
        if (ordering.isEmpty() == false) {
            qryString += " ORDER BY ";

            for (QryOrder qryOrder : ordering) {

                if (qryOrder.orderField.contains("(") && qryOrder.orderField.contains(")")) {
                    qryOrder.addVariable = false;
                }

                if (qryOrder.addVariable) {
                    qryString += " " + var + "." + qryOrder.orderField + " " + qryOrder.orderBy.getOrderName();
                } else {
                    qryString += " " + qryOrder.orderField + " " + qryOrder.orderBy.getOrderName();
                }

                orderCounter++;

                if (orderCounter != orderSize) {
                    qryString += " , ";
                }
            }
        }

        return qryString;
    }

    public Query applyParameters(Query query) {

        for (QryParam qryParam : qryParams) {
            query = applyParameters(query, qryParam);
        }

        return query;
    }

    public Query applyParameters(Query query, QryParam qryParam) {
        return QryHelper.applyQueryParameters(query, qryParam);
    }

    public static Query applyQueryParameters(Query query, QryParam qryParam) {
        if (qryParam.getValue() == null) {
            return query;
        }
        if (qryParam.getFieldType() == FieldType.Date) {
            query.setParameter(qryParam.paramVar, (Date) qryParam.getValue(), TemporalType.DATE);
        }
        if (qryParam.getFieldType() == FieldType.OBJECT) {
            query.setParameter(qryParam.paramVar, qryParam.getValue());
        }

        return query;
    }

    public int count(Map<String, Object> map) {

        try {
            for (String key : map.keySet()) {
                addObjectParam(key, map.get(key));
            }

            qryString = "SELECT COUNT(e) FROM " + className + " e ";

            buildParameterString();

            Query query = em.createQuery(qryString);
            applyParameters(query);

            if (showLog) {
                System.out.println(qryString);
                System.out.println(getParamString());

            }

            return ObjectValue.get_intValue(query.getSingleResult());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int count() {
        return count(new HashMap<String, Object>());
    }

    public int delete(Map<String, Object> map) {

        try {
            for (String key : map.keySet()) {
                addObjectParam(key, map.get(key));
            }

            qryString = "DELETE FROM " + className + " " + var + " ";

            buildParameterString();

            Query query = em.createQuery(qryString);
            applyParameters(query);

            if (showLog) {
                System.out.println(qryString);
                System.out.println(getParamString());

            }

            return ObjectValue.get_intValue(query.executeUpdate());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int delete() {
        return delete(new HashMap<String, Object>());
    }

    public Query buildQry() {

        qryString = getQryString();

        if (showLog) {
            System.out.println(qryString);
        }

        if (em == null) {
            LOGGER.log(Level.SEVERE, "Entity Manager could not be initialised");
        }

        Query query = em.createQuery(qryString);

        applyParameters(query);

        return query;
    }

    public void showLog(boolean showLog) {
        this.showLog = showLog;
    }

    public enum ComparismCriteria {

        LIKE(" % "),
        EQUAL(" = "),
        LESS_THAN(" < "),
        GREATER_THAN(" > "),
        GREATER_THAN_OR_EQUAL(" >= "),
        LESS_THAN_OR_EQUAL(" <= "),
        NOT(" != "),
        IN(" IN ");

        ComparismCriteria(String sign) {
            this.sign = sign;
        }

        private String sign;

        private String getSign() {
            return sign;
        }

    }

    public enum IncludeCriteria {

        AND("AND"),
        OR("OR");

        IncludeCriteria(String sign) {
            this.sign = sign;
        }

        private String sign;

        public String getSign() {
            return sign;
        }

    }

    public enum FieldType {
        String, Number, Date, OBJECT, LocalDate
    }

    public static class QryOrder implements Serializable {

        private String orderField;
        private boolean addVariable = true;
        private OrderBy orderBy;

        public QryOrder() {
        }

        public QryOrder(String orderField, OrderBy orderBy) {
            this.orderField = orderField;
            this.orderBy = orderBy;
        }

        public QryOrder(String orderField, OrderBy orderBy, boolean addVariable) {
            this.orderField = orderField;
            this.orderBy = orderBy;
            this.addVariable = addVariable;
        }

    }

    public static class QryParam implements Serializable {

        private Map<String, Object> parameters = null;

        public static final String DEFAULT_QRY_GROUP = "default";
        private boolean used;
        private String field;
        private String qryString = "";
        private Object value;
        private FieldType fieldType;
        private ComparismCriteria comparism;
        private String qryGroup = DEFAULT_QRY_GROUP;
        private IncludeCriteria includeCriteria = IncludeCriteria.AND;

        private String paramVar;
        private QryParam qryParam;

        public QryParam(String field, Object value, FieldType fieldType, ComparismCriteria includeCriteria) {
            this.field = field;
            this.value = value;
            this.fieldType = fieldType;
            this.comparism = includeCriteria;

            paramVar = field.replaceAll("\\.", "") + Math.abs(Objects.hashCode(value));
            qryParam = this;

            build();

        }

        public QryParam(String field, Object value, FieldType fieldType, ComparismCriteria comparismCriteria, IncludeCriteria includeCriteria1) {
            this.field = field;
            this.value = value;
            this.fieldType = fieldType;
            this.comparism = comparismCriteria;
            this.includeCriteria = includeCriteria1;

            paramVar = field.replaceAll("\\.", "") + Math.abs(Objects.hashCode(value));

            qryParam = this;

            build();
        }

        private QryParam(String name, Object value) {
            this.parameters = new HashMap<>();
            this.parameters.put(name, value);

            qryParam = this;

            build();
        }

        public static QryParam with(String name, Object value) {
            return new QryParam(name, value);
        }

        public QryParam and(String name, Object value) {
            this.parameters.put(name, value);
            return this;
        }

        public Map<String, Object> parameters() {
            return this.parameters;
        }

        public boolean isUsed() {
            return used;
        }

        public String getField() {
            return field;
        }

        public Object getValue() {
            return value;
        }

        public FieldType getFieldType() {
            return fieldType;
        }

        public ComparismCriteria getComparism() {
            return comparism;
        }

        public String getQryGroup() {
            return qryGroup;
        }

        public void setQryGroup(String qryGroup) {
            this.qryGroup = qryGroup;
        }

        public String getQryString() {
            return qryString;
        }

        public void setQryString(String qryString) {
            this.qryString = qryString;
        }

        @Override
        public String toString() {
            return "QryParam{ field=" + field + ", value=" + value + ", comparism=" + comparism + '}';
        }

        public void build() {
            if (qryParam.getValue() == null && qryParam.comparism == ComparismCriteria.NOT) {
                qryString += "e." + qryParam.field + " IS NOT NULL ";
            } else if (qryParam.getValue() == null) {
                qryString += "e." + qryParam.field + " IS NULL ";
            } else {
                if (qryParam.fieldType == FieldType.String) {
                    if (qryParam.comparism == ComparismCriteria.LIKE) {
                        qryString += "e." + qryParam.field + " LIKE '%" + qryParam.value + "%' ";
                    }

                    if (qryParam.comparism == ComparismCriteria.EQUAL) {
                        qryString += "e." + qryParam.field + " = '" + qryParam.value + "' ";
                    }
                }
                if (qryParam.fieldType == FieldType.Number) {
                    if (qryParam.comparism != ComparismCriteria.LIKE) {
                        qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + " " + qryParam.value + " ";
                    }
                }

                if (qryParam.fieldType == FieldType.Date) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                //TODO just added for localdate field type
                if (qryParam.fieldType == FieldType.LocalDate) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }

                if (qryParam.fieldType == FieldType.OBJECT) {
                    qryString += "e." + qryParam.field + " " + qryParam.comparism.getSign() + ":" + qryParam.paramVar + " ";
                }
            }

        }

    }

    public static class ParamQry {

        private String qryExpression;

        public void dateRangeExpression() {
//            d
        }
    }

    public static enum OrderBy implements Serializable {

        ASC("ASC"), DESC("DESC");
        private String orderName;

        private OrderBy(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderName() {
            return orderName;
        }
    }

    public static class QrySearchOrder implements Serializable {

        private String searchField;
        private OrderBy orderBy;

        public java.lang.String getSearchField() {
            return searchField;
        }

        public void setSearchField(java.lang.String searchField) {
            this.searchField = searchField;
        }

        public OrderBy getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(OrderBy orderBy) {
            this.orderBy = orderBy;
        }

        public static List<QryHelper.QrySearchOrder> createOrderList(int number) {
            List<QryHelper.QrySearchOrder> qrySearchOrdersList = new LinkedList<>();
            for (int i = 0; i < number; i++) {
                qrySearchOrdersList.add(new QrySearchOrder());
            }

            return qrySearchOrdersList;
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult(Class<T> t) {
        try {
            List<T> list = buildQry().getResultList();

            if (list != null && list.size() == 1) {
                return list.get(0);
            }

        } catch (Exception e) {
            String msg = "Error finding loading single result of class " + t.getName() + " - " + e.toString();
            LOGGER.log(Level.SEVERE, msg);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getSingleResult2(Class<T> t) {
        try {
            List<T> list = buildQry().getResultList();

            if (list != null && list.size() == 1) {
                return list.get(0);
            }
        } catch (Exception e) {
            String msg = "Error finding loading single result of class " + t.getName() + " - " + e.toString();
            LOGGER.log(Level.SEVERE, msg);
        }

        return null;
    }

    public static QryHelper get(EntityManager em, Class rootClass) {
        QryHelper qryBuilder = new QryHelper(em, rootClass);

        return qryBuilder;
    }

    public static void main(String[] args) {
        String param = "dd(dsd)";
        String regrex = "\\([a-zA-Z ]+\\)";

        boolean matches = param.matches(regrex);

        System.out.println(matches);
    }

    /**
     *
     */
    public List findAllOrderAsc(Class t, String orderField) {
        try {
            String qry = "SELECT e FROM " + t.getSimpleName() + " e "
                    + "ORDER BY e." + orderField;
            return em.createQuery(qry).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.EMPTY_LIST;
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
}
