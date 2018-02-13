package org.knowm.xchange.cexio.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import org.knowm.xchange.cexio.dto.CexIOApiResponse;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ujjwal on 14/02/18.
 */
public class CexIOFeeInfo extends CexIOApiResponse<Map<CurrencyPair, CexIOFeeInfo.FeeDetails>> {

  public CexIOFeeInfo(@JsonProperty("e") String e, @JsonProperty("data") @JsonDeserialize(keyUsing = CurrencyPairKeyDeserializer.class)
      Map<CurrencyPair,
          FeeDetails> data,
      @JsonProperty("ok")
          String ok,
      @JsonProperty("error") String error) {
    super(e, data, ok, error);
  }

  public static class CurrencyPairKeyDeserializer extends KeyDeserializer {
    @Override
    public CurrencyPair deserializeKey(String value, DeserializationContext deserializationContext) {
      String[] currencies = value.split(":");
      return new CurrencyPair(currencies[0], currencies[1]);
    }
  }

  public static class FeeDetails {
    private final BigDecimal sell;
    private final BigDecimal sellMaker;
    private final BigDecimal buy;
    private final BigDecimal buyMaker;

    public FeeDetails(@JsonProperty("sell") BigDecimal sell, @JsonProperty("sellMaker") BigDecimal sellMaker, @JsonProperty("buy") BigDecimal buy,
        @JsonProperty("buyMaker") BigDecimal buyMaker) {
      this.sell = sell;
      this.sellMaker = sellMaker;
      this.buy = buy;
      this.buyMaker = buyMaker;
    }

    public BigDecimal getSell() {
      return sell;
    }

    public BigDecimal getSellMaker() {
      return sellMaker;
    }

    public BigDecimal getBuy() {
      return buy;
    }

    public BigDecimal getBuyMaker() {
      return buyMaker;
    }
  }
}
