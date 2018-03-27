package org.knowm.xchange.poloniex.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.poloniex.dto.account.PoloniexLoan;

public class PoloniexLoansDataTest {

  @Test
  public void testUnmarshallLoans() throws JsonParseException, JsonMappingException, IOException {

    final InputStream is =
        PoloniexLoansDataTest.class.getResourceAsStream(
            "/org/knowm/xchange/poloniex/dto/marketdata/loans-info.json");

    final ObjectMapper mapper = new ObjectMapper();

    final JavaType stringType = mapper.getTypeFactory().constructType(String.class, String.class);
    final JavaType loanArray = mapper.getTypeFactory().constructArrayType(PoloniexLoan.class);
    final JavaType currencyInfoType =
        mapper.getTypeFactory().constructMapType(HashMap.class, stringType, loanArray);
    final Map<String, PoloniexLoan[]> loansInfo = mapper.readValue(is, currencyInfoType);

    assertThat(loansInfo).hasSize(2);

    PoloniexLoan[] providedLoans = loansInfo.get("provided");
    assertThat(providedLoans).hasSize(2);
    assertThat(providedLoans[0].getId()).isEqualTo("75073");
    assertThat(providedLoans[0].getCurrency()).isEqualTo(Currency.LTC.getCurrencyCode());
    assertThat(providedLoans[0].getRate()).isEqualTo("0.00020000");
    assertThat(providedLoans[0].getAmount()).isEqualTo("0.72234880");
    assertThat(providedLoans[0].getRange()).isEqualTo(2);
    assertThat(providedLoans[0].getDate()).isEqualTo("2015-05-10 23:45:05");
    assertThat(providedLoans[0].getFees()).isEqualTo("0.00006000");
    assertThat(providedLoans[0].isAutoRenew()).isEqualTo(false);

    PoloniexLoan[] usedLoans = loansInfo.get("used");
    assertThat(usedLoans).hasSize(1);
    assertThat(usedLoans[0].getId()).isEqualTo("75238");
    assertThat(usedLoans[0].getCurrency()).isEqualTo(Currency.BTC.getCurrencyCode());
    assertThat(usedLoans[0].getRate()).isEqualTo("0.00020000");
    assertThat(usedLoans[0].getAmount()).isEqualTo("0.04843834");
    assertThat(usedLoans[0].getRange()).isEqualTo(2);
    assertThat(usedLoans[0].getDate()).isEqualTo("2015-05-10 23:51:12");
    assertThat(usedLoans[0].getFees()).isEqualTo("-0.00000001");
  }
}
