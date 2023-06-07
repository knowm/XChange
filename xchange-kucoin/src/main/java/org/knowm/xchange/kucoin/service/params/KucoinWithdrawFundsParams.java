package org.knowm.xchange.kucoin.service.params;

import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;

@Value
@SuperBuilder
public class KucoinWithdrawFundsParams extends DefaultWithdrawFundsParams {

  String chain;

  String remark;

}
