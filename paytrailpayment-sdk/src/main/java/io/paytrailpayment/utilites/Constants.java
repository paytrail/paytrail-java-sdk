package io.paytrailpayment.utilites;

public class Constants {
    public static final String API_ENDPOINT = "https://services.paytrail.com";
    public static final String CHECKOUT_ACCOUNT_HEADER = "checkout-account";
    public static final String CHECKOUT_ALGORITHM_HEADER = "checkout-algorithm";
    public static final String CHECKOUT_METHOD_HEADER = "checkout-method";
    public static final String CHECKOUT_NONCE_HEADER = "checkout-nonce";
    public static final String CHECKOUT_TIMESTAMP_HEADER = "checkout-timestamp";
    public static final String CHECKOUT_TRANSACTION_ID_HEADER = "checkout-transaction-id";
    public static final String CHECKOUT_TOKENIZATION_ID_HEADER = "checkout-tokenization-id";
    public static final String SIGNATURE_HEADER = "signature";
    public static final String PLATFORM_NAME_HEADER = "platform-name";
    public static final String CONTENT_TYPE_HEADER = "content-type";
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";

    public static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";


    public static final String POST_METHOD = "POST";
    public static final String GET_METHOD = "GET";

    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static final String SHA256_ALGORITHM = "sha256";
}
