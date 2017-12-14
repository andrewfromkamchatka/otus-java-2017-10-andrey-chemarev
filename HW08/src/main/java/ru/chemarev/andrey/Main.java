package ru.chemarev.andrey;

import ru.chemarev.andrey.convertor.ConverterException;
import ru.chemarev.andrey.convertor.JsonDateConverter;
import ru.chemarev.andrey.convertor.MyJsonConverter;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try {
            MyJsonConverter converter = new MyJsonConverter();
            TestClass3 testClass3 = new TestClass3();

            System.out.println( converter.toJson(testClass3) );
            converter.addCustomConverter(LocalDateTime.class, new JsonDateConverter());
            System.out.println( converter.toJson(testClass3) );

        } catch (ConverterException e) {
            e.printStackTrace();
        }
    }

}
