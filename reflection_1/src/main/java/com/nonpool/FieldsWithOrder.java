package com.nonpool;

public class FieldsWithOrder {
    private final String fieldName;
    private final Object fieldValue;
    private final Integer order;

    public FieldsWithOrder(String fieldName, Object fieldValue, Integer order) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        if (fieldValue == null) {
            return  null;
        }

        if (fieldValue instanceof String) {
            return "\"" + fieldValue + "\"";
        }
        return fieldValue;

    }

    public Integer getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "\""+fieldName+"\":"+getFieldValue();
    }
}
