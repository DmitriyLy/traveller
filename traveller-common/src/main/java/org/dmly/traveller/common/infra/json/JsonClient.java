package org.dmly.traveller.common.infra.json;

public interface JsonClient {

    <T> String toJson(T t);

    <T> T fromJson(String json, Class<T> clz);
}
