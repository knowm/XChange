package org.knowm.xchange.examples.blockchain.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.examples.blockchain.BlockchainDemoUtils;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.HistoryParamsFundingType;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.knowm.xchange.examples.blockchain.BlockchainDemoUtils.END_TIME;

/**
 * @author scuevas
 */
public class BlockchainAccountDemo {

    private static final Exchange BLOCKCHAIN_EXCHANGE = BlockchainDemoUtils.createExchange();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void main(String[] args) throws IOException {
        System.out.println("===== ACCOUNT SERVICE =====");
        accountServiceDemo();
    }

    private static void accountServiceDemo() throws IOException {
        AccountService accountService = BLOCKCHAIN_EXCHANGE.getAccountService();

        System.out.println("===== getAccountInfo =====");
        AccountInfo accountInfo = accountService.getAccountInfo();
        System.out.println(OBJECT_MAPPER.writeValueAsString(accountInfo));

        /*System.out.println("===== withdrawFunds =====");
        String withdraw = accountService.withdrawFunds(Currency.BTC, BigDecimal.valueOf(0.00005), "ea1f34b3-e77a-4646-9cfa-5d6d3518c6d3");
        System.out.println(OBJECT_MAPPER.writeValueAsString(withdraw));*/

        System.out.println("===== requestDepositAddress =====");
        String address = accountService.requestDepositAddress(Currency.ETH);
        System.out.println(OBJECT_MAPPER.writeValueAsString(address));

        System.out.println("===== requestDepositAddressData =====");
        AddressWithTag addressWithTag = accountService.requestDepositAddressData(Currency.ETH);
        System.out.println(OBJECT_MAPPER.writeValueAsString(addressWithTag));

        System.out.println("===== getFundingHistory =====");
        TradeHistoryParams params = accountService.createFundingHistoryParams();
        final TradeHistoryParamsTimeSpan timeSpanParam = (TradeHistoryParamsTimeSpan) params;
        timeSpanParam.setStartTime(new Date(System.currentTimeMillis() - END_TIME));
        ((HistoryParamsFundingType) params).setType(FundingRecord.Type.DEPOSIT);
        List<FundingRecord> fundingDepositsRecords = accountService.getFundingHistory(params);

        ((HistoryParamsFundingType) params).setType(FundingRecord.Type.WITHDRAWAL);
        List<FundingRecord> fundingWithdrawalRecords = accountService.getFundingHistory(params);

        System.out.println(OBJECT_MAPPER.writeValueAsString(fundingDepositsRecords));
        System.out.println(OBJECT_MAPPER.writeValueAsString(fundingWithdrawalRecords));

        System.out.println("===== getDynamicTradingFees =====");
        Map<CurrencyPair, Fee> tradingFees = accountService.getDynamicTradingFees();
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradingFees));

        System.out.println("===== getDynamicTradingFeesByInstrument =====");
        Map<Instrument, Fee> tradingFeesByInstrument = accountService.getDynamicTradingFeesByInstrument();
        System.out.println(OBJECT_MAPPER.writeValueAsString(tradingFeesByInstrument));

    }
}
