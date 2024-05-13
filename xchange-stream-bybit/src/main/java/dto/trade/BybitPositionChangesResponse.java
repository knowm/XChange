package dto.trade;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitSide;


@Getter
public class BybitPositionChangesResponse {

  String id;
  String topic;
  long creationTime;
  List<BybitPositionChanges> data = new ArrayList<>();

  @Getter
  public static class BybitPositionChanges {

    BybitCategory category;
    String symbol;
    String side;
    String size;
    int positionIdx;
    String tradeMode;
    String positionValue;
    int riskId;
    String riskLimitValue;
    String entryPrice;
    String markPrice;
    String leverage;
    String positionBalance;
    int autoAddMargin;
    String positionMM;
    String positionIM;
    String liqPrice;
    String bustPrice;
    String tpslMode;
    String takeProfit;
    String stopLoss;
    String trailingStop;
    String unrealisedPnl;
    String curRealisedPnl;
    String sessionAvgPrice;
    String delta;
    String gamma;
    String vega;
    String theta;
    String cumRealisedPnl;
    String positionStatus;
    int adlRankIndicator;
    boolean isReduceOnly;

    String mmrSysUpdatedTime;

    String leverageSysUpdatedTime;

    String createdTime;
    String updatedTime;
    long seq;

  }
}


