package nostro.xchange.ftx;

import nostro.xchange.persistence.FundingEntity;
import nostro.xchange.persistence.FundingRepository;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.ftx.FtxTimestampFactory;
import org.knowm.xchange.ftx.dto.account.FtxFundingPaymentsDto;
import org.knowm.xchange.ftx.service.FtxAccountService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.account.params.AccountLeverageParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NostroFtxAccountService implements AccountService {
    
    private static final String SYNC_FUNDING_PAYMENTS_SYMBOL = "SYNC_FUNDING_PAYMENTS";
    
    private static final long FUNDING_PAYMENTS_PERIOD = 60 * 60 * 1000;
    private static final long SYNC_FUNDING_PAYMENTS_THRESHOLD = FUNDING_PAYMENTS_PERIOD - 60 * 1000;

    private final FtxAccountService inner;
    private final TransactionFactory txFactory;
    private final FtxExchange exchange;
    private final FtxTimestampFactory timestampFactory;

    public NostroFtxAccountService(FtxAccountService inner, TransactionFactory txFactory, FtxExchange exchange) {
        this.inner = inner;
        this.txFactory = txFactory;
        this.exchange = exchange;
        this.timestampFactory = (FtxTimestampFactory) exchange.getNonceFactory();
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return inner.getAccountInfo();
    }

    @Override
    public void setLeverage(AccountLeverageParams params) throws IOException {
        inner.setLeverage(params);
    }

    @Override
    public List<FundingRecord> getFundingHistory(TradeHistoryParams params) throws IOException {
        syncFundingPayments();

        Date startTime;
        Date endTime;
        if (params instanceof TradeHistoryParamsTimeSpan) {
            startTime = ((TradeHistoryParamsTimeSpan) params).getStartTime();
            endTime = ((TradeHistoryParamsTimeSpan) params).getEndTime();
        } else {
            throw new ExchangeException("params must implement TradeHistoryParamsTimeSpan and supply startTime and endTime");
        }
        
        Timestamp from = new Timestamp(startTime.getTime());
        Timestamp to = endTime != null ? new Timestamp(endTime.getTime()) : null;

        return txFactory.executeAndGet(tx -> tx.getFundingRepository()
                .findAllByTimestamp(from, to))
                .stream()
                .map(e -> NostroUtils.readFundingDocument(e.getDocument()))
                .collect(Collectors.toList());
    }

    private void syncFundingPayments() throws IOException {
        long now = timestampFactory.createValue();
        long from = txFactory.executeAndGet(tx -> tx.getSyncTaskRepository().findLatestBySymbol(SYNC_FUNDING_PAYMENTS_SYMBOL)
                .map(e -> e.getTimestamp().getTime())
                .orElse(now - 4 * FUNDING_PAYMENTS_PERIOD));

        if (now - from < SYNC_FUNDING_PAYMENTS_THRESHOLD) {
            return;
        }
        long to = now + FUNDING_PAYMENTS_PERIOD;
        List<FtxFundingPaymentsDto> payments = inner.getFtxFundingPayments(exchange.getExchangeSpecification().getUserName(), from / 1000, to / 1000, null).getResult();
        if (payments.isEmpty()) {
            return;
        }

        txFactory.execute(tx -> {
            FundingRepository fundingRepository = tx.getFundingRepository();
            Timestamp last = new Timestamp(0);
            for (FtxFundingPaymentsDto payment : payments) {
                FundingRecord record = NostroFtxUtils.adapt(payment);
                Timestamp timestamp = new Timestamp(record.getDate().getTime());
                fundingRepository.insert(new FundingEntity.Builder()
                        .externalId(record.getInternalId())
                        .type(record.getType().name())
                        .timestamp(timestamp)
                        .document(NostroUtils.writeFundingDocument(record))
                        .build());
                if (last.before(timestamp)) {
                    last = timestamp;
                }
            }
            tx.getSyncTaskRepository().insert(SYNC_FUNDING_PAYMENTS_SYMBOL, last, "{\"updated\":" + payments.size() + "}");
        });
    }
}
