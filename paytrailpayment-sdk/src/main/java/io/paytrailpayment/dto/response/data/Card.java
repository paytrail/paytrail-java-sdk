package io.paytrailpayment.dto.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Card with its details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    /**
     * Card type, for example "Visa".
     */
    private String type;

    /**
     * First 2 or 6 digits of the card number (6 for MC/VISA, 2 for Amex/Diners).
     */
    private String bin;

    /**
     * Last four digits of the card.
     */
    private String partialPan;

    /**
     * Card expiration year.
     */
    private String expireYear;

    /**
     * Card expiration month.
     */
    private String expireMonth;

    /**
     * Whether the CVC is required for paying with this card.
     * Can be one of "yes", "no" or "not_tested".
     */
    private String cvcRequired;

    /**
     * The card funding type: credit, debit, or unknown.
     */
    private String funding;

    /**
     * Card category: business, prepaid, or unknown.
     */
    private String category;

    /**
     * Country code, e.g. "FI".
     */
    private String countryCode;

    /**
     * Identifies a specific card number.
     * Cards with the same PAN but different expiry dates will have the same PAN fingerprint.
     * Hex string of length 64.
     */
    private String panFingerprint;

    /**
     * Identifies a specific card including the expiry date.
     * Hex string of length 64.
     */
    private String cardFingerprint;
}
