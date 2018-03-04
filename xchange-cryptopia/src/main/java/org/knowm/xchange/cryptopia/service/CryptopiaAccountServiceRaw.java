package org.knowm.xchange.cryptopia.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.knowm.xchange.cryptopia.Cryptopia;
import org.knowm.xchange.cryptopia.CryptopiaAdapters;
import org.knowm.xchange.cryptopia.CryptopiaDigest;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;

public class CryptopiaAccountServiceRaw extends CryptopiaBaseService {

    public CryptopiaAccountServiceRaw(CryptopiaExchange exchange) {

        super(exchange);

    }

    public List<Balance> getBalances() throws IOException {

//      @POST
//  @Path("GetBalance")
//  @Consumes(MediaType.APPLICATION_JSON)
//  CryptopiaBaseResponse<List<Map>> getBalance(@HeaderParam("Authorization") ParamsDigest signature, Object o) throws IOException;
        CryptopiaDigest.HttpCryptopia httpCryptopia = null;
        try {
            httpCryptopia = new CryptopiaDigest.HttpCryptopia("GetBalance", "{}", exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getSecretKey());
            String response = httpCryptopia.response;
            Map map = new Gson().fromJson(response, Map.class);

            List<Balance> balances = new ArrayList<>();
            List<? extends Map> data = (List<? extends Map>) map.get("Data");

            return data.parallelStream().filter(o -> Double.parseDouble(o.get("Total").toString()) != 0.0D).map(
                    datum -> {
                        BigDecimal total = new BigDecimal(datum.get("Total").toString());
                        Currency symbol = new Currency(datum.get("Symbol").toString());
                        BigDecimal available = new BigDecimal(datum.get("Available").toString());
                        BigDecimal heldForTrades = new BigDecimal(datum.get("HeldForTrades").toString());
                        BigDecimal pendingWithdraw = new BigDecimal(datum.get("PendingWithdraw").toString());
                        BigDecimal unconfirmed = new BigDecimal(datum.get("Unconfirmed").toString());
                        return new Balance(symbol, total, available, heldForTrades, BigDecimal.ZERO, BigDecimal.ZERO, pendingWithdraw, unconfirmed);
                    }).collect(Collectors.toList());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

   /*     CryptopiaBaseResponse<List<Map>> response = new CryptopiaBaseResponse<>(false, httpCryptopia.response, new ArrayList<>(), httpCryptopia.response);
        if (!response.isSuccess())
            throw new ExchangeException("Failed to get balance: " + response);
*/
        return null;
    }

    public String submitWithdraw(Currency currency, BigDecimal amount, String address, String paymentId) throws IOException {
        CryptopiaBaseResponse<Long> response = cryptopia.submitWithdraw(signatureCreator, new Cryptopia.SubmitWithdrawRequest(currency.getCurrencyCode(), address, paymentId, amount));
        if (!response.isSuccess())
            throw new ExchangeException("Failed to withdraw funds: " + response.toString());

        return String.valueOf(response.getData());
    }

    public String getDepositAddress(Currency currency) throws IOException {
        CryptopiaBaseResponse<Map> response = cryptopia.getDepositAddress(signatureCreator, new Cryptopia.GetDepositAddressRequest(currency.getCurrencyCode()));
        if (!response.isSuccess())
            throw new ExchangeException("Failed to get address: " + response.toString());

        return response.getData().get("Address").toString();
    }

    public List<FundingRecord> getTransactions(String type, Integer count) throws IOException {
        CryptopiaBaseResponse<List<Map>> response = cryptopia.getTransactions(signatureCreator, new Cryptopia.GetTransactionsRequest(type, count));
        if (!response.isSuccess())
            throw new ExchangeException("Failed to get transactions: " + response.toString());

        List<FundingRecord> results = new ArrayList<>();
        for (Map map : response.getData()) {
            Date timeStamp = CryptopiaAdapters.convertTimestamp(map.get("Timestamp").toString());
            Currency currency = Currency.getInstance(map.get("Currency").toString());
            FundingRecord.Type fundingType = map.get("Type").toString().equals(CryptopiaAccountService.CryptopiaFundingHistoryParams.Type.Deposit.name())
                    ? FundingRecord.Type.DEPOSIT : FundingRecord.Type.WITHDRAWAL;

            FundingRecord.Status status;
            String rawStatus = map.get("Status").toString();
            switch (rawStatus) {
                case "UnConfirmed":
                case "Pending":
                    status = FundingRecord.Status.PROCESSING;
                    break;
                case "Confirmed":
                case "Complete":
                    status = FundingRecord.Status.COMPLETE;
                    break;
                default:
                    status = FundingRecord.Status.resolveStatus(rawStatus);
                    if (status == null) {
                        status = FundingRecord.Status.FAILED;
                    }
                    break;
            }

            String address = map.get("Address") == null ? null : map.get("Address").toString();

            FundingRecord fundingRecord = new FundingRecord(
                    address,
                    timeStamp,
                    currency,
                    new BigDecimal(map.get("Amount").toString()),
                    map.get("Id").toString(),
                    map.get("TxId").toString(),
                    fundingType,
                    status,
                    null,
                    new BigDecimal(map.get("Fee").toString()),
                    null
            );

            results.add(fundingRecord);
        }

        return results;
    }
}
