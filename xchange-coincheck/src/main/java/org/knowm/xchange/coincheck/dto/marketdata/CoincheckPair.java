package org.knowm.xchange.coincheck.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import org.knowm.xchange.coincheck.jackson.CoincheckPairDeserializer;
import org.knowm.xchange.coincheck.jackson.CoincheckPairSerializer;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Value
@AllArgsConstructor
@Builder
@JsonSerialize(using = CoincheckPairSerializer.class)
@JsonDeserialize(using = CoincheckPairDeserializer.class)
public class CoincheckPair {
  CurrencyPair pair;

  public String toString() {
    return pairToString(this);
  }

  public static String pairToString(CoincheckPair pair) {
    String base = pair.getPair().base.toString().toLowerCase(Locale.ROOT);
    String counter = pair.getPair().counter.toString().toLowerCase(Locale.ROOT);
    return base + '_' + counter;
  }

  public static CoincheckPair stringToPair(String str) {
    String sanitized = str.replace("_", "");
    CurrencyPair pair = CurrencyPairDeserializer.getCurrencyPairFromString(sanitized);
    return new CoincheckPair(pair);
  }
}
