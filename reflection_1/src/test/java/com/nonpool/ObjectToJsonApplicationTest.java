package com.nonpool;

import com.nonpool.pojo.ClassA;
import com.nonpool.pojo.ClassB;
import com.nonpool.pojo.GetterClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectToJsonApplicationTest {
    final ObjectToJsonApplication objectToJsonApplication = new ObjectToJsonApplication();

    @Test
    void convert() {
        final ClassA classA = new ClassA("ABC", "DDD");
        final String classAJsonStr = objectToJsonApplication.convert(classA);

        assertEquals("{\"id\":\"ABC\",\"name\":\"DDD\"}", classAJsonStr);

        final ClassB classB = new ClassB("si", "li");
        final String classBJsonStr = objectToJsonApplication.convert(classB);
        assertEquals("{\"firstName\":\"si\",\"lastName\":\"li\"}", classBJsonStr);

    }

    @Test
    void convert_with_getter() {
        final GetterClass getterClass = new GetterClass("si", "li");
        final String getterClassJsonStr = objectToJsonApplication.convert(getterClass);

        assertEquals("{\"firstName\":\"si\",\"lastName\":\"LI\",\"name\":\"LI·si\"}", getterClassJsonStr);

    }

}