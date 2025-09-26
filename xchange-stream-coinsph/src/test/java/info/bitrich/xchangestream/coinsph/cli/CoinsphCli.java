package info.bitrich.xchangestream.coinsph.cli;

import info.bitrich.xchangestream.coinsph.CoinsphStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.*;

public class CoinsphCli {

  private StreamingExchange streamingExchange;

  public static void main(String[] args) {
    CoinsphCli cli = new CoinsphCli();
    cli.run();
  }

  CoinsphCli() {
    ExchangeSpecification exSpec = new ExchangeSpecification(CoinsphStreamingExchange.class);
    exSpec.setSslUri(System.getenv("COINSPH_API_URL"));
    exSpec.setApiKey(System.getenv("COINSPH_API_KEY"));
    exSpec.setSecretKey(System.getenv("COINSPH_SECRET_KEY"));
    exSpec.setExchangeSpecificParametersItem(
        StreamingExchange.USE_SANDBOX, Boolean.valueOf(System.getenv("COINSPH_USE_SANDBOX")));

    streamingExchange = (StreamingExchange) ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

  @SneakyThrows
  public void run() {
    AccountService accountService = streamingExchange.getAccountService();

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account Info: " + accountInfo);

    //        String address =
    // accountService.requestDepositAddress(DefaultRequestDepositAddressParams.builder().currency(Currency.USDT).network("ETH").build());
    //        System.out.println("Deposit Address: " + address);
    //
    //        TradeService tradeService = streamingExchange.getTradeService();
    //        String orderId = tradeService.placeLimitOrder(new
    // LimitOrder.Builder(Order.OrderType.BID, new CurrencyPair("USDT", "PHP"))
    //                .limitPrice(new BigDecimal("57.229"))
    //                .originalAmount(new BigDecimal("5"))
    //                .build());
    //
    //        System.out.println("Order ID: " + orderId);
    //
    //        String testOrderId = orderId;
    //
    //        CurrencyPairParam currencyPairParam = OrderQueryParams
    //                .builder()
    //                .currencyPair(CurrencyPair.BTC_PHP)
    //                .orderId(testOrderId)
    //                .build();
    //
    //        Collection<Order> order =
    // tradeService.getOrder((org.knowm.xchange.service.trade.params.orders.OrderQueryParams)
    // currencyPairParam);
    //        System.out.println("Order: " + order);
    //
    //        boolean state = tradeService.cancelOrder((CancelOrderParams) currencyPairParam);
    //
    //        System.out.println("Order Cancelled: " + state);

    //        String result = accountService.withdrawFunds(NetworkWithdrawFundsParams.builder()
    //                .address("0x623FF477C34AE44047909f1BCb7351c54E22F8b5")
    //                .amount(new BigDecimal("4"))
    //                .currency(Currency.USDT)
    //                .network("ETH")
    //                .build());
    //
    //        System.out.println("Withdrawal Result: " + result);

    //        List<FundingRecord> fundingRecords =
    // accountService.getFundingHistory(CoinsphFundingHistoryParams
    //                .builder()
    //                .currency(Currency.PHP)
    //                .build());
    //
    //        System.out.println("Funding Records: " + fundingRecords);
    //
    //    StreamingMarketDataService streamingMarketDataService =
    //        streamingExchange.getStreamingMarketDataService();
    //    streamingMarketDataService
    //        .getOrderBook(CurrencyPair.BTC_PHP)
    //        .doOnEach(s -> System.out.println("Order Book: " + s))
    //        .subscribe();
    //    streamingMarketDataService
    //        .getTicker(CurrencyPair.BTC_PHP)
    //        .doOnEach(s -> System.out.println("Ticker: " + s))
    //        .subscribe();
    //    streamingExchange.connect().blockingAwait();
  }

  @Data
  @Builder
  static class OrderQueryParams
      implements org.knowm.xchange.service.trade.params.orders.OrderQueryParams,
          CurrencyPairParam,
          CancelOrderParams,
          CancelOrderByIdParams,
          CancelOrderByCurrencyPair {
    CurrencyPair currencyPair;
    String orderId;
  }
}
