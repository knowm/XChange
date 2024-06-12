package org.knowm.xchange.bitflyer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitflyer.BitflyerAdapters;
import org.knowm.xchange.bitflyer.BitflyerUtils;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitflyerTradeService extends BitflyerTradeServiceRaw implements TradeService {
  private static final Logger LOG = LoggerFactory.getLogger(BitflyerTradeService.class);

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitflyerTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    return BitflyerAdapters.adaptOrderId(super.sendChildOrder(marketOrder));
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    return BitflyerAdapters.adaptOrderId(super.sendChildOrder(limitOrder));
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    List<Instrument> pairs = exchange.getExchangeInstruments();

    // US and EUR only have one pair available
    if (pairs.size() == 1) {
      return BitflyerAdapters.adaptOpenOrdersFromChildOrderResults(
          super.getChildOrders(
              BitflyerUtils.bitflyerProductCode((CurrencyPair) pairs.get(0)), "ACTIVE"));
    }

    // JPY has about three pairs so we need to combine the results
    List<LimitOrder> orders = new ArrayList<>();

    pairs.forEach(
        pair -> {
          try {
            orders.addAll(
                BitflyerAdapters.adaptOpenOrdersFromChildOrderResults(
                        super.getChildOrders(
                            BitflyerUtils.bitflyerProductCode((CurrencyPair) pair), "ACTIVE"))
                    .getOpenOrders());
          } catch (IOException e) {
            LOG.trace("IOException adapting open orders for {}", pair, e);
          }
        });

    return new OpenOrders(orders);
  }
}
