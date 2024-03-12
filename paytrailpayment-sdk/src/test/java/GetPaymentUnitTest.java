import io.paytrailpayment.PaytrailClient;
import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.model.CallbackUrl;
import io.paytrailpayment.dto.request.model.Customer;
import io.paytrailpayment.dto.request.model.Item;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.dto.response.GetPaymentResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GetPaymentUnitTest extends TestCase {
    private PaytrailClient client;
    private String transactionId;

    @Before
    public void setUp() {
        client = new PaytrailClient(this.merchantId, this.secretKey, this.platformName);

        CreatePaymentRequest req = new CreatePaymentRequest();

        req.setStamp(UUID.randomUUID().toString());
        req.setReference("9187445");
        req.setCurrency("EUR");
        req.setLanguage("FI");
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
    public void ShouldReturnStatusCode200() {
        GetPaymentResponse res = client.getPaymentInfo(transactionId);

        assertNotNull(res);
        assertNotNull(res.getData());
        assertEquals(ResponseMessage.OK.getCode(), res.getReturnCode());
        assertEquals(ResponseMessage.OK.getDescription(), res.getReturnMessage());
    }

    @Test()
    public void ShouldReturnStatusCode400WithPayloadNull() {
        GetPaymentResponse res = client.getPaymentInfo(null);

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void ShouldReturnStatusCode400WithPayloadValidateFail() {
        GetPaymentResponse res = client.getPaymentInfo("");

        assertNotNull(res);
        assertEquals(ResponseMessage.BAD_REQUEST.getCode(), res.getReturnCode());
        assertNull(res.getData());
    }

    @Test()
    public void ShouldReturnStatusCode401() {
        client = new PaytrailClient("375918", "SAIPPUAKAUPPIAS", "test");

        GetPaymentResponse res = client.getPaymentInfo(transactionId);

        assertNotNull(res);
        assertEquals(ResponseMessage.UNAUTHORIZED.getCode(), res.getReturnCode());
    }
}
