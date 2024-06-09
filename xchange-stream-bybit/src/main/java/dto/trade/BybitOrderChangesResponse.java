package dto.trade;

import java.util.ArrayList;
import java.util.List;
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
  List<BybitOrderChanges> data = new ArrayList<>();

  @Getter
  public static class BybitOrderChanges {

    BybitCategory category;
    String orderId;
    String orderLinkId;
    String isLeverage;
    String blockTradeId;
    String symbol;
    String price;
    String qty;
    BybitSide side;
    int positionIdx;
    BybitOrderStatus orderStatus;
    String createType;
    String cancelType;
    String rejectReason;
    String avgPrice;
    String leavesQty;
    String leavesValue;
    String cumExecQty;
    String cumExecValue;
    String cumExecFee;
    String feeCurrency;
    String timeInForce;
    BybitOrderType orderType;
    String stopOrderType;
    String ocoTriggerBy;
    String orderIv;
    String marketUnit;
    String triggerPrice;
    String takeProfit;
    String stopLoss;
    String tpslMode;
    String tpLimitPrice;
    String slLimitPrice;
    String tpTriggerBy;
    String slTriggerBy;
    int triggerDirection;
    String triggerBy;
    String lastPriceOnCreated;
    boolean reduceOnly;
    boolean closeOnTrigger;
    String placeType;
    String smpType;
    int smpGroup;
    String smpOrderId;
    String createdTime;
    String updatedTime;
  }
}

