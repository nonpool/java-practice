package com.nonpool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ObjectToJsonApplication {

    public String convert(Object any) throws RuntimeException {
        // todo
        Class<?> anyClass = any.getClass();
        boolean includeNullField = isIncludeNullField(anyClass);
        Method[] declaredMethods = anyClass.getDeclaredMethods();

        StringBuilder stringBuilder = new StringBuilder("{");
        for (Method method : declaredMethods) {
            String methodName = method.getName();
            String fieldName = methodName.substring(3);
            String s1 = fieldName.substring(0, 1).toLowerCase();
            String s2 = fieldName.substring(1);
            fieldName = s1 + s2;
            if (methodName.startsWith("get")) {
                try {
                    Object field = method.invoke(any);
                    if (includeNullField  || field != null ) {
                        String fieldValue = field == null ? null : ("\"" + field + "\"");
                        stringBuilder.append(",\"").append(fieldName).append("\":").append(fieldValue);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        stringBuilder.append("}");
        return String.valueOf(stringBuilder).replaceFirst(",", "");
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