package org.knowm.xchange.bitget.service.params;

import java.util.Date;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOrderId;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsIdSpan;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
@SuperBuilder
public class BitgetTradeHistoryParams
    implements TradeHistoryParamInstrument,
        TradeHistoryParamLimit,
        TradeHistoryParamsTimeSpan,
        TradeHistoryParamsIdSpan,
        TradeHistoryParamOrderId {

  private Instrument instrument;

  private Integer limit;

  private String orderId;

  private Date startTime;

  private Date endTime;

  private String startId;

  private String endId;
}
