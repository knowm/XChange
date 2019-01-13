package org.knowm.xchange.btcturk;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.account.BTCTurkAccountBalance;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkExchangeResult;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOpenOrders;
import org.knowm.xchange.btcturk.service.BTCTurkMarketDataService;
import org.knowm.xchange.btcturk.service.BTCTurkAccountService;
import org.knowm.xchange.btcturk.service.BTCTurkTradeService;
import org.knowm.xchange.btcturk.service.BTCTurkTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;

/** @author mertguner */
public class BTCTurkAuthenticatedServiceTest {

	public static final String BTCTURK_APIKEY = "";
	public static final String BTCTURK_SECRETKEY = "";
	
	public static Exchange createExchange() 
	{
	    ExchangeSpecification exSpec = new BTCTurkExchange().getDefaultExchangeSpecification();
    	exSpec.setApiKey(BTCTURK_APIKEY);
    	exSpec.setSecretKey(BTCTURK_SECRETKEY);    	
	    return ExchangeFactory.INSTANCE.createExchange(exSpec);
	  }
	
	 @Test
	  public void testOrderBookAdapter() throws IOException {
		 
		 Exchange btcTurk = createExchange();
		 MarketDataService marketDataService = btcTurk.getMarketDataService();
		 AccountService accountService = btcTurk.getAccountService();
		 TradeService tradeService = btcTurk.getTradeService();
		 
		 BTCTurkTicker ticker = ((BTCTurkMarketDataService)marketDataService).getBTCTurkTicker(CurrencyPair.BTC_TRY);
		 assertThat(ticker.getHigh()).isNotEqualTo(new BigDecimal("0"));
		 
		 BTCTurkAccountBalance balance = ((BTCTurkAccountService)accountService).getBTCTurkBalance();		 
		 assertThat(balance.getBtc_available()).isNotEqualTo(new BigDecimal("0"));
		 
		 List<BTCTurkOpenOrders> openOrders = ((BTCTurkTradeService)tradeService).getOpenOrders(CurrencyPair.ETH_TRY);

		 BTCTurkTradeServiceRaw tradeServiceRaw = ((BTCTurkTradeService)tradeService);
		 Boolean result = false;
		 if(openOrders.isEmpty())
		 {
			BTCTurkExchangeResult exchangeResult = ((BTCTurkTradeService)tradeService).placeLimitOrder(balance.getEth_balance(), new BigDecimal(713), CurrencyPair.ETH_TRY, BTCTurkOrderTypes.SELL);
			result = tradeServiceRaw.cancelOrder(exchangeResult.getId());
		 }
		 else 
		 {
			 result = tradeServiceRaw.cancelOrder(openOrders.get(0).getId());			 
		 }
		 assertThat(result).isEqualTo(true);
	 }
	 
}
