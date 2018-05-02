package org.knowm.xchange.exmo.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * NOT TESTED!!  Author doesn't have access to this API call so this has been implemented by following the spec and as such may not work
     */
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        Map<String, Object> response = exmo.walletHistory(signatureCreator, apiKey, exchange.getNonceFactory(), new Date(0).getTime());

        List<FundingRecord> results = new ArrayList<>();

        for (Map<String, Object> item : (List<Map<String, Object>>) response.get("history")) {
            long time = Long.valueOf(item.get("dt").toString());
            String type = item.get("type").toString();
            String curr = item.get("curr").toString();
            String status = item.get("status").toString();
            String amount = item.get("amount").toString();
            String account = item.get("account").toString();

            FundingRecord fundingRecord = new FundingRecord(
                    null,
                    new Date(time),
                    Currency.getInstance(curr),
                    new BigDecimal(amount),
                    null,
                    null,
                    type.equalsIgnoreCase("deposit") ? FundingRecord.Type.DEPOSIT : FundingRecord.Type.WITHDRAWAL,
                    status.equalsIgnoreCase("processing") ? FundingRecord.Status.PROCESSING : FundingRecord.Status.COMPLETE,
                    null,
                    null,
                    account
            );

            results.add(fundingRecord);
        }

        return results;
    }

}
