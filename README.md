# Paytrail Java SDK

The goal for this project is to develop a Java SDK for the [Paytrail payment service](https://www.paytrail.com/en). The aim is to provide Java developers with an easier and more streamlined way to integrate our API into their applications.

The initial version of the Java SDK will be focused on basic payment handling and backend usage, specifically enabling communication with the [Paytrail payment API](https://docs.paytrail.com/#/). As such, it will not include any frontend functionalities, but we may provide examples for frontend implementation at a later stage.

## Paytrail Payment Service

Paytrail is a payment gateway that offers 20+ payment methods for Finnish customers.

The payment gateway provides all the popular payment methods with one simple integration. The provided payment methods include, but are not limited to, credit cards, online banking and mobile payments.

To use the payment service, you need to sign up for a Paytrail account. Transaction fees will be charged for every transaction. Transaction cost may vary from merchant to merchant, based on what is agreed upon with Paytrail when negotiating your contract. For more information and registration, please visit our [website](https://www.paytrail.com/en) or [contact us](https://www.paytrail.com/en/contact) directly.

## Requirements

### General requirements

- Java 8 or later

### Development requirements

- [JUnit](https://junit.org/junit5/) - JUnit is the current generation of the JUnit testing framework, which provides a modern foundation for developer-side testing on the JVM. This includes focusing on Java 8 and above, as well as enabling many different styles of testing.

## Usage

```java
package io.paytrailpayment;

import io.paytrailpayment.dto.response.GetPaymentResponse;

public class Main {
    public static void main(String[] args) {
        PaytrailClient client = new PaytrailClient("xxx", "xxx", "xxx");
        private final String transactionId = "xxx";

        GetPaymentResponse res = client.getPaymentInfo(transactionId);

        System.out.println(res);
    }
}
```
## Basic functionalities

The Paytrail JAVA-SDK supports most of the functionalities of the [Paytrail Payment API](https://paytrail.github.io/api-documentation/#/).

Some of the key features are:

### Payments and refunds

- [Creating payment request](https://paytrail.github.io/api-documentation/#/?id=create)
- [Creating payment status request](https://paytrail.github.io/api-documentation/#/?id=get)
- [Creating refund request](https://paytrail.github.io/api-documentation/#/?id=refund)

## Methods

List of `PaytrailClient::class` methods

| Method                        | Description           |
| ----------------------------- |-----------------------|
| createPayment()               | Create payment        |
| getPaymentInfo()              | Get payment info      |
| createRefundRequest()         | Create refund request |

---

### Testing ###

You can test our payment services in your online store before signing an agreement. Payments made using [test credentials](https://docs.paytrail.com/#/?id=test-credentials) will not be processed.

With test credentials, you can test most of the payment methods included in Paytrail’s payment service. You can find the payment method specific credentials needed for testing in Paytrail’s [documentation](https://docs.paytrail.com/#/payment-method-providers).

---

**_Disclaimer:_** _This open source project is made available to assist coders in getting started with our API. However, we do not provide any warranty or guarantee that the code will work as intended and offer limited support for it. Use at your own risk._
