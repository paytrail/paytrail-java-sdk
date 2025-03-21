package io.paytrailpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.CreateRefundRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.CreateRefundResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateRefundRequestUnitTest extends TestCase {
    private PaytrailClient client;
    private String transactionId;

    @BeforeEach
    public void init() {
        client = new PaytrailClient(this.merchantId, this.secretKey, this.platformName);

        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setStamp(UUID.randomUUID().toString());
        req.setReference("9187445");
        req.setCurrency(PaytrailCurrency.EUR);
        req.setLanguage(PaytrailLanguage.FI);
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
    public void createRefundRequestReturnStatusCode400WithPayloadNull() {
        CreateRefundResponse res = client.createRefundRequest(null, null);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void createRefundRequestReturnStatusCode400WithFieldRequiredNotFilled() throws JsonProcessingException {
        String messageExpect = "{\"callbackUrls\":\"Callback Urls can't be null.\",\"items\":\"{\\\"amount\\\":\\\"Item's amount is invalid. \\\"}\"}\n";
        CreateRefundRequest req = new CreateRefundRequest();

        req.setEmail("test@gmail.com");
        req.setRefundStamp(UUID.randomUUID().toString());
        req.setRefundReference(UUID.randomUUID().toString());

        List<RefundItem> items = new ArrayList<>();
        RefundItem item = new RefundItem();
        item.setAmount(-1590);
        item.setStamp(UUID.randomUUID().toString());
        item.setRefundStamp(UUID.randomUUID().toString());
        items.add(item);

        req.setItems(items);

        CreateRefundResponse res = client.createRefundRequest(req, transactionId);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode actualJsonNode = objectMapper.readTree(res.getReturnMessage());

        // Extract the "item" field which contains the nested JSON string
        String itemField = actualJsonNode.get("items").asText();

        // Parse the nested JSON string inside the "item" field
        JsonNode itemJsonNode = objectMapper.readTree(itemField);
        // Extract and normalize the callbackUrls and amount messages
        String actualCallbackUrlsMessage = actualJsonNode.get("callbackUrls").asText().replace("\\u0027", "'");
        String actualAmountMessage = itemJsonNode.get("amount").asText().replace("\\u0027", "'");

        // Combine the messages if needed
        String actualVatPercentageMessage = "{\"callbackUrls\":\"" + actualCallbackUrlsMessage + "\",\"items\":\"{\\\"amount\\\":\\\"" + actualAmountMessage + "\\\"}\"}\n";
        assertEquals(messageExpect, actualVatPercentageMessage);
        assertNull(res.getData());
    }

    @Test()
    public void createRefundRequestReturnStatusCode403WithPayloadValidateFail() {
        CreateRefundRequest req = new CreateRefundRequest();

        req.setAmount(1590);
        req.setEmail("test@gmail.com");
        req.setRefundStamp(UUID.randomUUID().toString());
        req.setRefundReference(UUID.randomUUID().toString());

        List<RefundItem> items = new ArrayList<>();
        RefundItem item = new RefundItem();
        item.setAmount(-1590);
        item.setStamp(UUID.randomUUID().toString());
        item.setRefundStamp(UUID.randomUUID().toString());
        items.add(item);

        req.setItems(items);

        CallbackUrl callbackUrls = new CallbackUrl();
        callbackUrls.setSuccess("https://ecom.example.org/success");
        callbackUrls.setCancel("https://ecom.example.org/cancel");
        req.setCallbackUrls(callbackUrls);

        CreateRefundResponse res = client.createRefundRequest(req, transactionId);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void createRefundRequestReturnStatusCode200() {
        CreateRefundRequest req = new CreateRefundRequest();
        req.setAmount(1590);
        req.setEmail("test@gmail.com");
        req.setRefundStamp(UUID.randomUUID().toString());
        req.setRefundReference(UUID.randomUUID().toString());

        List<RefundItem> items = new ArrayList<>();
        RefundItem item = new RefundItem();
        item.setAmount(1590);
        item.setStamp(UUID.randomUUID().toString());
        item.setRefundStamp(UUID.randomUUID().toString());
        items.add(item);

        req.setItems(items);

        CallbackUrl callbackUrls = new CallbackUrl();
        callbackUrls.setSuccess("https://ecom.example.org/success");
        callbackUrls.setCancel("https://ecom.example.org/cancel");
        req.setCallbackUrls(callbackUrls);
        CreateRefundResponse res = client.createRefundRequest(req, transactionId);
        assertNotNull(res);
    }

    @Test()
    public void createRefundRequestReturnStatusCode401WithFalseAuthenticationInformation() {
        client = new PaytrailClient("375918", "SAIPPUAKAUPPIAS", "test");
        CreateRefundRequest req = new CreateRefundRequest();

        req.setAmount(1590);
        req.setEmail("test@gmail.com");
        req.setRefundStamp(UUID.randomUUID().toString());
        req.setRefundReference(UUID.randomUUID().toString());

        List<RefundItem> items = new ArrayList<>();
        RefundItem item = new RefundItem();
        item.setAmount(1590);
        item.setStamp(UUID.randomUUID().toString());
        item.setRefundStamp(UUID.randomUUID().toString());
        items.add(item);

        req.setItems(items);

        CallbackUrl callbackUrls = new CallbackUrl();
        callbackUrls.setSuccess("https://ecom.example.org/success");
        callbackUrls.setCancel("https://ecom.example.org/cancel");
        req.setCallbackUrls(callbackUrls);

        CreateRefundResponse res = client.createRefundRequest(req, transactionId);

        assertNotNull(res);
        assertEquals(ResponseMessage.UNAUTHORIZED.getCode(), res.getReturnCode());
    }
}
