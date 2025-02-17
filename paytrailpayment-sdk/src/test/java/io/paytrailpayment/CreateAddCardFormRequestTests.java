package io.paytrailpayment;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.paytrailpayment.dto.request.AddCardFormRequest;
import io.paytrailpayment.dto.response.AddCardFormResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class CreateAddCardFormRequestTests extends TestCase {
    private static final String MERCHANTIDSIS = "695861";
    private static final String SECRETKEYSIS = "MONISAIPPUAKAUPPIAS";
    private PaytrailClient payTrail;

    @Before
    public void setUp() {
        payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
    }

    @Test
    public void createAddCardFormRequest_RequestNull_ReturnCode400() {
        // Arrange
        int expected = ResponseMessage.BAD_REQUEST.getCode();

        // Act
        AddCardFormRequest request = null;
        AddCardFormResponse res = payTrail.createAddCardFormRequest(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createAddCardFormRequest_ValidateFalse_ReturnCode403() {
        // Arrange
        int expected = ResponseMessage.VALIDATION_FAILED.getCode();

        // Act
        AddCardFormRequest request = new AddCardFormRequest();
        AddCardFormResponse res = payTrail.createAddCardFormRequest(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createAddCardFormRequest_Success_ReturnCode200() throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        // Arrange
        int expected = ResponseMessage.OK.getCode();

        // Act
        AddCardFormRequest request = new AddCardFormRequest();
        request.setCheckoutAccount(375917);
        request.setCheckoutAlgorithm("sha256");
        request.setCheckoutMethod("POST");
        request.setCheckoutNonce("6501220b16b7");
        request.setCheckoutTimestamp("2023-08-22T04:05:20.253Z");
        request.setCheckoutRedirectSuccessUrl("https://somedomain.com/success");
        request.setCheckoutRedirectCancelUrl("https://somedomain.com/cancel");
        request.setLanguage("EN");
        request.setSignature("542e780c253761ed64333d5485391ddd4f55d5e00b7bdc7f60f0f0d15516f889");
        AddCardFormResponse res = payTrail.createAddCardFormRequest(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }
}
