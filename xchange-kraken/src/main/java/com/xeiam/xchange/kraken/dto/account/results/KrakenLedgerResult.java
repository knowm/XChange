/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.kraken.dto.account.results;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.kraken.dto.KrakenResult;
import com.xeiam.xchange.kraken.dto.account.KrakenLedger;
import com.xeiam.xchange.kraken.dto.account.results.KrakenLedgerResult.KrakenLedgers;

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
