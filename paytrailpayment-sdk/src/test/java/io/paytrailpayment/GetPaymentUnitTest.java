package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.GetPaymentResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetPaymentUnitTest extends TestCase {
    private PaytrailClient client;
    private String transactionId;

    @BeforeEach
    public void init() {
        client = new PaytrailClient(this.merchantId, this.secretKey, this.platformName);

        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setStamp(UUID.randomUUID().toString());
        req.setReference("9187445");
        req.setCurrency(PaytrailCurrency.EUR);
        req.setLanguage(PaytrailLanguage.SV);
        req.setOrderId("12335");
        req.setAmount(1590);

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setUnitPrice(1590);
        item.setUnits(1);
        item.setVatPercentage(new BigDecimal(24));
        item.setProductCode("#927502759");
        items.add(item);

        req.setItems(items);

        Customer customer = new Customer();
        customer.setEmail("erja.esimerkki@example.org");
        customer.setFirstName("TEST");
        customer.setLastName("test");
        customer.setPhone("0369874566");
        customer.setVatId("156988");
        customer.setCompanyName("ttest");
        req.setCustomer(customer);

        CallbackUrl redirectUrls = new CallbackUrl();
        redirectUrls.setSuccess("https://ecom.example.org/success");
        redirectUrls.setCancel("https://ecom.example.org/cancel");
        req.setRedirectUrls(redirectUrls);

        CallbackUrl callbackUrls = new CallbackUrl();
        callbackUrls.setSuccess("https://ecom.example.org/success");
        callbackUrls.setCancel("https://ecom.example.org/cancel");
        req.setCallbackUrls(callbackUrls);

        CreatePaymentResponse res = client.createPayment(req);

        transactionId = res.getData().getTransactionId();
    }

    @Test()
    public void getPaymentReturnStatusCode200() {
        GetPaymentResponse res = client.getPaymentInfo(transactionId);

        assertNotNull(res);
        assertNotNull(res.getData());
        assertEquals(ResponseMessage.OK.getCode(), res.getReturnCode());
        assertEquals(ResponseMessage.OK.getDescription(), res.getReturnMessage());
    }

    @Test()
    public void getPaymentReturnStatusCode400WithPayloadNull() {
        GetPaymentResponse res = client.getPaymentInfo(null);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void getPaymentReturnStatusCode400WithPayloadValidateFail() {
        GetPaymentResponse res = client.getPaymentInfo("");

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void getPaymentReturnStatusCode401WithFalseAuthenticationInformation() {
        client = new PaytrailClient("375918", "SAIPPUAKAUPPIAS", "test");

        GetPaymentResponse res = client.getPaymentInfo(transactionId);

        assertNotNull(res);
        assertEquals(ResponseMessage.UNAUTHORIZED.getCode(), res.getReturnCode());
    }
}
