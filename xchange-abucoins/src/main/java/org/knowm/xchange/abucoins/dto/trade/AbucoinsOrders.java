package org.knowm.xchange.abucoins.dto.trade;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;
import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

/**
 * POJO representing the output JSON for the Abucoins <code>GET /orders</code> endpoint. Example:
 * <code><pre>
 * [
 *    {
 *         "id": "7786713",
 *         "price": "0.05367433",
 *         "size": "0.10451686",
 *         "product_id": "ZEC-BTC",
 *         "side": "buy",
 *         "type": "limit",
 *         "time_in_force": "GTC",
 *         "post_only": false,
 *         "created_at": "2017-09-03T03:33:17Z",
 *         "filled_size": "0.00000000",
 *         "status": "closed",
 *         "settled": false
 *     },
 *     {
 *         "id": "7786713",
 *         "price": "0.05367433",
 *         "size": "0.10451686",
 *         "product_id": "ZEC-BTC",
 *         "side": "buy",
 *         "type": "limit",
 *         "time_in_force": "GTC",
 *         "post_only": false,
 *         "created_at": "2017-09-03T03:33:17Z",
 *         "filled_size": "0.00000000",
 *         "status": "closed",
 *         "settled": false
 *     }
 * ]
 * </pre></code>
 *
 * @author bryant_harris
 */
@JsonDeserialize(using = AbucoinsOrders.AbucoinsOrdersDeserializer.class)
public class AbucoinsOrders {
  AbucoinsOrder[] orders;

  public AbucoinsOrders(AbucoinsOrder[] orders) {
    this.orders = orders;
  }

  public AbucoinsOrder[] getOrders() {
    return orders;
  }

  @Override
  public String toString() {
    return "AbucoinsOrders [orders=" + Arrays.toString(orders) + "]";
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case (json object with
   * <em>message</em> field).
   *
   * @author bryant_harris
   */
  static class AbucoinsOrdersDeserializer
      extends AbucoinsArrayOrMessageDeserializer<AbucoinsOrder, AbucoinsOrders> {
    public AbucoinsOrdersDeserializer() {
      super(AbucoinsOrder.class, AbucoinsOrders.class);
    }
  }
}
