package ru.chemarev.andrey.convertor;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyJsonConverter {
    private Map<Class, CustomConverter> customConverters;

    public MyJsonConverter() {
        customConverters = new HashMap<>();
    }

    public String toJson(Object obj) throws ConverterException {

        if (obj.getClass().isPrimitive())
            throw new NotImplementedException();
        else if (obj.getClass().isArray())
            return arrayToJson(obj).toJSONString();
        else
            return objectToJson(obj).toJSONString();

    }

    JSONObject objectToJson(Object obj) throws ConverterException {
        JSONObject result = new JSONObject();

        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            try {
                Object value = field.get(obj);

                CustomConverter customConverter = customConverters.get(value.getClass());

                if (customConverter != null)
                    value = customConverter.convert(value);

                else if (value.getClass().isArray())
                    value = arrayToJson(value);

                else if (List.class.isAssignableFrom(value.getClass()))
                    value = arrayToJson(((List) value).toArray());

                else if (Map.class.isAssignableFrom(value.getClass())) {
                    JSONObject newValue = new JSONObject();
                    for (Object entry : ((Map) value).entrySet())
                        newValue.put(((Map.Entry) entry).getKey(), objectToJson(((Map.Entry) entry).getValue()));
                    value = newValue;
                }

                result.put(field.getName(), value);

            } catch (IllegalAccessException e) {
                throw new ConverterException(e);
            }

            field.setAccessible(false);
        }

        return result;
    }

    JSONArray arrayToJson(Object obj) throws ConverterException {
        JSONArray result = new JSONArray();

        for (int i = 0; i < Array.getLength(obj); i++) {
            Object value = Array.get(obj, i);

            if (value.getClass().isArray())
                value = arrayToJson(value);

            else if (!isPrimitive(value.getClass()) && value.getClass() != String.class)
                value = objectToJson(value);

            result.add(value);
        }

        return result;
    }

    private boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive() ||
                clazz == Double.class ||
                clazz == Float.class ||
                clazz == Long.class ||
                clazz == Integer.class ||
                clazz == Short.class ||
                clazz == Character.class ||
                clazz == Byte.class ||
                clazz == Boolean.class;
    }

    public void addCustomConverter(Class clazz, CustomConverter converter) {
        customConverters.put(clazz, converter);
    }

}
