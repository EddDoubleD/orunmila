package com.edddoubled.orunmila.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {
    /**
     * The default behaviour that is implemented in Gson is that null object fields are ignored.
     */
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static <T> T deserialize(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String serialize(Object object) {
        return gson.toJson(object);
    }
}
