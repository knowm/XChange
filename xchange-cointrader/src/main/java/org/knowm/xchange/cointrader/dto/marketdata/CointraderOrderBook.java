package org.knowm.xchange.cointrader.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.knowm.xchange.cointrader.dto.CointraderBaseResponse;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.jackson.SqlUtcTimeDeserializer;

/**
 * @author Matija Mazi
 */
public class CointraderOrderBook extends CointraderBaseResponse<List<CointraderOrderBook.Entry>> {

  private final Integer totalCount;

  public CointraderOrderBook(@JsonProperty("data") List<Entry> data, @JsonProperty("totalCount") Integer totalCount,
      @JsonProperty("success") Boolean success, @JsonProperty("message") String message) {
    super(success, message, data);
    this.totalCount = totalCount;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  @Override
  public String toString() {
    return "CointraderOrderBook [" + getData() + "]";
  }

  @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
  public static class Entry {

    @JsonDeserialize(using = CurrencyPairDeserializer.class)
    private CurrencyPair currencyPair;

    private BigDecimal price;

    private BigDecimal quantity;

    private BigDecimal total;

    private CointraderOrder.Type type;

    @JsonProperty
    @JsonDeserialize(using = SqlUtcTimeDeserializer.class)
    private Date created;

    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }

    public BigDecimal getPrice() {
      return price;
    }

    public BigDecimal getQuantity() {
      return quantity;
    }

    public BigDecimal getTotal() {
      return total;
    }

    public CointraderOrder.Type getType() {
      return type;
    }

    public Date getCreated() {
      return created;
    }

    @Override
    public String toString() {
      return String.format("Entry{currencyPair=%s, price=%s, quantity=%s, total=%s, type=%s, created=%s}", currencyPair, price, quantity, total, type,
          created);
    }
  }
}
