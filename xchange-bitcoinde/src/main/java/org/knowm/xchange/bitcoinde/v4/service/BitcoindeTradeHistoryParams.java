package org.knowm.xchange.bitcoinde.v4.service;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamPaging;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BitcoindeTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan
    implements TradeHistoryParamCurrencyPair, TradeHistoryParamPaging {

  private CurrencyPair currencyPair;
  private Order.OrderType type;
  private BitcoindeMyTrade.State state;
  private Boolean onlyTradesWithActionForPaymentOrTransferRequired;
  private BitcoindeMyTrade.PaymentMethod paymentMethod;
  private Integer pageNumber;
  // This is used to report back the number of pages available
  private Integer lastPageNumber;

  public BitcoindeTradeHistoryParams(
      final CurrencyPair currencyPair,
      final Order.OrderType type,
      final Date startTime,
      final Date endTime,
      final BitcoindeMyTrade.State state,
      final Boolean onlyTradesWithActionForPaymentOrTransferRequired,
      final BitcoindeMyTrade.PaymentMethod paymentMethod,
      final Integer pageNumber) {

    super(startTime, endTime);
    this.currencyPair = currencyPair;
    this.type = type;
    this.state = state;
    this.onlyTradesWithActionForPaymentOrTransferRequired =
        onlyTradesWithActionForPaymentOrTransferRequired;
    this.paymentMethod = paymentMethod;
    this.pageNumber = pageNumber;
  }

  /**
   * Copy constructor
   *
   * @param other
   */
  public BitcoindeTradeHistoryParams(BitcoindeTradeHistoryParams other) {
    this(
        other.currencyPair,
        other.type,
        other.getStartTime(),
        other.getEndTime(),
        other.state,
        other.onlyTradesWithActionForPaymentOrTransferRequired,
        other.paymentMethod,
        other.getPageNumber());
  }

  // Only include to fulfill TradeHistoryParamPaging interface, can't be changed and isn't used
  @Override
  public Integer getPageLength() {
    return null;
  }

  @Override
  public void setPageLength(final Integer pageLength) {}
}
