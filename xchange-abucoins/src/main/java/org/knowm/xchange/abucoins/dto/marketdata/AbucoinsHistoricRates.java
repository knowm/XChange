package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;
import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

@JsonDeserialize(using = AbucoinsHistoricRates.AbucoinsHistoricRatesDeserializer.class)
public class AbucoinsHistoricRates {
  @JsonProperty("message")
  public String message; // for error conditions

  AbucoinsHistoricRate[] historicRates;

  public AbucoinsHistoricRates() {}

  public AbucoinsHistoricRates(AbucoinsHistoricRate[] historicRates) {
    this.historicRates = historicRates;
  }

  public AbucoinsHistoricRate[] getHistoricRates() {
    return historicRates;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return "AbucoinsHistoricRates [historicRates=" + Arrays.toString(historicRates) + "]";
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case (json object with
   * <em>message</em> field).
   *
   * @author bryant_harris
   */
  static class AbucoinsHistoricRatesDeserializer
      extends AbucoinsArrayOrMessageDeserializer<AbucoinsHistoricRate, AbucoinsHistoricRates> {
    public AbucoinsHistoricRatesDeserializer() {
      super(AbucoinsHistoricRate.class, AbucoinsHistoricRates.class, true);
    }
  }
}
