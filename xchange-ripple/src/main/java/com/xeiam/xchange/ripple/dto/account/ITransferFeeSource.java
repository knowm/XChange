package com.xeiam.xchange.ripple.dto.account;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.ripple.dto.RippleException;

public interface ITransferFeeSource {
  public BigDecimal getTransferFeeRate(String address) throws RippleException, IOException;
}
