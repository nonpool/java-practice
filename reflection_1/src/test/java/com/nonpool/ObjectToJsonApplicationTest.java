package com.nonpool;

import com.nonpool.pojo.ClassA;
import com.nonpool.pojo.ClassB;
import com.nonpool.pojo.ClassC;
import com.nonpool.pojo.GetterClass;
import com.nonpool.pojo.NumberFieldClass;
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

    @Test
    void convert_with_number_field() {
        final NumberFieldClass numberFieldClass = new NumberFieldClass(1000L, 28);
        final String getterClassJsonStr = objectToJsonApplication.convert(numberFieldClass);

        assertEquals("{\"id\":1000,\"age\":28}", getterClassJsonStr);

    }

    @Test
    void convert_with_null_value_field() {
        final ClassA classA = new ClassA("ABC", null);
        final String classAJsonStr = objectToJsonApplication.convert(classA);

        assertEquals("{\"id\":\"ABC\",\"name\":null}", classAJsonStr);

        final ClassC classC = new ClassC("ABC", null);
        final String classCJsonStr = objectToJsonApplication.convert(classC);
        assertEquals("{\"id\":\"ABC\"}", classCJsonStr);

    }
}