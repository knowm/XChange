package info.bitrich.xchangestream.binance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction;
import info.bitrich.xchangestream.binance.dto.ExecutionReportBinanceUserTransaction.ExecutionType;
import info.bitrich.xchangestream.core.StreamingTradeService;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.io.IOException;

import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.trade.margin.MarginAccountType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class BinanceStreamingTradeService implements StreamingTradeService {

  private final Subject<ExecutionReportBinanceUserTransaction> executionReportsPublisher =
      PublishSubject.<ExecutionReportBinanceUserTransaction>create().toSerialized();

  private volatile Disposable executionReports;
  private volatile BinanceUserDataStreamingService binanceUserDataStreamingService;
  private final MarginAccountType marginAccountType;

  private final ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  public BinanceStreamingTradeService(
      BinanceExchange exchange,
      BinanceUserDataStreamingService binanceUserDataStreamingService,
      MarginAccountType marginAccountType) {
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;

    boolean includeMarginAccountTypeInOrderId = Boolean.TRUE.equals(
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem(BinanceExchange.SPECIFIC_PARAM_INCLUDE_MARGIN_ACCOUNT_TYPE_IN_ORDER_ID));
    // ignore margin account type if it doesn't have to be included in order ID
    this.marginAccountType = includeMarginAccountTypeInOrderId ? marginAccountType : null;
  }

  public Observable<ExecutionReportBinanceUserTransaction> getRawExecutionReports() {
    if (binanceUserDataStreamingService == null || !binanceUserDataStreamingService.isSocketOpen())
      throw new ExchangeSecurityException("Not authenticated");
    return executionReportsPublisher;
  }

  public Observable<Order> getOrderChanges() {
    return getRawExecutionReports()
        .filter(r -> !r.getExecutionType().equals(ExecutionType.REJECTED))
        .map((ExecutionReportBinanceUserTransaction transaction) -> transaction.toOrder(marginAccountType));
  }

  @Override
  public Observable<Order> getOrderChanges(CurrencyPair currencyPair, Object... args) {
    return getOrderChanges().filter(oc -> currencyPair.equals(oc.getCurrencyPair()));
  }

  public Observable<UserTrade> getUserTrades() {
    return getRawExecutionReports()
        .filter(r -> r.getExecutionType().equals(ExecutionType.TRADE))
        .map((ExecutionReportBinanceUserTransaction transaction) -> transaction.toUserTrade(marginAccountType));
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {
    return getUserTrades().filter(t -> t.getCurrencyPair().equals(currencyPair));
  }

  /** Registers subsriptions with the streaming service for the given products. */
  public void openSubscriptions() {
    if (binanceUserDataStreamingService != null) {
      executionReports =
          binanceUserDataStreamingService
              .subscribeChannel(
                  BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.EXECUTION_REPORT)
              .map(this::executionReport)
              .subscribe(executionReportsPublisher::onNext);
    }
  }

  /**
   * User data subscriptions may have to persist across multiple socket connections to different
   * URLs and therefore must act in a publisher fashion so that subscribers get an uninterrupted
   * stream.
   */
  void setUserDataStreamingService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    if (executionReports != null && !executionReports.isDisposed()) executionReports.dispose();
    this.binanceUserDataStreamingService = binanceUserDataStreamingService;
    openSubscriptions();
  }

  private ExecutionReportBinanceUserTransaction executionReport(JsonNode json) {
    try {
      return mapper.treeToValue(json, ExecutionReportBinanceUserTransaction.class);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse execution report", e);
    }
  }
}
