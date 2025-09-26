package org.knowm.xchange.bitso.service;

import java.math.BigDecimal;
import java.util.List;
import lombok.SneakyThrows;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitso.BitsoExchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.FiatWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.NetworkWithdrawFundsParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.withdrawals.Address;
import org.knowm.xchange.service.trade.params.withdrawals.Bank;
import org.knowm.xchange.service.trade.params.withdrawals.Beneficiary;

public class BitsoCli {
  public static void main(String[] args) {
    new BitsoCli().run();
  }

  @SneakyThrows
  void run() {
    ExchangeSpecification exSpec = new ExchangeSpecification(BitsoExchange.class);
    exSpec.setSslUri(System.getenv("BITSO_API_URL"));
    exSpec.setApiKey(System.getenv("BITSO_API_KEY"));
    exSpec.setSecretKey(System.getenv("BITSO_SECRET_KEY"));
    exSpec.setExchangeSpecificParametersItem(
        Exchange.USE_SANDBOX, Boolean.valueOf(System.getenv("BITSO_USE_SANDBOX")));

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    AccountService accountService = exchange.getAccountService();
    MarketDataService marketDataService = exchange.getMarketDataService();
    TradeService tradeService = exchange.getTradeService();

    CurrencyPair usdcMxn = new CurrencyPair(Currency.USD, Currency.MXN);

    // 1. Get latest order book for BTC MXN
    System.out.println("=== 1. Getting USDC/MXN Order Book ===");
    OrderBook orderBook = marketDataService.getOrderBook(usdcMxn);
    System.out.println("Order Book: " + orderBook);
    System.out.println("Best Bid: " + orderBook.getBids().get(0));
    System.out.println("Best Ask: " + orderBook.getAsks().get(0));

    // 2. Place the smallest allowed BUY order slightly below the top bid
    System.out.println("\n=== 2. Placing BUY Order Below Top Bid ===");
    BigDecimal topBidPrice = orderBook.getBids().get(0).getLimitPrice();
    BigDecimal orderPrice = topBidPrice.subtract(BigDecimal.TEN);
    BigDecimal minOrderAmount = new BigDecimal("5");

    LimitOrder buyOrder =
        new LimitOrder(Order.OrderType.BID, minOrderAmount, usdcMxn, null, null, orderPrice);
    String orderId = tradeService.placeLimitOrder(buyOrder);
    System.out.println("Order placed with ID: " + orderId);

    // 3. Check the order status
    System.out.println("\n=== 3. Checking Order Status ===");
    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders);

    // 4. Cancel the order
    System.out.println("\n=== 4. Canceling Order ===");
    boolean cancelled = tradeService.cancelOrder(orderId);
    System.out.println("Order cancelled: " + cancelled);

    // 5. Get the latest ticker for BTC MXN
    System.out.println("\n=== 5. Getting USDC/MXN Ticker ===");
    Ticker ticker = marketDataService.getTicker(usdcMxn);
    System.out.println("Ticker: " + ticker);

    // 6. Get a deposit address for USDC on Ethereum
    System.out.println("\n=== 6. Getting USDC Deposit Address (Ethereum) ===");
    try {
      String usdcDepositAddress = accountService.requestDepositAddress(Currency.USDC, "ETH");
      System.out.println("USDC Deposit Address (Ethereum): " + usdcDepositAddress);
    } catch (Exception e) {
      System.out.println("Error getting USDC deposit address: " + e.getMessage());
    }

    // 7. List withdrawals
    System.out.println("\n=== 7. Listing Withdrawals ===");
    try {
      TradeHistoryParams withdrawalParams =
          new HistoryParamsFundingType() {
            @Override
            public FundingRecord.Type getType() {
              return FundingRecord.Type.WITHDRAWAL;
            }

            @Override
            public void setType(FundingRecord.Type type) {}
          };
      List<FundingRecord> withdrawals = accountService.getFundingHistory(withdrawalParams);
      System.out.println("Withdrawals: " + withdrawals);
    } catch (Exception e) {
      System.out.println("Error listing withdrawals: " + e.getMessage());
    }

    // 8. List deposits
    System.out.println("\n=== 8. Listing Deposits ===");
    try {
      TradeHistoryParams depositParams =
          new HistoryParamsFundingType() {
            @Override
            public FundingRecord.Type getType() {
              return FundingRecord.Type.DEPOSIT;
            }

            @Override
            public void setType(FundingRecord.Type type) {}
          };
      List<FundingRecord> deposits = accountService.getFundingHistory(depositParams);
      System.out.println("Deposits: " + deposits);
    } catch (Exception e) {
      System.out.println("Error listing deposits: " + e.getMessage());
    }

    //     9. Create a 5 USDC withdrawal to 0xb99c32Ff829a3ce731303e7fae94d7CEdd3223Ba
    System.out.println("\n=== 9. Creating 5 USDC Withdrawal ===");
    try {
      NetworkWithdrawFundsParams withdrawalParams =
          NetworkWithdrawFundsParams.builder()
              .currency(Currency.USDC)
              .amount(new BigDecimal("5"))
              .address("0xb99c32Ff829a3ce731303e7fae94d7CEdd3223Ba")
              .network("eth_erc20")
              .build();

      String withdrawalId = accountService.withdrawFunds(withdrawalParams);
      System.out.println("USDC withdrawal created with ID: " + withdrawalId);
    } catch (Exception e) {
      System.out.println("Error creating USDC withdrawal: " + e.getMessage());
    }

    System.out.println("\n=== 9. Creating 5 USDC Withdrawal ===");
    try {
      FiatWithdrawFundsParams withdrawalParams =
          FiatWithdrawFundsParams.builder()
              .currency(Currency.MXN)
              .amount(new BigDecimal("10"))
              .beneficiary(
                  new Beneficiary() {
                    @Override
                    public String getId() {
                      return "";
                    }

                    @Override
                    public String getName() {
                      return "";
                    }

                    @Override
                    public String getAccountNumber() {
                      return "";
                    }

                    @Override
                    public Address getAddress() {
                      return null;
                    }

                    @Override
                    public String getReference() {
                      return "";
                    }

                    @Override
                    public Bank getBank() {
                      return null;
                    }
                  })
              .build();

      String withdrawalId = accountService.withdrawFunds(withdrawalParams);
      System.out.println("USDC withdrawal created with ID: " + withdrawalId);
    } catch (Exception e) {
      System.out.println("Error creating USDC withdrawal: " + e.getMessage());
    }

    // Display account info for reference
    System.out.println("\n=== Account Info ===");
    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println("Account Info: " + accountInfo);
  }
}
