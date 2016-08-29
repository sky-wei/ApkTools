package com.wei.apktools.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by starrysky on 15-1-17.
 */
public class JsonUtils {

    public static String toJsonString(Object object) {

        if (object == null) return null;

        Gson gson = new Gson();

        return gson.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> classz) {

        if (json == null || classz == null) return null;

        Gson gson = new Gson();

        return gson.fromJson(json, classz);
    }

    public static <T> T fromJson(String json, Type type) {

        if (json == null || type == null) return null;

        Gson gson = new Gson();

        return gson.fromJson(json, type);
    }
}
