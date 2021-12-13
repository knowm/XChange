package nostro.xchange.ftx;

import nostro.xchange.persistence.DataSourceTest;
import nostro.xchange.persistence.FundingEntity;
import nostro.xchange.persistence.FundingRepository;
import nostro.xchange.persistence.TransactionFactory;
import nostro.xchange.utils.NostroUtils;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.ftx.FtxExchange;
import org.knowm.xchange.ftx.FtxTimestampFactory;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.FtxFundingPaymentsDto;
import org.knowm.xchange.ftx.service.FtxAccountService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamsTimeSpan;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NostroFtxAccountServiceTest extends DataSourceTest {

    private FtxAccountService inner;
    private TransactionFactory txFactory;
    private FtxExchange exchange;
    private NostroFtxAccountService service;
    private FtxTimestampFactory timestampFactory;

    @Before
    public void setUp() throws Exception {
        txFactory = TransactionFactory.get("Ftx", "0001");
        inner = mock(FtxAccountService.class);
        exchange = mock(FtxExchange.class);
        timestampFactory = mock(FtxTimestampFactory.class);
        when(exchange.getNonceFactory()).thenReturn(timestampFactory);
        
        service = new NostroFtxAccountService(inner, txFactory, exchange);

        try(Connection connection = TransactionFactory.getDataSource().getConnection();
            PreparedStatement stmt0 = connection.prepareStatement("DELETE FROM funding$1");
            PreparedStatement stmt1 = connection.prepareStatement("DELETE FROM sync_task$1 WHERE symbol = 'SYNC_FUNDING_PAYMENTS'")) {
            stmt0.execute();
            stmt1.execute();
        }
    }

    @Test
    public void getDbFundingPayments() throws IOException {
        txFactory.execute(tx -> {
            FundingRepository fundingRepo = tx.getFundingRepository();
            fundingRepo.insert(newFundingEntity(1000, new BigDecimal("0.1")));
            fundingRepo.insert(newFundingEntity(2000, new BigDecimal("-0.2")));
            fundingRepo.insert(newFundingEntity(3000, new BigDecimal("0.3")));
            
            tx.getSyncTaskRepository().insert("SYNC_FUNDING_PAYMENTS", new Timestamp(3000),"{}");
        });
        when(timestampFactory.createValue()).thenReturn((long) (3000 + 58 * 60 * 1000));
        
        List<FundingRecord> records = service.getFundingHistory(new DefaultTradeHistoryParamsTimeSpan(new Date(1000), new Date(3000)));
        assertThat(records.size()).isEqualTo(3);

        FundingRecord r = records.get(0);
        assertThat(r.getType() == FundingRecord.Type.REALISED_PROFIT);
        assertThat(r.getAmount().compareTo(new BigDecimal("0.1"))).isEqualTo(0);
        assertThat(r.getDate().getTime()).isEqualTo(1000);

        r = records.get(1);
        assertThat(r.getType() == FundingRecord.Type.REALISED_LOSS);
        assertThat(r.getAmount().compareTo(new BigDecimal("0.2"))).isEqualTo(0);
        assertThat(r.getDate().getTime()).isEqualTo(2000);

        r = records.get(2);
        assertThat(r.getType() == FundingRecord.Type.REALISED_PROFIT);
        assertThat(r.getAmount().compareTo(new BigDecimal("0.3"))).isEqualTo(0);
        assertThat(r.getDate().getTime()).isEqualTo(3000);

        records = service.getFundingHistory(new DefaultTradeHistoryParamsTimeSpan(new Date(1000), new Date(2000)));
        assertThat(records.size()).isEqualTo(2);

        records = service.getFundingHistory(new DefaultTradeHistoryParamsTimeSpan(new Date(3000), null));
        assertThat(records.size()).isEqualTo(1);
    }
    
    private static FundingEntity newFundingEntity(long time, BigDecimal amount) {
        FundingRecord record = new FundingRecord.Builder()
                .setDate(new Date(time))
                .setType(amount.signum() < 0 ? FundingRecord.Type.REALISED_PROFIT : FundingRecord.Type.REALISED_LOSS)
                .setAmount(amount)
                .setStatus(FundingRecord.Status.COMPLETE)
                .build();
        
        return new FundingEntity.Builder()
                .type(record.getType().name())
                .externalId(NostroUtils.randomUUID())
                .timestamp(new Timestamp(time))
                .document(NostroUtils.writeFundingDocument(record))
                .build();
    }

    @Test
    public void syncFundingPayments() throws IOException {
        when(exchange.getExchangeSpecification()).thenReturn(new FtxExchange().getDefaultExchangeSpecification());
        
        txFactory.execute(tx -> tx.getSyncTaskRepository().insert("SYNC_FUNDING_PAYMENTS", new Timestamp(1000),"{}"));

        when(timestampFactory.createValue()).thenReturn((long) (1000 + 59 * 60 * 1000));

        List<FtxFundingPaymentsDto> dtos = Arrays.asList(
                new FtxFundingPaymentsDto("ETH-PERP", "id0", new BigDecimal("0.1"), new Date(2000), new BigDecimal("0.01")),
                new FtxFundingPaymentsDto("BTC-PERP", "id0", new BigDecimal("-0.1"), new Date(2000), new BigDecimal("-0.01"))
        );
        when(inner.getFtxFundingPayments(any(), any(), any(), any())).thenReturn(new FtxResponse<>(true, dtos, false));
        List<FundingRecord> records = service.getFundingHistory(new DefaultTradeHistoryParamsTimeSpan(new Date(1000), null));

        
        assertThat(records.size()).isEqualTo(2);

        FundingRecord r = records.get(0);
        assertThat(r.getType() == FundingRecord.Type.REALISED_PROFIT);
        assertThat(r.getAmount().compareTo(new BigDecimal("0.1"))).isEqualTo(0);
        assertThat(r.getDate().getTime()).isEqualTo(2000);

        r = records.get(1);
        assertThat(r.getType() == FundingRecord.Type.REALISED_LOSS);
        assertThat(r.getAmount().compareTo(new BigDecimal("0.1"))).isEqualTo(0);
        assertThat(r.getDate().getTime()).isEqualTo(2000);
    }

}