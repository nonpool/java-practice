package com.nonpool;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ObjectToJsonApplication {

    public String convert(Object any) throws RuntimeException {
        // todo
        Class<?> anyClass = any.getClass();
        boolean includeNullField = isIncludeNullField(anyClass);

        HashMap<String, Integer> fieldsOrderMap = new HashMap<>();
        Field[] declaredFields = anyClass.getDeclaredFields();
        int fieldsLength = declaredFields.length;
        for (int i = 0; i < fieldsLength; i++) {
            fieldsOrderMap.put(declaredFields[i].getName(), i);
        }

        List<FieldsWithOrder> fieldsWithOrders = new ArrayList<>();
        try {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(anyClass).getPropertyDescriptors();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Object fieldValue = propertyDescriptor.getReadMethod().invoke(any);
                String fieldName = propertyDescriptor.getName();
                if (!fieldName.equals("class")) {
                    FieldsWithOrder fieldsWithOrder = new FieldsWithOrder(fieldName, fieldValue, fieldsOrderMap.getOrDefault(fieldName, fieldsLength + 1));
                    fieldsWithOrders.add(fieldsWithOrder);
                }
            }

            String collect = fieldsWithOrders
                    .stream()
                    .filter(s-> s.getFieldValue() !=null ||includeNullField==true )
                    .sorted(Comparator.comparingInt(FieldsWithOrder::getOrder))
                    .map(FieldsWithOrder::toString)
                    .collect(Collectors.joining(","));

            return "{"+collect+"}";
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isIncludeNullField(Class<?> anyClass) {
        boolean annotationPresent = anyClass.isAnnotationPresent(ToJsonConfig.class);
        boolean includeNullField = true;
        if (annotationPresent) {
            ToJsonConfig toJsonConfig = anyClass.getAnnotation(ToJsonConfig.class);
            includeNullField = toJsonConfig.includeNullField();
        }
        return includeNullField;
    }


}