package org.knowm.xchange.bleutrade.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.bleutrade.dto.marketdata.BleutradeTicker;
import org.knowm.xchange.bleutrade.service.BleutradeMarketDataService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.CertHelper;

@Ignore("Dead or Portugal IP only")
/** @author timmolter */
public class TickerFetchIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    CertHelper.trustAllCerts();

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BleutradeExchange.class);
    exchange.remoteInit();
    MarketDataService marketDataService = exchange.getMarketDataService();

    // TODO: getTicker() call with a single currency pair returns a result json object, not a result
    // json array
    // {"success":true,"message":"","result":{"Ask":"8014.39600000","Average":"7717.79135089","BaseCurrency":"CBRL","BaseVolume":"1291.33706711","Bid":"7944.64770000","High":"7979.64770000","IsActive":"true","Last":"7979.64770000","Low":"7347.52880000","MarketCurrency":"ETH","MarketName":"ETH_CBRL","PrevDay":"7347.52880000","TimeStamp":"2021-02-03 16:03:51","Volume":"0.16731951"}}
    List<BleutradeTicker> tickers =
        ((BleutradeMarketDataService) marketDataService).getBleutradeTickers();
    //    System.out.println(ticker.toString());
    assertThat(tickers).isNotNull();
  }
}
