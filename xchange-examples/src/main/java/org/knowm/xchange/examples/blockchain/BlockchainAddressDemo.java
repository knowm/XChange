package org.knowm.xchange.examples.blockchain;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

/** @author timmolter */
public class BlockchainAddressDemo {

  private static final String API_KEY = "";
  private static final String API_SECRET = "";
  private static final Exchange BLOCKCHAIN_EXCHANGE = ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class, API_KEY, API_SECRET);
  private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  public static void main(String[] args) throws IOException {
    System.out.println("===== MARKET DATA SERVICE =====");
    marketDataServiceDemo();
    System.out.println("===== ACCOUNT SERVICE =====");
    accountServiceDemo();
    System.out.println("===== TRADE SERVICE =====");
    tradeServiceDemo();
  }

  private static void marketDataServiceDemo() throws IOException {
    MarketDataService marketDataService = BLOCKCHAIN_EXCHANGE.getMarketDataService();

    System.out.println("===== getTicker =====");
    Ticker ticker = marketDataService.getTicker(CurrencyPair.ETH_BTC);
    System.out.println(objectMapper.writeValueAsString(ticker));

    System.out.println("===== getTickers =====");
    List<Ticker> tickers = marketDataService.getTickers(null);
    System.out.println(objectMapper.writeValueAsString(tickers));

    System.out.println("===== getOrderBook =====");
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);
    System.out.println(objectMapper.writeValueAsString(orderBook));

    System.out.println("===== getTrades =====");
    Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);
    System.out.println(objectMapper.writeValueAsString(trades));
  }

  private static void accountServiceDemo() throws IOException {
    AccountService accountService = BLOCKCHAIN_EXCHANGE.getAccountService();

    System.out.println("===== withdrawFunds =====");
    String withdraw = accountService.withdrawFunds(Currency.BTC, BigDecimal.valueOf(0.00005), "ea1f34b3-e77a-4646-9cfa-5d6d3518c6d3");
    System.out.println(objectMapper.writeValueAsString(withdraw));

    System.out.println("===== requestDepositAddress =====");
    String address = accountService.requestDepositAddress(Currency.ETH);
    System.out.println(objectMapper.writeValueAsString(address));

    System.out.println("===== requestDepositAddressData =====");
    AddressWithTag addressWithTag = accountService.requestDepositAddressData(Currency.ETH);
    System.out.println(objectMapper.writeValueAsString(addressWithTag));

    System.out.println("===== getFundingHistory =====");
    TradeHistoryParams params = accountService.createFundingHistoryParams();

    if (params instanceof TradeHistoryParamsTimeSpan) {
      final TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
      timeSpanParam.setStartTime(
              new Date(System.currentTimeMillis() - (1 * 12 * 30 * 24 * 60 * 60 * 1000L)));
    }
    if (params instanceof HistoryParamsFundingType) {
      ((HistoryParamsFundingType) params).setType(FundingRecord.Type.DEPOSIT);
    }

    List<FundingRecord> fundingDepositsRecords = accountService.getFundingHistory(params);
    System.out.println(objectMapper.writeValueAsString(fundingDepositsRecords));

    if (params instanceof HistoryParamsFundingType) {
      ((HistoryParamsFundingType) params).setType(FundingRecord.Type.WITHDRAWAL);
    }

    List<FundingRecord> fundingWithdrawalRecords = accountService.getFundingHistory(params);
    System.out.println(objectMapper.writeValueAsString(fundingWithdrawalRecords));

    System.out.println("===== getDynamicTradingFees =====");
    Map<CurrencyPair, Fee> tradingFees = accountService.getDynamicTradingFees();
    System.out.println(objectMapper.writeValueAsString(tradingFees));

  }

  private static void tradeServiceDemo() throws IOException {
    TradeService tradeService = BLOCKCHAIN_EXCHANGE.getTradeService();

    LimitOrder order = new LimitOrder.Builder(Order.OrderType.ASK, new CurrencyPair(Currency.USDT, Currency.USD))
            .limitPrice(BigDecimal.valueOf(1.0))
            .originalAmount(BigDecimal.valueOf(5.0))
            .build();

    System.out.println("===== placeLimitOrder =====");
//    String limitOrderId = tradeService.placeLimitOrder(order);
//    System.out.println(limitOrderId);

    System.out.println("===== getOpenOrders =====");
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(objectMapper.writeValueAsString(openOrders));


    System.out.println("===== getTradeHistory =====");
    UserTrades userTrades = tradeService.getTradeHistory(null);
    System.out.println(objectMapper.writeValueAsString(userTrades));
  }
}
