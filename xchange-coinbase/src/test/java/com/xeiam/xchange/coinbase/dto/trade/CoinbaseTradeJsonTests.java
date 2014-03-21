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
package com.xeiam.xchange.coinbase.dto.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer.CoinbaseTransferStatus;
import com.xeiam.xchange.utils.DateUtils;

/**
 * @author jamespedwards42
 */
public class CoinbaseTradeJsonTests {

  @Test
  public void testDeserializeTransfers() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CoinbaseTradeJsonTests.class.getResourceAsStream("/trade/example-transfers-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CoinbaseTransfers transfers = mapper.readValue(is, CoinbaseTransfers.class);

    List<CoinbaseTransfer> transferList = transfers.getTransfers();
    assertThat(transferList.size()).isEqualTo(1);

    CoinbaseTransfer transfer = transferList.get(0);
    assertThat(transfer.getId()).isEqualTo("52f4411667c71baf9000003c");
    assertThat(transfer.getCreatedAt()).isEqualTo(DateUtils.fromISO8601DateString("2014-02-06T18:12:38-08:00"));
    assertThat(transfer.getCoinbaseFee()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("9.05")));
    assertThat(transfer.getBankFee()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal(".15")));
    assertThat(transfer.getPayoutDate()).isEqualTo(DateUtils.fromISO8601DateString("2014-02-06T18:12:37-08:00"));
    assertThat(transfer.getTransactionId()).isEqualTo("52f4411767c71baf9000003f");
    assertThat(transfer.getFundingType()).isEqualTo("AchDebit");
    assertThat(transfer.getCode()).isEqualTo("52f4411667c71baf9000003c");
    assertThat(transfer.getType()).isEqualTo(CoinbaseTransferType.BUY);
    assertThat(transfer.getStatus()).isEqualTo(CoinbaseTransferStatus.COMPLETED);
    assertThat(transfer.getBtcAmount()).isEqualsToByComparingFields(new CoinbaseMoney("BTC", new BigDecimal("1.20000000")));
    assertThat(transfer.getSubtotal()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("905.10")));
    assertThat(transfer.getTotal()).isEqualsToByComparingFields(new CoinbaseMoney("USD", new BigDecimal("914.30")));
    assertThat(transfer.getDescription()).isEqualTo("Bought 1.20 BTC for $914.30.  \n\nPaid for with Bank ****. Your bitcoin will arrive by the end of day on Thursday Feb  6, 2014.");

  }
}
