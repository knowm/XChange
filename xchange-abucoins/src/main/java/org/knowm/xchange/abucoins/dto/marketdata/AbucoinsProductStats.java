package org.knowm.xchange.abucoins.dto.marketdata;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Arrays;
import org.knowm.xchange.abucoins.service.AbucoinsArrayOrMessageDeserializer;

@JsonDeserialize(using = AbucoinsProductStats.AbucoinsProductStatsDeserializer.class)
public class AbucoinsProductStats {
  AbucoinsProductStat[] stats;

  public AbucoinsProductStats(AbucoinsProductStat[] stats) {
    this.stats = stats;
  }

  public AbucoinsProductStat[] getStats() {
    return stats;
  }

  @Override
  public String toString() {
    return "AbucoinsProductStats [stats=" + Arrays.toString(stats) + "]";
  }

  /**
   * Deserializer handles the success case (array json) as well as the error case (json object with
   * <em>message</em> field).
   *
   * @author bryant_harris
   */
  static class AbucoinsProductStatsDeserializer
      extends AbucoinsArrayOrMessageDeserializer<AbucoinsProductStat, AbucoinsProductStats> {
    public AbucoinsProductStatsDeserializer() {
      super(AbucoinsProductStat.class, AbucoinsProductStats.class, true);
    }
  }
}
