package io.paytrailpayment;

import io.paytrailpayment.dto.response.data.DataResponse;
import io.paytrailpayment.utilites.Constants;
import io.paytrailpayment.utilites.ResponseMessage;
import io.paytrailpayment.utilites.Signature;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

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
            headers.put("checkout-account", this.merchantId);
            headers.put("checkout-algorithm", "sha256");
            headers.put("checkout-method", method.toUpperCase());
            headers.put("checkout-nonce", UUID.randomUUID().toString());
            headers.put("checkout-timestamp", String.format("%tFT%<tT.%<tLZ", datetime));
            headers.put("platform-name", this.platformName);
            headers.put("content-type", "application/json; charset=utf-8");

            if (transactionId != null && !transactionId.isEmpty()) {
                headers.put("checkout-transaction-id", transactionId);
            }

            if (checkoutTokenizationId != null && !checkoutTokenizationId.isEmpty()) {
                headers.put("checkout-tokenization-id", checkoutTokenizationId);
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

        return Signature.calculateHmac(this.secretKey, hdparams, body, "sha256");
    }

    protected DataResponse handleRequest(String method, String url, String body, String transactionId, String checkoutTokenizationId) {
        DataResponse res = new DataResponse();
        try {
            Map<String, String> hdparams = getHeaders(method, transactionId, checkoutTokenizationId);

            String signature = calculateHmac(hdparams, body);

            if (signature.isEmpty()) {
                res.setStatusCode(ResponseMessage.BAD_REQUEST.getCode());
                res.setData(ResponseMessage.BAD_REQUEST.getDescription());
                return res;
            }

            hdparams = getHeaders(hdparams, "signature", signature);

            // Create new request
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost httpPost = new HttpPost(url);
            HttpGet httpGet = new HttpGet(url);

            if (Objects.equals(method, Constants.POST_METHOD)) {
                EntityBuilder builder = EntityBuilder
                        .create()
                        .setText(body);
                httpPost.setEntity(builder.build());

                for (Map.Entry<String, String> entry : hdparams.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            } else if (Objects.equals(method, Constants.GET_METHOD)) {
                for (Map.Entry<String, String> entry : hdparams.entrySet()) {
                    httpGet.setHeader(entry.getKey(), entry.getValue());
                }
            }

            // Send request
            HttpClientResponseHandler<String> handler = response -> {
                int status = response.getCode();
                HttpEntity entity = response.getEntity();
                String getResponseString = entity != null ? EntityUtils.toString(entity) : null;
                if (entity != null) {
                    EntityUtils.consume(entity);
                }

                res.setStatusCode(status);
                res.setData(getResponseString);

                return getResponseString;
            };

            // Execute
            if (Objects.equals(method, Constants.POST_METHOD)) {
                httpClient.execute(httpPost, handler);
            } else if (Objects.equals(method, Constants.GET_METHOD)) {
                httpClient.execute(httpGet, handler);
            }

            return res;
        } catch (Exception ex) {
            res.setStatusCode(ResponseMessage.RESPONSE_ERROR.getCode());
            res.setData(ex.toString());
            return res;
        }
    }
}
