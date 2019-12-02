package org.dmly.traveller.common.infra.json;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.dmly.traveller.common.infra.exception.flow.ValidationException;

public class GsonJsonClient implements JsonClient {

    private static final Gson GSON = new Gson();

    @Override
    public <T> String toJson(T t) {
        return GSON.toJson(t);
    }

    @Override
    public <T> T fromJson(String json, Class<T> clz) {
        try {
            return GSON.fromJson(json, clz);
        } catch (JsonSyntaxException e) {
            throw new ValidationException("Invalid json", e);
        }

    }
}
