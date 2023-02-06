package com.nonpool;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonApplication {

    public String convert(Object any) {
        try {
            return help(any);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String help(Object any) throws Exception {
        final Class<?> anyClass = any.getClass();
        final BeanInfo beanInfo = Introspector.getBeanInfo(anyClass);
        final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        final HashMap<String, Integer> fieldOrderMap = new HashMap<>();
        final Field[] declaredFields = anyClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            fieldOrderMap.put(declaredFields[i].getName(), i);
        }

        final List<ClassReflectionInfo> list = new ArrayList<>();
        for (PropertyDescriptor pd : propertyDescriptors) {
            if (!pd.getName().equals("class")) {
                final Object invokeValue = pd.getReadMethod().invoke(any);
                final ClassReflectionInfo classReflectionInfo = new ClassReflectionInfo(
                        pd.getName(), invokeValue, fieldOrderMap.getOrDefault(pd.getName(), Integer.MAX_VALUE));
                list.add(classReflectionInfo);
            }
        }

        final String collect = list.stream()
                .sorted(Comparator.comparingInt(ClassReflectionInfo::getOrder))
                .map(ClassReflectionInfo::toString)
                .collect(Collectors.joining(","));


        return "{" + collect + "}";
    }

}

class ClassReflectionInfo {
    private final String filedName;
    private final Object value;
    private final Integer order;

    public ClassReflectionInfo(String filedName, Object value, Integer order) {
        this.filedName = filedName;
        this.value = value;
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }

    public String toString() {
        return "\"" + getFiledName() + "\"" + ":" + getValue();
    }

    public String getFiledName() {
        return filedName;
    }


    public Object getValue() {
        if (Objects.isNull(value)) {
            return null;
        }

        if (value instanceof String) {
            return "\"" + value + "\"";
        }

        return value;
    }
}