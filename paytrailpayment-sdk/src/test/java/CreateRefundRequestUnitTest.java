import io.paytrailpayment.PaytrailClient;
import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.CreateRefundRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.CreateRefundResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateRefundRequestUnitTest extends TestCase {
    private PaytrailClient client;
    private String transactionId;

    @Before
    public void setUp() {
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
    public void createRefundRequestReturnStatusCode400WithFieldRequiredNotFilled() {
        String messageExpect = "{\"callbackUrls\":\"Callback Urls can't be null.\",\"items\":\"{\\\"amount\\\":\\\"Item's amount is invalid.\\\"}\"}\n";
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
        assertEquals(messageExpect, res.getReturnMessage());
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
    public void createRefundRequestReturnStatusCode201() {
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
