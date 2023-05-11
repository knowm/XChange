package org.knowm.xchange.service.trade.params;

import lombok.Value;
import lombok.experimental.SuperBuilder;

@Value
@SuperBuilder
public class RippleWithdrawFundsParams extends DefaultWithdrawFundsParams {

  String tag; // optional

}
