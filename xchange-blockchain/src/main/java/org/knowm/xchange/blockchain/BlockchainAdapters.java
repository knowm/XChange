package org.knowm.xchange.blockchain;

import lombok.experimental.UtilityClass;
import org.knowm.xchange.blockchain.dto.account.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AddressWithTag;
import org.knowm.xchange.dto.account.FundingRecord;

import java.math.BigDecimal;

import static org.knowm.xchange.blockchain.BlockchainConstants.*;

@UtilityClass
public class BlockchainAdapters {

    public static String toSymbol(CurrencyPair currencyPair) {
        return String.format(CURRENCY_PAIR_SYMBOL_FORMAT, currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    }

    public static AddressWithTag toAddressWithTag(BlockchainDeposit blockchainDeposit){
        return new AddressWithTag(blockchainDeposit.getAddress(), null);
    }

    public static FundingRecord.Status toWithdrawStatus(String status) {
        switch (status.toUpperCase()) {
            case REJECTED:
            case REFUNDING:
                return FundingRecord.Status.CANCELLED;
            case PENDING:
                return FundingRecord.Status.PROCESSING;
            case FAILED:
                return FundingRecord.Status.FAILED;
            case COMPLETED:
                return FundingRecord.Status.COMPLETE;
            default:
                throw new RuntimeException(STATUS_INVALID + status);
        }
    }
    public static FundingRecord.Status toDepositStatus(String status) {
        switch (status.toUpperCase()) {
            case REJECTED:
            case UNCONFIRMED:
                return FundingRecord.Status.CANCELLED;
            case COMPLETED:
                return FundingRecord.Status.COMPLETE;
            default:
                throw new RuntimeException(STATUS_INVALID + status);
        }
    }

    public static FundingRecord toFundingWithdrawal(BlockchainWithdrawal w){
        return new FundingRecord(
                w.getBeneficiary(),
                null,
                w.getTimestamp(),
                w.getCurrency(),
                w.getAmount(),
                w.getWithdrawalId(),
                null,
                FundingRecord.Type.WITHDRAWAL,
                BlockchainAdapters.toWithdrawStatus(w.getState()),
                null,
                w.getFee(),
                null);
    }

    public  static  FundingRecord toFundingDeposit(BlockchainDeposits d){
        return new FundingRecord(
                d.getAddress(),
                null,
                d.getTimestamp(),
                d.getCurrency(),
                d.getAmount(),
                d.getDepositId(),
                d.getTxHash(),
                FundingRecord.Type.DEPOSIT,
                BlockchainAdapters.toDepositStatus(d.getState()),
                null,
                null,
                null);
    }

    public static BlockchainWithdrawalRequest toWithdrawalRequest(Currency currency, BigDecimal amount, String address){
        return BlockchainWithdrawalRequest.builder()
                .currency(currency)
                .amount(amount)
                .address(address)
                .build();
    }

   public static CurrencyPair toCurrencyPairBySymbol(BlockchainSymbols blockchainSymbol) {
        Currency baseSymbol = blockchainSymbol.getBaseCurrency();
        Currency counterSymbol = blockchainSymbol.getCounterCurrency();
        return new CurrencyPair(baseSymbol, counterSymbol);
    }
}
