package org.knowm.xchange.coinfloor.service;

import java.util.Collection;
import java.util.Collections;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamInstrument;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParamMultiInstrument;

public class CoinfloorOpenOrdersParams
    implements OpenOrdersParamMultiInstrument, OpenOrdersParamInstrument {
  private Collection<Instrument> pairs = Collections.emptySet();
  private Instrument pair = null;

  @Override
  public Collection<Instrument> getInstruments() {
    return pairs;
  }

  @Override
  public void setInstruments(Collection<Instrument> value) {
    pairs = value;
  }

  @Override
  public Instrument getInstrument() {
    return pair;
  }

  @Override
  public void setInstrument(Instrument value) {
    pair = value;
  }

  @Override
  public boolean accept(LimitOrder order) {
    return OpenOrdersParamInstrument.super.accept(order)
        || OpenOrdersParamMultiInstrument.super.accept(order);
  }
}
