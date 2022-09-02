package org.knowm.xchange.kraken.dto.account.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.kraken.dto.KrakenResult;
import org.knowm.xchange.kraken.dto.account.KrakenStaking;

public class KrakenStakingTransactionsResult extends KrakenResult<KrakenStakingTransactionsResult.KrakenStakings> {

    /**
     * Constructor
     *
     * @param result
     * @param error
     */
    public KrakenStakingTransactionsResult(
            @JsonProperty("result") KrakenStakings result,
            @JsonProperty("error") String[] error) {
        super(result, error);
    }

    public static class KrakenStakings {
        private final KrakenStaking[] stakings;
        private int count;

        public KrakenStakings(KrakenStakings result) {
            this.stakings = result.getStakings();
            this.count = result.getCount();
        }

        public KrakenStaking[] getStakings() {
            return stakings;
        }

        public int getCount() {
            return count;
        }
    }
}