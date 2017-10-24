package org.knowm.xchange.huobi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * fee : 0, //手续费 id : 2489, //订单ID storeId : 0, // 仓位ID tradeType : 2, //交易类型（1、买多 2、卖空） price : 215.77, //价格 orderType : 1, //订单类型（1、开仓 2、平仓） status
 * : 0, //订单状态（0、未成交 1、部分成交 2、已成交 3、撤单 7、队列中） money : 200, //下单金额 amount : 0.08130081, // 订单比特币数量 lever : 10, // 订单杠杆倍数 orderTime : 1409991500, //下单时间
 * lastTime : 1409991500, //最后处理时间 processedMoney : 0, //已处理金额 processedAmount : 0, // 已处理比特币数量 margin : 0.008130081, // 订单冻结保证金 processedPrice : 0 //
 * 成交价格
 */
public class BitVcFuturesPlaceOrderResult {

  private final long id;

  public BitVcFuturesPlaceOrderResult(@JsonProperty("id") final long id) {

    this.id = id;
  }

  public long getId() {

    return id;
  }
}
