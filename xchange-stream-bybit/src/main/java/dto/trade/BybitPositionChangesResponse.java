package dto.trade;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;


@Getter
public class BybitPositionChangesResponse {

  private String id;
  private String topic;
  private long creationTime;
  private List<BybitPositionChanges> data = new ArrayList<>();

  @Getter
  public static class BybitPositionChanges {

    private BybitCategory category;
    private String symbol;
    private String side;
    private String size;
    private int positionIdx;
    private int tradeMode;
    private String positionValue;
    private int riskId;
    private String riskLimitValue;
    private String entryPrice;
    private String markPrice;
    private String leverage;
    private String positionBalance;
    private int autoAddMargin;
    private String positionMM;
    private String positionIM;
    private String liqPrice;
    private String bustPrice;
    private String tpslMode;
    private String takeProfit;
    private String stopLoss;
    private String trailingStop;
    private String unrealisedPnl;
    private String curRealisedPnl;
    private String sessionAvgPrice;
    private String delta;
    private String gamma;
    private String vega;
    private String theta;
    private String cumRealisedPnl;
    private String positionStatus;
    private int adlRankIndicator;
    private boolean isReduceOnly;

    private String mmrSysUpdatedTime;

    private String leverageSysUpdatedTime;

    private String createdTime;
    private String updatedTime;
    private long seq;

  }
}


