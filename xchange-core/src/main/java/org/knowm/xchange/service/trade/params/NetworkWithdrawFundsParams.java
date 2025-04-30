package org.knowm.xchange.service.trade.params;

import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

@Value
@NonFinal
@SuperBuilder
@ToString(callSuper = true)
public class NetworkWithdrawFundsParams extends DefaultWithdrawFundsParams {
  String network;
}
