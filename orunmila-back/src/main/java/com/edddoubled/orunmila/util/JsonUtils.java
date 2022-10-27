package com.edddoubled.orunmila.util;

import com.google.gson.Gson;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {
    private static final Gson gson = new Gson();

    public static <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String serialize(Object object) {
        return gson.toJson(object);
    }


}
