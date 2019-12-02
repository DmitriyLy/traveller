package org.dmly.traveller.common.infra.json;

import org.dmly.traveller.common.infra.exception.flow.ValidationException;

public interface JsonClient {

    <T> String toJson(T t);

    <T> T fromJson(String json, Class<T> clz) throws ValidationException;
}
