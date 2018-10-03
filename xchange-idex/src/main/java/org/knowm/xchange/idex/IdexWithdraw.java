package org.knowm.xchange.idex;

import java.math.BigInteger;
import org.knowm.xchange.idex.dto.WithdrawReq;
import org.knowm.xchange.service.trade.params.WithdrawFundsParams;

final class IdexWithdraw extends WithdrawReq implements WithdrawFundsParams {
  public IdexWithdraw(
      String address,
      String amount,
      String token,
      BigInteger nonce,
      String s,
      BigInteger v,
      String r) {
    address(address).amount(amount).token(token).nonce(nonce).s(s).v(v).r(r);
  }
}
