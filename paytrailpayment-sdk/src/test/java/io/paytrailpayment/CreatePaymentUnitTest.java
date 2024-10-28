package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.request.model.PaytrailCurrency;
import io.paytrailpayment.dto.response.CreatePaymentResponse;

import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePaymentUnitTest extends TestCase {
    private PaytrailClient client;

    @Before
    public void setUp() {
        // Initialize your PaytrailClient here with default values for all tests or any other setup.
        client = new PaytrailClient(this.merchantId, this.secretKey, this.platformName);
    }

    @Test()
    public void createPaymentReturnStatusCode400WithPayloadNull() {
        CreatePaymentResponse res = client.createPayment(null);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void createPaymentReturnStatusCode400WithFieldRequiredNotFilled() {
        String messageExpect = "{\"reference\":\"Reference can't be null or empty.\",\"amount\":\"Amount doesn't match total of items.\",\"stamp\":\"Stamp can't be null or empty.\",\"currency\":\"Currency can't be null.\",\"language\":\"Language can't be null.\"}";
        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setOrderId("12335");

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setUnitPrice(1590);
        item.setUnits(1);
        item.setVatPercentage(24);
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

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertEquals(messageExpect, res.getReturnMessage());
        assertNull(res.getData());
    }

    @Test()
    public void createPaymentReturnStatusCode400WithPayloadValidateFail() {
        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setStamp(UUID.randomUUID().toString());
        req.setReference("9187445");
        req.setCurrency(PaytrailCurrency.EUR);
        req.setLanguage(PaytrailLanguage.EN);
        req.setAmount(-1590);

        CreatePaymentResponse res = client.createPayment(req);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void createPaymentReturnStatusCode201() {
        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setStamp(UUID.randomUUID().toString());
        req.setReference("9187445");
        req.setCurrency(PaytrailCurrency.EUR);
        req.setLanguage(PaytrailLanguage.EN);
        req.setOrderId("12335");
        req.setAmount(1590);

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setUnitPrice(1590);
        item.setUnits(1);
        item.setVatPercentage(24);
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

        assertNotNull(res);
        assertEquals(ResponseMessage.CREATED.getCode(), res.getReturnCode());
        assertEquals(ResponseMessage.CREATED.getDescription(), res.getReturnMessage());
        assertNotNull(res.getData());
    }

    @Test()
    public void createPaymentReturnStatusCode401WithFalseAuthenticationInformation() {
        client = new PaytrailClient("375918", "SAIPPUAKAUPPIAS", "test");

        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setStamp(UUID.randomUUID().toString());
        req.setReference("9187445");
        req.setCurrency(PaytrailCurrency.EUR);
        req.setLanguage(PaytrailLanguage.EN);
        req.setOrderId("12335");
        req.setAmount(1590);

        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setUnitPrice(1590);
        item.setUnits(1);
        item.setVatPercentage(24);
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

        assertNotNull(res);
        assertEquals(ResponseMessage.UNAUTHORIZED.getCode(), res.getReturnCode());
    }
}
