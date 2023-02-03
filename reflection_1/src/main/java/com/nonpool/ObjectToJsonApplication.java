package com.nonpool;

import java.lang.reflect.Field;

public class ObjectToJsonApplication {

    public String convert(Object any) {
        // todo
        Class<?> anyClass = any.getClass();
        Field[] fields = anyClass.getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder("{");
        for (Field field : fields) {
            String name = field.getName();
            field.setAccessible(true);
            try {
                Object value = field.get(any);
                stringBuilder.append(',').append('"').append(name).append('"').append(":").append('"').append(value).append('"');
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        stringBuilder.append("}");

        return String.valueOf(stringBuilder).replaceFirst(",","");
    }

}