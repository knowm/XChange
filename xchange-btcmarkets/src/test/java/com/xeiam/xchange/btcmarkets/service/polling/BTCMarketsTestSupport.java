package com.xeiam.xchange.btcmarkets.service.polling;

import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import com.xeiam.xchange.btcmarkets.dto.trade.BTCMarketsUserTrade;
import org.powermock.reflect.Whitebox;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Test utilities for btnmarkets tests.
 */
public class BTCMarketsTestSupport {

  protected BTCMarketsUserTrade createBTCMarketsUserTrade(String description, BigDecimal price,
      BigDecimal volume, BigDecimal fee, Date creationTime, BTCMarketsOrder.Side side) {

    BTCMarketsUserTrade marketsUserTrade = new BTCMarketsUserTrade();
    Whitebox.setInternalState(marketsUserTrade, "description", description);
    Whitebox.setInternalState(marketsUserTrade, "price", price);
    Whitebox.setInternalState(marketsUserTrade, "volume", volume);
    Whitebox.setInternalState(marketsUserTrade, "fee", fee);
    Whitebox.setInternalState(marketsUserTrade, "creationTime", creationTime);
    Whitebox.setInternalState(marketsUserTrade, "side", side);

    return marketsUserTrade;
  }

}
