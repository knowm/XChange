package nostro.xchange.binance;

import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.binance.service.BinanceAccountService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class BinanceNostroAccountService implements AccountService {

    private final BinanceAccountService inner;
    private final TransactionFactory txFactory;
    
    public BinanceNostroAccountService(BinanceAccountService inner, TransactionFactory txFactory) {
        this.inner = inner;
        this.txFactory = txFactory;
    }

    @Override
    public AccountInfo getAccountInfo() {
        List<Balance> balances = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest()).stream()
                .map(e -> NostroUtils.readBalanceDocument(e.getDocument()))
                .collect(Collectors.toList());
        
        OptionalLong time = balances.stream().mapToLong(b -> b.getTimestamp().getTime()).max();
        Date date = time.isPresent() ? new Date(time.getAsLong()) : null;
        
        return new AccountInfo(date, Wallet.Builder.from(balances).build());
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, String address) throws IOException {
        return inner.withdrawFunds(currency, amount, address);
    }

    @Override
    public String withdrawFunds(Currency currency, BigDecimal amount, AddressWithTag address) throws IOException {
        return inner.withdrawFunds(currency, amount, address);
    }

    @Override
    public String withdrawFunds(WithdrawFundsParams params) throws IOException {
        return inner.withdrawFunds(params);
    }

    @Override
    public String requestDepositAddress(Currency currency, String... args) throws IOException {
        return inner.requestDepositAddress(currency, args);
    }

    @Override
    public AddressWithTag requestDepositAddressData(Currency currency, String... args) throws IOException {
        return inner.requestDepositAddressData(currency, args);
    }

    @Override
    public TradeHistoryParams createFundingHistoryParams() {
        return inner.createFundingHistoryParams();
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        return inner.getFundingHistory(params);
    }

    @Override
    public Map<CurrencyPair, Fee> getDynamicTradingFees() throws IOException {
        return inner.getDynamicTradingFees();
    }
}
