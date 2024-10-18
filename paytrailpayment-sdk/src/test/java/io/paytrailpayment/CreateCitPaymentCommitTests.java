package io.paytrailpayment;

import io.paytrailpayment.dto.request.CreateMitOrCitPaymentRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.response.CreateMitOrCitPaymentResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

public class CreateCitPaymentCommitTests {
    private static final String MERCHANTIDN = "375917";
    private static final String MERCHANTIDSIS = "695861";
    private static final String SECRETKEYSIS = "SAIPPUAKAUPPIAS";

    @Test
    public void requestNullReturnCode400() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest request = null;
        CreateMitOrCitPaymentResponse res = payTrail.createCitPaymentCommit(request, "0e056dd8-408f-11ee-9cb4-e3059a523029");
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validateFalseReturnCode403() {
        // Arrange
        int expected = ResponseMessage.VALIDATION_FAILED.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest request = new CreateMitOrCitPaymentRequest();
        CreateMitOrCitPaymentResponse res = payTrail.createCitPaymentCommit(request, "0e056dd8-408f-11ee-9cb4-e3059a523029");
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void successReturnCode200() {
        // Arrange
        int expected = ResponseMessage.OK.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest payload = new CreateMitOrCitPaymentRequest();
        payload.setToken("c7441208-c2a1-4a10-8eb6-458bd8eaa64f");
        payload.setStamp(UUID.randomUUID().toString());
        payload.setReference("9187445");
        payload.setAmount(1590);
        payload.setCurrency(PaytrailCurrency.EUR);
        payload.setLanguage(PaytrailLanguage.FI);
        payload.setOrderId("");
        payload.setItems(Arrays.asList(new ShopInShopItem(
                1590, 1, 24, "#927502759", "Pet supplies", "Cat ladder",
                UUID.randomUUID().toString(), "9187445")));
        payload.setCustomer(new Customer(
                "erja.esimerkki@example.org", "Erja", "FI12345671", "nothing",
                "+358501234567", "123"));
        payload.setRedirectUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        payload.setCallbackUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        payload.setDeliveryAddress(new Address("Tampere", "FI", "Pirkanmaa", "33100", "Hämeenkatu 6 B"));
        payload.setInvoicingAddress(new Address("Helsinki", "FI", "Uusimaa", "00510", "Testikatu 1"));
        payload.setGroups(Arrays.asList(PaytrailPaymentMethodGroup.MOBILE.toString()));

        CreateMitOrCitPaymentResponse res = payTrail.createCitPaymentCommit(payload, "0e056dd8-408f-11ee-9cb4-e3059a523029");
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void callPaytrailReturnFailReturnCode500() {
        // Arrange
        int expected = ResponseMessage.RESPONSE_ERROR.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest payload = new CreateMitOrCitPaymentRequest();
        payload.setToken("c7441208-c2a1-4a10-8eb6-458bd8eaa64f");
        payload.setStamp(UUID.randomUUID().toString());
        payload.setReference("9187445");
        payload.setAmount(1590);
        payload.setCurrency(PaytrailCurrency.EUR);
        payload.setLanguage(PaytrailLanguage.FI);
        payload.setOrderId("");
        payload.setItems(Arrays.asList(new ShopInShopItem(
                1590, 1, 24, "#927502759", "Pet supplies", "Cat ladder",
                UUID.randomUUID().toString(), "9187445")));
        payload.setCustomer(new Customer(
                "erja.esimerkki@example.org", "Erja", "FI12345671", "nothing",
                "+358501234567", "123"));
        payload.setRedirectUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        payload.setCallbackUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        payload.setDeliveryAddress(new Address("Tampere", "FI", "Pirkanmaa", "33100", "Hämeenkatu 6 B"));
        payload.setInvoicingAddress(new Address("Helsinki", "FI", "Uusimaa", "00510", "Testikatu 1"));
        payload.setGroups(Arrays.asList(PaytrailPaymentMethodGroup.MOBILE.toString()));

        CreateMitOrCitPaymentResponse res = payTrail.createCitPaymentCommit(payload, "0e056dd8-408f-11ee-9cb4-e3059a523029");
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
