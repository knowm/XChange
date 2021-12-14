package nostro.xchange.binance.futures;

import info.bitrich.xchangestream.binance.futures.dto.AccountUpdateBinanceWebsocketTransaction;
import nostro.xchange.binance.NostroBinanceUtils;
import nostro.xchange.persistence.BalanceEntity;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.knowm.xchange.binance.futures.service.BinanceFuturesAccountService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.Derivative;
import org.knowm.xchange.dto.account.*;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.account.params.AccountLeverageParams;
import org.knowm.xchange.service.account.params.AccountMarginParams;
import org.knowm.xchange.service.account.params.AccountPositionMarginParams;
import org.knowm.xchange.utils.jackson.InstrumentDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class NostroBinanceFuturesAccountService implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(NostroBinanceFuturesAccountService.class);

    private final BinanceFuturesAccountService inner;
    private final TransactionFactory txFactory;

    public NostroBinanceFuturesAccountService(BinanceFuturesAccountService inner, TransactionFactory txFactory) {
        this.inner = inner;
        this.txFactory = txFactory;
    }

    @Override
    public AccountInfo getAccountInfo() {
        List<BalanceEntity> balanceEntities = txFactory.executeAndGet(tx -> tx.getBalanceRepository().findAllLatest());

        OptionalLong time = balanceEntities.stream().mapToLong(b -> b.getTimestamp().getTime()).max();
        Date date = time.isPresent() ? new Date(time.getAsLong()) : null;

        List<BalanceEntity> positionEntities = balanceEntities.stream()
                .filter(balanceEntity -> InstrumentDeserializer.fromString(balanceEntity.getAsset()) instanceof Derivative)
                .collect(Collectors.toList());
        balanceEntities.removeAll(positionEntities);

        List<Balance> balances = balanceEntities.stream()
                .map(e -> NostroUtils.readBalanceDocument(e.getDocument()))
                .collect(Collectors.toList());

        List<OpenPosition> positions = positionEntities.stream()
                .map(e -> NostroUtils.readPositionDocument(e.getDocument()))
                .collect(Collectors.toList());

        return new AccountInfo(null, null, Collections.singletonList(Wallet.Builder.from(balances).build()), positions, date);
    }

    @Override
    public Map<CurrencyPair, Fee> getDynamicTradingFees() {
        return inner.getDynamicTradingFees();
    }

    @Override
    public void setLeverage(AccountLeverageParams params) throws IOException {
        inner.setLeverage(params);
    }

    @Override
    public void setMarginType(AccountMarginParams params) throws IOException {
        inner.setMarginType(params);
    }

    @Override
    public void setIsolatedPositionMargin(AccountPositionMarginParams params) throws IOException {
        inner.setIsolatedPositionMargin(params);
    }

    Pair<List<Balance>, List<OpenPosition>> saveAccountInfo(AccountUpdateBinanceWebsocketTransaction accountInfo) {
        return txFactory.executeAndGet(tx -> {
            tx.getBalanceRepository().lock();
            List<Balance> updatedBalances = new ArrayList<>();
            List<OpenPosition> updatedPositions = new ArrayList<>();

            for (Balance balance : accountInfo.toBalanceList()) {
                LOG.debug("process balance={}", balance);
                Optional<BalanceEntity> o = tx.getBalanceRepository().findLatestByAsset(balance.getCurrency().getCurrencyCode());
                if (o.isPresent()) {
                    if (NostroBinanceUtils.updateRequired(o.get(), balance)) {
                        updatedBalances.add(balance);
                    }
                } else if (!NostroBinanceUtils.isZeroBalance(balance)) {
                    updatedBalances.add(balance);
                }
            }
            for (OpenPosition position : accountInfo.toPositionList()) {
                LOG.debug("process position={}", position);
                Optional<BalanceEntity> o = tx.getBalanceRepository().findLatestByAsset(position.getInstrument().toString());
                if (o.isPresent()) {
                    if (NostroBinanceUtils.updateRequired(o.get(), position)) {
                        updatedPositions.add(position);
                    }
                } else if (!NostroBinanceUtils.isZeroPosition(position)) {
                    updatedPositions.add(position);
                }
            }
            LOG.info("Updated account info, ts={}, balances={}, positions={}", new Timestamp(accountInfo.getTransactionTime()), updatedBalances, updatedPositions);

            for (Balance b : updatedBalances) {
                tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(b));
            }

            for (OpenPosition p : updatedPositions) {
                tx.getBalanceRepository().insert(NostroBinanceUtils.toEntity(p));
            }

            return Pair.of(updatedBalances, updatedPositions);
        });
    }
}
