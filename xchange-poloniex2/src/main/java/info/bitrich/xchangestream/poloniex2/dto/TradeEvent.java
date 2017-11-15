package info.bitrich.xchangestream.poloniex2.dto;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexPublicTrade;

import java.math.BigDecimal;

/**
 * Created by Lukas Zaoralek on 11.11.17.
 */
public class TradeEvent {
  private String tradeId;
  private String type;
  private BigDecimal rate;
  private BigDecimal amount;
  private String date;

  public TradeEvent(String tradeId, String type, BigDecimal rate, BigDecimal amount, String date) {
    this.tradeId = tradeId;
    this.type = type;
    this.rate = rate;
    this.amount = amount;
    this.date = date;
  }

  public String getTradeId() {
    return tradeId;
  }

  public String getType() {
    return type;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getDate() {
    return date;
  }

  public PoloniexPublicTrade toPoloniexPublicTrade() {
    PoloniexPublicTrade poloniexPublicTrade = new PoloniexPublicTrade();
    poloniexPublicTrade.setTradeID(tradeId);
    poloniexPublicTrade.setType(type);
    poloniexPublicTrade.setRate(rate);
    poloniexPublicTrade.setAmount(amount);
    BigDecimal total = rate.multiply(amount);
    poloniexPublicTrade.setTotal(total);
    poloniexPublicTrade.setDate(date);
    return poloniexPublicTrade;
  }
}
