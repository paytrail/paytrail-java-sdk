// PaytrailClientGetGroupPaymentProviderTests.java
package io.paytrailpayment;

import io.paytrailpayment.dto.request.GetGroupedPaymentProvidersRequest;
import io.paytrailpayment.dto.request.model.PaytrailPaymentMethodGroup;
import io.paytrailpayment.dto.response.GetGroupedPaymentProvidersResponse;
import io.paytrailpayment.dto.response.data.DataResponse;
import io.paytrailpayment.utilites.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PaytrailClientGetGroupPaymentProviderTests {

    private static final String MERCHANTIDN = "375917";
    private static final String MERCHANTIDSIS = "695861";
    private static final String SECRETKEYSIS = "MONISAIPPUAKAUPPIAS";

    @Test
    public void getGroupPaymentProviderRequestNullReturnCode400() {
        // Arrange
        int expected = ResponseMessage.RESPONSE_NULL.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");

        // Act
        GetGroupedPaymentProvidersResponse res = payTrail.getGroupedPaymentProviders(null);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getGroupPaymentProviderValidateFalseReturnCode403() {
        // Arrange
        int expected = ResponseMessage.VALIDATION_FAILED.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
        GetGroupedPaymentProvidersRequest request = new GetGroupedPaymentProvidersRequest();
        GetGroupedPaymentProvidersResponse res = payTrail.getGroupedPaymentProviders(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getGroupPaymentProviderSuccessReturnCode200() {
        // Arrange
        int expected = ResponseMessage.OK.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDSIS, SECRETKEYSIS, "test");
        GetGroupedPaymentProvidersRequest request = new GetGroupedPaymentProvidersRequest();
        request.setAmount(100);
        request.setGroups(Collections.singletonList(PaytrailPaymentMethodGroup.creditcard));

        // Act
        GetGroupedPaymentProvidersResponse res = payTrail.getGroupedPaymentProviders(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getGroupPaymentProviderCallPaytrailReturnFailReturnCode500() {
        // Arrange
        int expected = ResponseMessage.RESPONSE_ERROR.getCode();
        PaytrailClient payTrail = new PaytrailClient(MERCHANTIDN, SECRETKEYSIS, "test");
        GetGroupedPaymentProvidersRequest request = new GetGroupedPaymentProvidersRequest();
        request.setAmount(100);
        request.setGroups(Collections.singletonList(PaytrailPaymentMethodGroup.creditcard));

        // Mock the handleRequest method to return a failed response
        PaytrailClient spyPayTrail = Mockito.spy(payTrail);
        DataResponse mockDataResponse = new DataResponse();
        mockDataResponse.setStatusCode(ResponseMessage.RESPONSE_ERROR.getCode());
        Mockito.doReturn(mockDataResponse).when(spyPayTrail).handleRequest(any(), any(), any(), any(), any());

        // Act
        GetGroupedPaymentProvidersResponse res = spyPayTrail.getGroupedPaymentProviders(request);
        int actual = res.getReturnCode();

        // Assert
        assertEquals(expected, actual);
    }
}