package io.paytrailpayment;

import io.paytrailpayment.dto.request.*;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.response.CreateMitOrCitPaymentResponse;
import io.paytrailpayment.dto.response.GetTokenResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateMitPaymentChargeTests {
    private static final String MERCHANTIDN = "375917";
    private static final String MERCHANTIDSIS = "695861";
    private static final String SECRETKEYSIS = "MONISAIPPUAKAUPPIAS";
    private static final String SECRETKEYSIS_BAD = "MONISAIPPUAKAUPPIASS";

    @Test
    public void createMitPaymentCharge_RequestNull_ReturnCode400() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest request = null;
        CreateMitOrCitPaymentResponse res = payTrail.createMitPaymentCharge(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createMitPaymentCharge_ValidateFalse_ReturnCode403() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest request = new CreateMitOrCitPaymentRequest();
        CreateMitOrCitPaymentResponse res = payTrail.createMitPaymentCharge(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createMitPaymentCharge_Success_ReturnCode200() {
        // Arrange
        int expected = ResponseMessage.OK.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
        CreateMitOrCitPaymentRequest payload = createValidPayload();
        CreateMitOrCitPaymentResponse res = payTrail.createMitPaymentCharge(payload);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createMitPaymentCharge_CallPaytrailReturnFail_ReturnCode401() {
        // Arrange
        int expected = ResponseMessage.UNAUTHORIZED.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS_BAD, "test");
        CreateMitOrCitPaymentRequest payload = createValidPayload();
        CreateMitOrCitPaymentResponse res = payTrail.createMitPaymentCharge(payload);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    private CreateMitOrCitPaymentRequest createValidPayload() {
        CreateMitOrCitPaymentRequest payload = new CreateMitOrCitPaymentRequest();
        payload.setToken("c7441208-c2a1-4a10-8eb6-458bd8eaa64f");
        payload.setStamp(UUID.randomUUID().toString());
        payload.setReference("9187445");
        payload.setAmount(1590);
        payload.setCurrency(PaytrailCurrency.EUR);
        payload.setLanguage(PaytrailLanguage.FI);
        payload.setOrderId("");

        ShopInShopItem item = new ShopInShopItem();
        item.setUnitPrice(1590);
        item.setUnits(1);
        item.setVatPercentage(BigDecimal.valueOf(24));
        item.setProductCode("#927502759");
        item.setCategory("Pet supplies");
        item.setDescription("Cat ladder");
        item.setStamp(UUID.randomUUID().toString());
        item.setReference("9187445");
        item.setMerchant("695874");
        payload.setItems(Arrays.asList(item));

        Customer customer = new Customer();
        customer.setEmail("erja.esimerkki@example.org");
        customer.setFirstName("Erja");
        customer.setVatId("FI12345671");
        customer.setCompanyName("nothing");
        customer.setLastName("+358501234567");
        customer.setPhone("123");
        payload.setCustomer(customer);

        CallbackUrl redirectUrls = new CallbackUrl();
        redirectUrls.setSuccess("https://ecom.example.org/success");
        redirectUrls.setCancel("https://ecom.example.org/cancel");
        payload.setRedirectUrls(redirectUrls);

        CallbackUrl callbackUrls = new CallbackUrl();
        callbackUrls.setSuccess("https://ecom.example.org/success");
        callbackUrls.setCancel("https://ecom.example.org/cancel");
        payload.setCallbackUrls(callbackUrls);

        Address deliveryAddress = new Address();
        deliveryAddress.setCity("Tampere");
        deliveryAddress.setCountry("FI");
        deliveryAddress.setCounty("Pirkanmaa");
        deliveryAddress.setPostalCode("33100");
        deliveryAddress.setStreetAddress("Hämeenkatu 6 B");
        payload.setDeliveryAddress(deliveryAddress);

        Address invoicingAddress = new Address();
        invoicingAddress.setCity("Helsinki");
        invoicingAddress.setCountry("FI");
        invoicingAddress.setCounty("Uusimaa");
        invoicingAddress.setPostalCode("00510");
        invoicingAddress.setStreetAddress("Testikatu 1");
        payload.setInvoicingAddress(invoicingAddress);

        payload.setGroups(Arrays.asList(PaytrailPaymentMethodGroup.mobile.toString()));
        payload.setToken(this.getToken());
        return payload;
    }

    /**
     * Get token
     *
     * @return
     */
    private String getToken() {
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
        GetTokenRequest getTokenRequest = new GetTokenRequest();
        getTokenRequest.setCheckoutTokenizationId("1d0a51f6-a60c-477b-94e2-403a0ed37199");
        GetTokenResponse getTokenResponse = payTrail.createGetTokenRequest(getTokenRequest);
        if (getTokenResponse == null || getTokenResponse.getReturnCode() != ResponseMessage.OK.getCode()) {
            return null;
        }
        return getTokenResponse.getData().getToken();
    }
}
