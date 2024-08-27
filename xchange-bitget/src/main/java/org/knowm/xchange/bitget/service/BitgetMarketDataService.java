package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.config.Config;
import org.knowm.xchange.dto.meta.ExchangeHealth;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitgetMarketDataService extends BitgetMarketDataServiceRaw implements
    MarketDataService {

  public BitgetMarketDataService(BitgetExchange exchange) {
    super(exchange);
  }

  @Override
  public ExchangeHealth getExchangeHealth() {
    try {
      Instant serverTime = getBitgetServerTime().getServerTime();
      Instant localTime = Instant.now(Config.getInstance().getClock());

      // timestamps shouldn't diverge by more than 10 minutes
      if (Duration.between(serverTime, localTime).toMinutes() < 10) {
        return ExchangeHealth.ONLINE;
      }
//    } catch (BitgetException | IOException e) {
    } catch (IOException e) {
      return ExchangeHealth.OFFLINE;
    }

    return ExchangeHealth.OFFLINE;
  }

}
