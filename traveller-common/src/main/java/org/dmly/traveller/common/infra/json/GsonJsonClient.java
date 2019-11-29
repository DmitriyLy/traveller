package org.dmly.traveller.common.infra.json;

import com.google.gson.Gson;

public class GsonJsonClient implements JsonClient {

    private static final Gson GSON = new Gson();

    @Override
    public <T> String toJson(T t) {
        return GSON.toJson(t);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clz) {
        return GSON.fromJson(json, clz);
    }
}
