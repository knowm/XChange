package org.knowm.xchange.bitbay.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.dto.acount.BitbayAccountInfoResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.FundingRecord;

/**
 * @author Z. Dolezal
 */
public class BitbayAccountServiceRaw extends BitbayBaseService {

  public BitbayAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitbayAccountInfoResponse getBitbayAccountInfo() throws IOException {
    BitbayAccountInfoResponse response = bitbayAuthenticated.info(apiKey, sign, exchange.getNonceFactory());

    checkError(response);
    return response;
  }

  public List<FundingRecord> history(Currency currency, int limit) {
    List<Map> history = bitbayAuthenticated.history(apiKey, sign, exchange.getNonceFactory(), currency.getCurrencyCode(), limit);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

    List<FundingRecord> res = new ArrayList<>();
    for (Map map : history) {
      try {
        FundingRecord.Type type;
        if (map.get("operation_type").toString().equals("+outside_income"))
          type = FundingRecord.Type.DEPOSIT;
        else if (map.get("operation_type").toString().equals("-transfer"))
          type = FundingRecord.Type.WITHDRAWAL;
        else
          continue;

        res.add(new FundingRecord(
            null,
            dateFormat.parse(map.get("time").toString()),
            Currency.getInstance(map.get("currency").toString()),
            new BigDecimal(map.get("amount").toString()),
            map.get("id").toString(),
            null,
            type,
            FundingRecord.Status.COMPLETE,
            null,
            null,
            map.get("comment").toString()
        ));
      } catch (ParseException e) {
        throw new IllegalStateException("Should not happen", e);
      }
    }

    return res;
  }
}
