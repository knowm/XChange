package org.knowm.xchange.abucoins.dto.account;

import java.util.Arrays;

import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <p>POJO representing the output JSON for the Abucoins
 * <code>GET /fills</code> endpoint.</p>
 *
 * Example:
 * <code><pre>
 * [
 *   {
 *     "trade_id":"785705",
 *     "product_id":"BTC-PLN",
 *     "price":"14734.55000000",
 *     "size":"100.00000000",
 *     "order_id":"4196245",
 *     "created_at":"2017-09-28T13:08:43Z",
 *     "liquidity":"T",
 *     "side":"sell"
 *   },
 *   {
 *     "trade_id":"785704",
 *     "product_id":"BTC-PLN",
 *     "price":"14734.55000000",
 *     "size":"0.01000000",
 *     "order_id":"4196245",
 *     "created_at":"2017-09-28T13:08:43Z",
 *     "liquidity":"T",
 *     "side":"sell"
 *   }
 * ]
 * </pre></code>
 * @author bryant_harris
 */
@JsonDeserialize(using = AbucoinsFills.AbucoinsFillsDeserializer.class)
public class AbucoinsFills {
  AbucoinsFill[] fills;
        
  public AbucoinsFills(AbucoinsFill[] fills) {
    this.fills = fills;
  }
          
  public AbucoinsFill[] getFills() {
    return fills;
  }

  @Override
  public String toString() {
    return "AbucoinsFills [fills=" + Arrays.toString(fills) + "]";
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case
   * (json object with <em>message</em> field).
   * @author bryant_harris
   */
  static class AbucoinsFillsDeserializer extends AbucoinsArrayOrMessageDeserializer<AbucoinsFill, AbucoinsFills> {
    public AbucoinsFillsDeserializer() {
      super(AbucoinsFill.class, AbucoinsFills.class);
    }
  }
}
