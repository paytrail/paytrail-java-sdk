package io.paytrailpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.paytrailpayment.PaytrailClient;
import io.paytrailpayment.TestCase;
import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.request.model.PaytrailCurrency;
import io.paytrailpayment.dto.response.CreatePaymentResponse;

import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
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
    public void createPaymentReturnStatusCode400WithFieldRequiredNotFilled() throws JsonProcessingException {
        String messageExpect = "{\"reference\":\"Reference can't be null or empty.\",\"amount\":\"Amount doesn't match total of items.\",\"stamp\":\"Stamp can't be null or empty.\",\"currency\":\"Currency can't be null.\",\"language\":\"Language can't be null.\"}";
        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setOrderId("12335");

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

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode expectedJson = objectMapper.readTree(messageExpect);
        JsonNode actualJson = objectMapper.readTree(res.getReturnMessage());

        assertEquals(expectedJson, actualJson);
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
    public void createPaymentReturnStatusCode200() {
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

        assertNotNull(res);
        assertEquals(ResponseMessage.OK.getCode(), res.getReturnCode());
        assertEquals(ResponseMessage.OK.getDescription(), res.getReturnMessage());
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

        assertNotNull(res);
        assertEquals(ResponseMessage.UNAUTHORIZED.getCode(), res.getReturnCode());
    }

    @Test()
    public void createPaymentReturnStatusCode400WithVatPercentageMoreThanOneDecimalPlace() throws Exception {
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
        item.setVatPercentage(new BigDecimal("24.55")); // VatPercentage has more than one decimal place
        item.setProductCode("#927502759");
        items.add(item);

        req.setItems(items);

        CreatePaymentResponse res = client.createPayment(req);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());

        // Expected vatPercentage message
        String expectedVatPercentageMessage = "Item's vat Percentage can't have more than one decimal place.";

        // Parse the actual response message to a JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualJsonNode = objectMapper.readTree(res.getReturnMessage());

        // Extract the "item" field which contains the nested JSON string
        String itemField = actualJsonNode.get("item").asText();

        // Parse the nested JSON string inside the "item" field
        JsonNode itemJsonNode = objectMapper.readTree(itemField);

        // Extract and normalize the vatPercentage message
        String actualVatPercentageMessage = itemJsonNode.get("vatPercentage").asText().replace("\\u0027", "'");

        // Compare the actual vatPercentage message with the expected one
        assertEquals(expectedVatPercentageMessage, actualVatPercentageMessage);

        assertNull(res.getData());
    }

    @Test()
    public void createPaymentReturnStatusCode400WithVatPercentageNegative() throws Exception {
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
        item.setVatPercentage(new BigDecimal("-5.0")); // VatPercentage is negative
        item.setProductCode("#927502759");
        items.add(item);

        req.setItems(items);

        CreatePaymentResponse res = client.createPayment(req);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());

        // Expected vatPercentage message
        String expectedVatPercentageMessage = "Item's vat Percentage can't be null or a negative number.";

        // Parse the actual response message to a JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualJsonNode = objectMapper.readTree(res.getReturnMessage());

        // Extract the "item" field which contains the nested JSON string
        String itemField = actualJsonNode.get("item").asText();

        // Parse the nested JSON string inside the "item" field
        JsonNode itemJsonNode = objectMapper.readTree(itemField);

        // Extract and normalize the vatPercentage message
        String actualVatPercentageMessage = itemJsonNode.get("vatPercentage").asText().replace("\\u0027", "'");

        // Compare the actual vatPercentage message with the expected one
        assertEquals(expectedVatPercentageMessage, actualVatPercentageMessage);

        assertNull(res.getData());
    }



    @Test()
    public void createPaymentReturnStatusCode400WithVatPercentageNull() throws Exception {
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
        item.setVatPercentage(null); // VatPercentage is null
        item.setProductCode("#927502759");
        items.add(item);

        req.setItems(items);

        CreatePaymentResponse res = client.createPayment(req);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());

        // Expected vatPercentage message
        String expectedVatPercentageMessage = "Item's vat Percentage can't be null or a negative number.";

        // Parse the actual response message to a JsonNode
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualJsonNode = objectMapper.readTree(res.getReturnMessage());

        // Extract the "item" field which contains the nested JSON string
        String itemField = actualJsonNode.get("item").asText();

        // Parse the nested JSON string inside the "item" field
        JsonNode itemJsonNode = objectMapper.readTree(itemField);

        // Extract and normalize the vatPercentage message
        String actualVatPercentageMessage = itemJsonNode.get("vatPercentage").asText().replace("\\u0027", "'");

        // Compare the actual vatPercentage message with the expected one
        assertEquals(expectedVatPercentageMessage, actualVatPercentageMessage);

        assertNull(res.getData());
    }
}
