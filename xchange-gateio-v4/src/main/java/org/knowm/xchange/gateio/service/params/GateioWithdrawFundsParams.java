package org.knowm.xchange.gateio.service.params;

import lombok.Value;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.service.trade.params.DefaultWithdrawFundsParams;

@Value
@SuperBuilder
public class GateioWithdrawFundsParams extends DefaultWithdrawFundsParams {

  String clientRecordId;

  String chain;

}
