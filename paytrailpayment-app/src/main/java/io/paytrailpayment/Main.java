package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreatePaymentRequest;
import io.paytrailpayment.dto.request.model.CallbackUrl;
import io.paytrailpayment.dto.request.model.Customer;
import io.paytrailpayment.dto.request.model.Item;
import io.paytrailpayment.dto.response.CreatePaymentResponse;
import io.paytrailpayment.utilites.ResponseMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        PaytrailClient client = new PaytrailClient("375917", "SAIPPUAKAUPPIAS", "test");

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

        System.out.println(res.getReturnCode());
        System.out.println(res.getReturnMessage());
        System.out.println(res.getData());
    }
}