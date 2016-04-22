package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jean-Christophe Laruelle
 */

@Deprecated
public class CryptoFacilitiesContractsJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoFacilitiesContractsJSONTest.class.getResourceAsStream("/marketdata/example-contracts-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesContracts cryptoFacilitiesContracts = mapper.readValue(is, CryptoFacilitiesContracts.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesContracts.isSuccess()).isTrue();

    List<CryptoFacilitiesContract> contracts = cryptoFacilitiesContracts.getContracts();
    assertThat(contracts.size()).isEqualTo(6);

    Iterator<CryptoFacilitiesContract> it = contracts.iterator();
    CryptoFacilitiesContract ct = it.next();

    assertThat(ct.getUnit()).isEqualTo("USD");
    assertThat(ct.getTradeable()).isEqualTo("F-XBT:USD-Dec15-W2");
    assertThat(ct.getContractSize()).isEqualTo(new BigDecimal("1"));
    assertThat(ct.getTickSize()).isEqualTo(new BigDecimal("0.01"));
    assertThat(ct.getSuspended()).isFalse();

    Calendar cal = new GregorianCalendar();
    //2015-12-11 16:00:00
    cal.set(Calendar.YEAR, 2015);
    cal.set(Calendar.MONTH, Calendar.DECEMBER);
    cal.set(Calendar.DAY_OF_MONTH, 11);
    cal.set(Calendar.HOUR_OF_DAY, 16);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    assertThat(ct.getLastTradingDayAndTime()).isEqualTo(cal.getTime());

  }

}
