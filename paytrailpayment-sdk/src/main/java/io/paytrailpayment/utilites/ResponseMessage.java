package io.paytrailpayment.utilites;

import lombok.Getter;

@Getter
public enum ResponseMessage {
    OK(200, "Success"),
    CREATED(201, "Success"),
    EXCEPTION(503, "Exception"),
    RESPONSE_NULL(404, "Paytrail Server Return Null"),
    RESPONSE_ERROR(500, "Paytrail Server Error"),
    BAD_REQUEST(400, "Bad Request"),
    VALIDATE_FAIL(403, "Validation Failed"),
    UNAUTHORIZED(401, "Unauthorized");
    private final int code;
    private final String description;
    ResponseMessage(int code, String description) {
        this.code = code;
        this.description = description;
    }
}