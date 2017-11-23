package org.knowm.xchange.kraken.dto.account.results;

import java.util.Map;

import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenLedger;
import org.knowm.xchange.kraken.dto.account.results.KrakenLedgerResult.KrakenLedgers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KrakenLedgerResult extends KrakenResult<KrakenLedgers> {

  /**
   * Constructor
   *
   * @param result
   * @param error
   */
  public KrakenLedgerResult(@JsonProperty("result") KrakenLedgers result, @JsonProperty("error") String[] error) {

    super(result, error);
  }

  public static class KrakenLedgers {

    private final Map<String, KrakenLedger> ledgerMap;
    private final int count;

    /**
     * Constructor
     *
     * @param ledgerMap
     */
    public KrakenLedgers(Map<String, KrakenLedger> ledgerMap) {

      this.ledgerMap = ledgerMap;
      this.count = ledgerMap.size();
    }

    /**
     * Constructor
     *
     * @param ledgerMap
     * @param count
     */
    public KrakenLedgers(@JsonProperty("ledger") Map<String, KrakenLedger> ledgerMap, @JsonProperty("count") int count) {

      this.ledgerMap = ledgerMap;
      this.count = count;
    }

    public Map<String, KrakenLedger> getLedgerMap() {

      return ledgerMap;
    }

    public int getCount() {

      return count;
    }

    @Override
    public String toString() {

      return "KrakenLedgers [ledgerMap=" + ledgerMap + ", count=" + count + "]";
    }

  }
}
