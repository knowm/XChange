package org.knowm.xchange.exmo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.utils.DateUtils;

public class ExmoAccountServiceRaw extends BaseExmoService {
  protected ExmoAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Map<String, String> depositAddresses() {
    return exmo.depositAddress(signatureCreator, apiKey, exchange.getNonceFactory());
  }

  public List<Balance> balances() {
    Map map = exmo.userInfo(signatureCreator, apiKey, exchange.getNonceFactory());

    Map<String, String> balances = (Map<String, String>) map.get("balances");
    Map<String, String> reserved = (Map<String, String>) map.get("reserved");

    List<Balance> results = new ArrayList<>();
    for (String ccy : balances.keySet()) {
      results.add(ExmoAdapters.adaptBalance(balances, reserved, ccy));
    }

    return results;
  }

  public List<FundingRecord> getFundingHistory(Date since) throws IOException {
    Map<String, Object> response =
        exmo.walletHistory(
            signatureCreator, apiKey, exchange.getNonceFactory(), since.getTime() / 1000);

    List<FundingRecord> results = new ArrayList<>();

    for (Map<String, Object> item : (List<Map<String, Object>>) response.get("history")) {
      long time = Long.valueOf(item.get("dt").toString());
      String type = item.get("type").toString();
      String curr = item.get("curr").toString();
      String status = item.get("status").toString();
      String amount = item.get("amount").toString();
      String account = item.get("account").toString();
      String provider = item.get("provider").toString();
      String description = (account + " " + provider).trim();

      String address = null;
      if (account.startsWith(curr + ":")) {
        address = account.replace(curr + ":", "").trim();
      }

      FundingRecord.Status statusEnum = FundingRecord.Status.FAILED;
      if (status.equalsIgnoreCase("processing")) statusEnum = FundingRecord.Status.PROCESSING;
      else if (status.equalsIgnoreCase("paid")) statusEnum = FundingRecord.Status.COMPLETE;
      else if (status.equalsIgnoreCase("transferred")) statusEnum = FundingRecord.Status.COMPLETE;
      else if (status.equalsIgnoreCase("cancelled")) statusEnum = FundingRecord.Status.CANCELLED;

      FundingRecord fundingRecord =
          new FundingRecord(
              address,
              DateUtils.fromUnixTime(Long.valueOf(time)),
              Currency.getInstance(curr),
              new BigDecimal(amount).abs(),
              null,
              null,
              type.equalsIgnoreCase("deposit")
                  ? FundingRecord.Type.DEPOSIT
                  : FundingRecord.Type.WITHDRAWAL,
              statusEnum,
              null,
              null,
              description);

      results.add(fundingRecord);
    }

    return results;
  }
}
