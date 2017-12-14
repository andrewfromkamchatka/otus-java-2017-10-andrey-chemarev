package ru.chemarev.andrey.convertor;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.chemarev.andrey.TestClass;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class MyJsonConverterTest {

    private TestClass original;
    private MyJsonConverter myJsonConverter;

    @Before
    public void initialize() {
        original = new TestClass();
        myJsonConverter = new MyJsonConverter();
    }

    @Test
    public void toJson() throws Exception {
        Gson gson = new Gson();
        String json = myJsonConverter.toJson(original);

        TestClass repaired = gson.fromJson(json, TestClass.class);
        assertEquals(original, repaired);
    }

}