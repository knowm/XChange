package org.knowm.xchange.cexio.dto.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.Currency.GBP;
import static org.knowm.xchange.currency.Currency.ZEC;
import static org.knowm.xchange.currency.CurrencyPair.BCH_GBP;
import static org.knowm.xchange.currency.CurrencyPair.ETH_USD;
import static org.knowm.xchange.currency.CurrencyPair.ZEC_BTC;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ujjwal on 14/02/18.
 */
public class CexIOFeeInfoTest {
  @Test
  public void jsonMapperTest() throws IOException {
    InputStream is = getClass().getResourceAsStream("/account/sample_get_myfee.json");
    ObjectMapper mapper = new ObjectMapper();
    final CexIOFeeInfo cexIOFeeInfo = mapper.readValue(is, CexIOFeeInfo.class);
    assertThat(cexIOFeeInfo).isNotNull();
    assertThat(cexIOFeeInfo.getE()).isEqualToIgnoringCase("get_myfee");
    assertThat(cexIOFeeInfo.getOk()).isEqualToIgnoringCase("ok");
    assertThat(cexIOFeeInfo.getData()).isNotEmpty()
        .containsKeys(ETH_USD, BCH_GBP, ZEC_BTC, new CurrencyPair(ZEC, GBP));
    final CexIOFeeInfo.FeeDetails feeDetails = cexIOFeeInfo.getData().get(ETH_USD);
    assertThat(feeDetails).isNotNull();
    assertThat(feeDetails.getBuy()).isNotNull();
    assertThat(feeDetails.getBuyMaker()).isNotNull();
    assertThat(feeDetails.getSell()).isNotNegative();
    assertThat(feeDetails.getSellMaker()).isNotNegative();
  }

}
