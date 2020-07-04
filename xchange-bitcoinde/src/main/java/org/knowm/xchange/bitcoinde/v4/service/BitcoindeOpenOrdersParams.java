package org.knowm.xchange.bitcoinde.v4.service;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.BitcoindeAdapters;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamOffset;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BitcoindeOpenOrdersParams
    implements OpenOrdersParamCurrencyPair, OpenOrdersParamOffset {

  private CurrencyPair currencyPair;
  private BitcoindeType type;
  private BitcoindeOrderState state;
  private Date start;
  private Date end;
  private Integer offset;

  public BitcoindeOpenOrdersParams(final BitcoindeOrderState state) {
    this.state = state;
  }

  @Override
  public boolean accept(final LimitOrder order) {
    return accept((Order) order);
  }

  @Override
  public boolean accept(final Order order) {
    return order != null
        && order.getInstrument().equals(currencyPair)
        && order.getType() == BitcoindeAdapters.adaptOrderType(this.type)
        && order.getStatus() == BitcoindeAdapters.adaptOrderStatus(this.state)
        && !order.getTimestamp().before(this.start)
        && !order.getTimestamp().after(this.end);
  }
}
