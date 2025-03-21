package io.paytrailpayment;

import io.paytrailpayment.dto.request.PayAddCardRequest;
import io.paytrailpayment.dto.request.model.*;
import io.paytrailpayment.dto.response.PayAddCardResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

public class PayAndAddCardTests {
    private static final String MERCHANTIDN = "375917";
    private static final String SECRETKEY = "SAIPPUAKAUPPIAS";
    private static final String MERCHANTIDSISS = "695874";
    private static final String SECRETKEY_BAD = "SAIPPUAKAUPPIASS";

    @Test
    public void requestNullReturnCode401() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEY, "test");
        PayAddCardRequest request = null;
        PayAddCardResponse res = payTrail.payAndAddCard(request);
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void validateFalseReturnCode401() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEY, "test");
        PayAddCardRequest request = new PayAddCardRequest();
        PayAddCardResponse res = payTrail.payAndAddCard(request);
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void successReturnCode200() {
        // Arrange
        int expected = ResponseMessage.OK.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEY, "test");
        PayAddCardRequest request = new PayAddCardRequest();
        request.setStamp(UUID.randomUUID().toString());
        request.setReference("9187445");
        request.setAmount(1590);
        request.setCurrency(PaytrailCurrency.EUR);
        request.setLanguage(PaytrailLanguage.FI);
        request.setOrderId("");
        request.setItems(Arrays.asList(new Item(
                1590, 1, BigDecimal.valueOf(24), "#927502759", "Pet supplies", "Cat ladder")));
        request.setCustomer(new Customer(
                "erja.esimerkki@example.org", "Erja", "FI12345671", "nothing",
                "+358501234567", "123"));
        request.setRedirectUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        request.setCallbackUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        request.setDeliveryAddress(new Address("Tampere", "FI", "Pirkanmaa", "33100", "Hämeenkatu 6 B"));
        request.setInvoicingAddress(new Address("Helsinki", "FI", "Uusimaa", "00510", "Testikatu 1"));
        request.setGroups(Arrays.asList(PaytrailPaymentMethodGroup.mobile.toString()));

        PayAddCardResponse res = payTrail.payAndAddCard(request);
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void callPaytrailReturnNullReturnCode401() {
        // Arrange
        int expected = ResponseMessage.UNAUTHORIZED.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEY_BAD, "test");
        PayAddCardRequest request = new PayAddCardRequest();
        request.setStamp(UUID.randomUUID().toString());
        request.setReference("9187445");
        request.setAmount(1590);
        request.setCurrency(PaytrailCurrency.EUR);
        request.setLanguage(PaytrailLanguage.FI);
        request.setOrderId("");
        request.setItems(Arrays.asList(new Item(
                1590, 1, BigDecimal.valueOf(24), "#927502759", "Pet supplies", "Cat ladder")));
        request.setCustomer(new Customer(
                "erja.esimerkki@example.org", "Erja", "FI12345671", "nothing",
                "+358501234567", "123"));
        request.setRedirectUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        request.setCallbackUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        request.setDeliveryAddress(new Address("Tampere", "FI", "Pirkanmaa", "33100", "Hämeenkatu 6 B"));
        request.setInvoicingAddress(new Address("Helsinki", "FI", "Uusimaa", "00510", "Testikatu 1"));
        request.setGroups(Arrays.asList(PaytrailPaymentMethodGroup.mobile.toString()));

        PayAddCardResponse res = payTrail.payAndAddCard(request);
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void callPaytrailReturnFailReturnCode400() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEY, "test");
        PayAddCardRequest request = new PayAddCardRequest();
        request.setStamp(UUID.randomUUID().toString());
        request.setReference("9187445");
        request.setAmount(1590);
        request.setCurrency(PaytrailCurrency.EUR);
        request.setLanguage(PaytrailLanguage.FI);
        request.setOrderId("");
        request.setItems(Arrays.asList(new ShopInShopItem(
                1590, 1, BigDecimal.valueOf(24), "#927502759", "Pet supplies", "Cat ladder",
                MERCHANTIDSISS, UUID.randomUUID().toString(), "", "")));
        request.setCustomer(new Customer(
                "erja.esimerkki@example.org", "Erja", "FI12345671", "nothing",
                "+358501234567", "123"));
        request.setRedirectUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        request.setCallbackUrls(new CallbackUrl("https://ecom.example.org/success", "https://ecom.example.org/cancel"));
        request.setDeliveryAddress(new Address("Tampere", "FI", "Pirkanmaa", "33100", "Hämeenkatu 6 B"));
        request.setInvoicingAddress(new Address("Helsinki", "FI", "Uusimaa", "00510", "Testikatu 1"));
        request.setGroups(Arrays.asList(PaytrailPaymentMethodGroup.mobile.toString()));

        PayAddCardResponse res = payTrail.payAndAddCard(request);
        int actual = res.getReturnCode();

        // Assert
        Assertions.assertEquals(expected, actual);
    }
}
