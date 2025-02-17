package io.paytrailpayment;

import io.paytrailpayment.dto.response.data.DataResponse;
import io.paytrailpayment.exception.PaytrailClientException;
import io.paytrailpayment.exception.PaytrailCommunicationException;
import io.paytrailpayment.utilites.Constants;
import io.paytrailpayment.utilites.ResponseMessage;
import io.paytrailpayment.utilites.Signature;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public abstract class Paytrail {
    protected String merchantId;
    protected String secretKey;
    protected String platformName;

    protected Map<String, String> getHeaders(String method, String transactionId, String checkoutTokenizationId) {
        Date datetime = new Date();
        Map<String, String> headers = new HashMap<>();
        try {
            headers.put(Constants.CHECKOUT_ACCOUNT_HEADER, this.merchantId);
            headers.put(Constants.CHECKOUT_ALGORITHM_HEADER, Constants.SHA256_ALGORITHM);
            headers.put(Constants.CHECKOUT_METHOD_HEADER, method.toUpperCase());
            headers.put(Constants.CHECKOUT_NONCE_HEADER, UUID.randomUUID().toString());
            headers.put(Constants.CHECKOUT_TIMESTAMP_HEADER, String.format("%tFT%<tT.%<tLZ", datetime));
//      headers.put(Constants.PLATFORM_NAME_HEADER, this.platformName);
//      headers.put(Constants.CONTENT_TYPE_HEADER, Constants.CONTENT_TYPE);

            if (transactionId != null && !transactionId.isEmpty()) {
                headers.put(Constants.CHECKOUT_TRANSACTION_ID_HEADER, transactionId);
            }

            if (checkoutTokenizationId != null && !checkoutTokenizationId.isEmpty()) {
                headers.put(Constants.CHECKOUT_TOKENIZATION_ID_HEADER, checkoutTokenizationId);
            }

            return headers;
        } catch (Exception e) {
            return null;
        }
    }

    protected Map<String, String> getHeaders(Map<String, String> headers, String headerName, String headerValue) {
        headers.put(headerName, headerValue);
        return headers;
    }

    protected String calculateHmac(Map<String, String> hdparams, String body) throws NoSuchAlgorithmException, InvalidKeyException {
        if (body == null || body.isEmpty()) {
            body = "";
        }

        return Signature.calculateHmac(this.secretKey, hdparams, body, Constants.SHA256_ALGORITHM);
    }

    protected DataResponse handleRequest(String method, String url, String body, String transactionId, String checkoutTokenizationId) throws PaytrailClientException, PaytrailCommunicationException {
        return handleRequest(method, url, body, transactionId, checkoutTokenizationId, false);
    }

    protected DataResponse handleRequest(String method, String url, String body,
                                         String transactionId, String checkoutTokenizationId, boolean skipAuthHeaders)
            throws PaytrailClientException, PaytrailCommunicationException {
        DataResponse res = new DataResponse();
        try {
            Map<String, String> hdparams = null;

            if (!skipAuthHeaders) {
                hdparams = getHeaders(method, transactionId, checkoutTokenizationId);
                String signature = calculateHmac(hdparams, body);

                if (signature.isEmpty()) {
                    res.setStatusCode(ResponseMessage.BAD_REQUEST.getCode());
                    res.setData(ResponseMessage.BAD_REQUEST.getDescription());
                    return res;
                }

                hdparams = getHeaders(hdparams, Constants.SIGNATURE_HEADER, signature);
            } else {
                Map<String, String> headers = new HashMap<>();
                hdparams = getHeaders(headers, Constants.CONTENT_TYPE_HEADER, Constants.CONTENT_TYPE);
                hdparams = getHeaders(hdparams, Constants.PLATFORM_NAME_HEADER, this.platformName);
            }
            // Build the OkHttpClient instance (add interceptors if needed for logging)
            OkHttpClient client = new OkHttpClient.Builder().build();

            // Build the request, adding headers if available
            okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder().url(url);
            if (hdparams != null) {
                for (Map.Entry<String, String> entry : hdparams.entrySet()) {
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }
            if (Constants.POST_METHOD.equals(method)) {
                RequestBody requestBody = RequestBody.create(body.getBytes(StandardCharsets.UTF_8));
                requestBuilder.post(requestBody);
            } else if (Constants.GET_METHOD.equals(method)) {
                requestBuilder.get();
            }

            okhttp3.Request request = requestBuilder.build();
            // Execute the request
            try (okhttp3.Response response = client.newCall(request).execute()) {
                int status = response.code();
                String responseString = response.body() != null ? response.body().string() : null;
                res.setStatusCode(status);
                res.setData(responseString);
            }

            return res;
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // Exception related to the client (e.g., HMAC calculation)
            throw new PaytrailClientException(ResponseMessage.RESPONSE_ERROR.getCode(), e.toString(), e);
        } catch (IOException e) {
            // Exception related to communication issues
            throw new PaytrailCommunicationException(ResponseMessage.EXCEPTION.getCode(), e.getMessage(), e);
        }
    }
}
