package nostro.xchange.ftx;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.ftx.dto.account.FtxFundingPaymentsDto;

public class NostroFtxUtils {
    public static FundingRecord adapt(FtxFundingPaymentsDto dto) {
        FuturesContract futuresContract = (FuturesContract) FtxAdapters.adaptFtxMarketToInstrument(dto.getFuture());
        Currency currency = futuresContract.getCurrencyPair().counter;
        FundingRecord.Type type = dto.getPayment().signum() < 0 ? FundingRecord.Type.REALISED_PROFIT : FundingRecord.Type.REALISED_LOSS;
        return new FundingRecord.Builder()
                .setInternalId(dto.getId())
                .setDate(dto.getTime())
                .setType(type)
                .setStatus(FundingRecord.Status.COMPLETE)
                .setCurrency(currency)
                .setAmount(dto.getPayment())
                .setInstrument(futuresContract)
                .setDescription("rate=" + dto.getRate())
                .build();
    }
}
