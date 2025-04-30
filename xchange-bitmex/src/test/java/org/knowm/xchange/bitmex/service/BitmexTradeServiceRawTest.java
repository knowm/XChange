package org.knowm.xchange.bitmex.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitmex.BitmexExchangeWiremock;
import org.knowm.xchange.bitmex.dto.params.FilterParam;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.currency.CurrencyPair;

class BitmexTradeServiceRawTest extends BitmexExchangeWiremock {

  BitmexTradeServiceRaw bitmexTradeServiceRaw = (BitmexTradeServiceRaw) exchange.getTradeService();

  @Test
  void positions() {
    FilterParam filterParam =
        FilterParam.builder()
            .instrument(CurrencyPair.ETH_BTC)
            .instrument(new CurrencyPair("SOL/USDT"))
            .build();

    List<BitmexPosition> positions = bitmexTradeServiceRaw.getBitmexPositions(filterParam);

    assertThat(positions).hasSize(2);

    BitmexPosition expected =
        BitmexPosition.builder()
            .instrument(new CurrencyPair("SOL/USDT"))
            .crossMargin(true)
            .account(2273415)
            .isOpen(true)
            .openOrderBuyCost(new BigDecimal("0.436020"))
            .openOrderBuyQty(new BigDecimal("2"))
            .openOrderSellCost(new BigDecimal("0.665070"))
            .openOrderSellQty(new BigDecimal("3"))
            .timestamp(ZonedDateTime.parse("2024-12-14T17:25:58.097Z[UTC]"))
            .build();

    assertThat(positions)
        .first()
        .usingComparatorForType(BigDecimal::compareTo, BigDecimal.class)
        .usingRecursiveComparison()
        .isEqualTo(expected);
  }
}
