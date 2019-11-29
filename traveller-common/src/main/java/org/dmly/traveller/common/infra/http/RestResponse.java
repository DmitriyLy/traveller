package org.dmly.traveller.common.infra.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RestResponse<T> {

    private final int statusCode;

    private final T body;

}
