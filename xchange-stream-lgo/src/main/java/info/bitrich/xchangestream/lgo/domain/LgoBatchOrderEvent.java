package info.bitrich.xchangestream.lgo.domain;

import java.util.Date;
import java.util.Map;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

public abstract class LgoBatchOrderEvent extends LgoOrderEvent {

  /**
   * Identifier of the batch where the event happened. Null in the case of a type=received event.
   * Not null otherwise
   */
  private Long batchId;

  protected LgoBatchOrderEvent(String type, String orderId, Date time) {
    super(type, orderId, time);
  }

  public LgoBatchOrderEvent(Long batchId, String type, String orderId, Date time) {
    super(type, orderId, time);
    this.batchId = batchId;
  }

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(long batchId) {
    this.batchId = batchId;
  }

  public abstract Order applyOnOrders(CurrencyPair currencyPair, Map<String, Order> allOrders);
}
