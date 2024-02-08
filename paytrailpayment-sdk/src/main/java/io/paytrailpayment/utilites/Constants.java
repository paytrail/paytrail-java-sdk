package io.paytrailpayment.utilites;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String API_ENDPOINT = "https://services.paytrail.com";
    public static final String CHECKOUT_ACCOUNT = "checkout-account";
    public static final String CHECKOUT_ALGORITHM = "checkout-algorithm";
    public static final String CHECKOUT_METHOD = "checkout-method";
    public static final String CHECKOUT_NONCE = "checkout-nonce";
    public static final String CHECKOUT_TIMESTAMP = "checkout-timestamp";
    public static final String PLATFORM_NAME = "platform-name";
    public static final String CONTENT_TYPE = "application/json; charset=utf-8";
    public static final String CHECKOUT_TRANSACTION_ID = "checkout-transaction-id";
    public static final String CHECKOUT_TOKENIZATION_ID = "checkout-tokenization-id";
    public static final String SIGNATURE = "signature";
    public static final String CHECKOUT_REDIRECT_SUCCESS_URL = "checkout-redirect-success-url";
    public static final String CHECKOUT_REDIRECT_CANCEL_URL = "checkout-redirect-cancel-url";
    public static final String CHECKOUT_CALLBACK_SUCCESS_URL = "checkout-callback-success-url";
    public static final String CHECKOUT_CALLBACK_CANCEL_URL = "checkout-callback-cancel-url";
    public static final String LANGUAGE = "language";
    public static final String MESSAGE_CHECKOUT_ACCOUNT_EMPTY = "checkout-account is empty. ";
    public static final String MESSAGE_CHECKOUT_ALGORITHM_EMPTY = "checkout-algorithm is empty. ";
    public static final String MESSAGE_UNSUPPORTED_METHOD = "unsupported method chosen. ";
    public static final String MESSAGE_CHECKOUT_TIMESTAMP_EMPTY = "checkout-timestamp is empty. ";
    public static final String MESSAGE_CHECKOUT_REDIRECT_SUCCESS_URL_EMPTY = "checkout-redirect success url is empty. ";
    public static final String MESSAGE_CHECKOUT_REDIRECT_CANCEL_URL_EMPTY = "checkout-redirect cancel url is empty. ";
    public static final String MESSAGE_UNSUPPORTED_LANGUAGE = "unsupported language chosen. ";

    public static final String POST_METHOD = "POST";
    public static final String GET_METHOD = "GET";
}
