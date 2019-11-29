package org.dmly.traveller.common.infra.http;

public interface RestClient {

    <T> RestResponse<T> get(String url, Class<T> clz);

}
