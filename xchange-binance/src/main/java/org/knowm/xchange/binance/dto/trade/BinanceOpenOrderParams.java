package org.knowm.xchange.binance.dto.trade;

import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderByInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;

public class BinanceOpenOrderParams implements OpenOrdersParamInstrument  {

  private Instrument pair;
  private Boolean isMarginOrder;
  public BinanceOpenOrderParams(Instrument pair) {
    this.pair = pair;
    this.isMarginOrder = false;
  }


  public BinanceOpenOrderParams(Instrument pair, Boolean isMarginOrder) {
    this.pair = pair;
    this.isMarginOrder=isMarginOrder;
  }
  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public void setInstrument(Instrument pair) {
    this.pair=pair;
  }




  public Boolean getIsMarginOrder() {
    return isMarginOrder;
  }

  public void setIsMarginOrder(Boolean isMarginOrder) {
    this.isMarginOrder = isMarginOrder;
}
}


