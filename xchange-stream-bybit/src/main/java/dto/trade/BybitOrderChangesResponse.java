package dto.trade;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitOrderStatus;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;

@Getter
public class BybitOrderChangesResponse {

  String id;
  String topic;
  long creationTime;
  List<BybitOrderChanges> data;

  @Getter
  public static class BybitOrderChanges {

    private BybitCategory category;
    private String orderId;
    private String orderLinkId;
    private String isLeverage;
    private String blockTradeId;
    private String symbol;
    private String price;
    private String qty;
    private BybitSide side;
    private int positionIdx;
    private BybitOrderStatus orderStatus;
    private String createType;
    private String cancelType;
    private String rejectReason;
    private String avgPrice;
    private String leavesQty;
    private String leavesValue;
    private String cumExecQty;
    private String cumExecValue;
    private String cumExecFee;
    private String feeCurrency;
    private String timeInForce;
    private BybitOrderType orderType;
    private String stopOrderType;
    private String ocoTriggerBy;
    private String orderIv;
    private String marketUnit = "";
    private String triggerPrice;
    private String takeProfit;
    private String stopLoss;
    private String tpslMode;
    private String tpLimitPrice;
    private String slLimitPrice;
    private String tpTriggerBy;
    private String slTriggerBy;
    private int triggerDirection;
    private String triggerBy;
    private String lastPriceOnCreated = "";
    private boolean reduceOnly;
    private boolean closeOnTrigger;
    private String placeType;
    private String smpType;
    private int smpGroup;
    private String smpOrderId = "";
    private String createdTime;
    private String updatedTime;
  }
}
