package org.knowm.xchange.abucoins.dto.account;

import java.util.Arrays;

import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>GET /payment-methods</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 * [
 *     {
 *         "id": "sepa_pln",
 *         "type": "sepa_pln",
 *         "name": "PLN",
 *         "currency": "PLN",
 *         "allow_buy": true,
 *         "allow_sell": true,
 *         "allow_deposit": true,
 *         "allow_withdraw": true,
 *         "limits": {
 *             "buy": 10000,
 *             "sell": 10000,
 *             "deposit": 9223372036854775807,
 *             "withdraw": 9223372036854775807
 *         }
 *     },
 *     {
 *         "id": "bitcoin",
 *         "type": "bitcoin",
 *         "name": "Bitcoin",
 *         "currency": "BTC",
 *         "allow_buy": true,
 *         "allow_sell": true,
 *         "allow_deposit": true,
 *         "allow_withdraw": true,
 *         "limits": {
 *             "buy": 10000,
 *             "sell": 10000,
 *             "deposit": 5,
 *             "withdraw": 0.5
 *         }
 *     }
 * ] 
 * </pre></code>
 * @author bryant_harris
 */
@JsonDeserialize(using = AbucoinsPaymentMethods.AbucoinsPaymentMethodsDeserializer.class)
public class AbucoinsPaymentMethods {
  AbucoinsPaymentMethod[] paymentMethods;
                
  public AbucoinsPaymentMethods(AbucoinsPaymentMethod[] paymentMethods) {
    this.paymentMethods = paymentMethods;
  }

  public AbucoinsPaymentMethod[] getPaymentMethods() {
    return paymentMethods;
  }

  @Override
  public String toString() {
    return "AbucoinsPaymentMethods [paymentMethods=" + Arrays.toString(paymentMethods) + "]";
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case
   * (json object with <em>message</em> field).
   * @author bryant_harris
   */
  static class AbucoinsPaymentMethodsDeserializer extends AbucoinsArrayOrMessageDeserializer<AbucoinsPaymentMethod, AbucoinsPaymentMethods> {
    public AbucoinsPaymentMethodsDeserializer() {
      super(AbucoinsPaymentMethod.class, AbucoinsPaymentMethods.class);
    }
  }
}
