package org.knowm.xchange.bibox.dto.trade;

import java.math.BigDecimal;
import org.knowm.xchange.bibox.dto.BiboxCommand;

/** @author odrotleff */
public class BiboxTradeCommand extends BiboxCommand<BiboxTradeCommandBody> {

  public BiboxTradeCommand(
      String pair,
      int accountType,
      int orderType,
      int orderSide,
      boolean payBix,
      BigDecimal price,
      BigDecimal amount,
      BigDecimal money) {
    super(
        "orderpending/trade",
        new BiboxTradeCommandBody(
            pair, accountType, orderType, orderSide, payBix, price, amount, money));
  }
}
