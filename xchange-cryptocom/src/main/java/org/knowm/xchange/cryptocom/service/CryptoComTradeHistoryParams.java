package org.knowm.xchange.cryptocom.service;

import java.util.Date;
import lombok.Data;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamInstrument;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsTimeSpan;

@Data
public class CryptoComTradeHistoryParams
    implements TradeHistoryParamInstrument, TradeHistoryParamsTimeSpan, TradeHistoryParamLimit {

  private Instrument instrument;
  private Date startTime;
  private Date endTime;
  private Integer limit;
}
