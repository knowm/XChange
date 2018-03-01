package org.knowm.xchange.idex


import org.knowm.xchange.idex.dto.*
import org.knowm.xchange.service.trade.params.*

class IdexWithdraw(address: String, amount: String, token: String, nonce: String, s: String, v: Int,
                   r: String) : WithdrawReq(), WithdrawFundsParams {init {
    address(address)
            .amount(amount)
            .token(token)
            .nonce(nonce)
            .s(s)
            .v(v)
            .r(r)
}
}

