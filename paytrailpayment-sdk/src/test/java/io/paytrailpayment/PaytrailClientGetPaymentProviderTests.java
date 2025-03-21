package io.paytrailpayment;

import io.paytrailpayment.dto.request.GetPaymentProvidersRequest;
import io.paytrailpayment.dto.request.model.PaytrailPaymentMethodGroup;
import io.paytrailpayment.dto.response.GetPaymentProvidersResponse;
import io.paytrailpayment.dto.response.data.DataResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PaytrailClientGetPaymentProviderTests {

    private static final String MERCHANTIDN = "375917";
    private static final String MERCHANTIDSIS = "695861";
    private static final String SECRETKEYSIS = "MONISAIPPUAKAUPPIAS";

    @Test
    public void getPaymentProviderRequestNullReturnCode400() {
        // Arrange
        int expected = ResponseMessage.RESPONSE_NULL.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");

        // Act
        GetPaymentProvidersResponse res = payTrail.getPaymentProviders(null);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getPaymentProviderValidateFalseReturnCode403() {
        // Arrange
        int expected = ResponseMessage.VALIDATION_FAILED.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
        GetPaymentProvidersRequest request = new GetPaymentProvidersRequest();

        // Act
        GetPaymentProvidersResponse res = payTrail.getPaymentProviders(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getPaymentProviderSuccessReturnCode200() {
        // Arrange
        int expected = ResponseMessage.OK.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");

        GetPaymentProvidersRequest request = new GetPaymentProvidersRequest();
        request.setAmount(100);
        request.setGroups(Collections.singletonList(PaytrailPaymentMethodGroup.creditcard));

        // Act
        GetPaymentProvidersResponse res = payTrail.getPaymentProviders(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getGroupPaymentProviderCallPaytrailReturnFailReturnCode500() {
        // Arrange
        int expected = ResponseMessage.RESPONSE_ERROR.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        GetPaymentProvidersRequest request = new GetPaymentProvidersRequest();
        request.setAmount(100);
        request.setGroups(Collections.singletonList(PaytrailPaymentMethodGroup.creditcard));

        // Mock the handleRequest method to return a failed response
        PaytrailClient spyPayTrail = Mockito.spy(payTrail);
        DataResponse mockDataResponse = new DataResponse();
        mockDataResponse.setStatusCode(ResponseMessage.RESPONSE_ERROR.getCode());
        Mockito.doReturn(mockDataResponse).when(spyPayTrail).handleRequest(any(), any(), any(), any(), any());

        // Act
        GetPaymentProvidersResponse res = spyPayTrail.getPaymentProviders(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }
}