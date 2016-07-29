package org.knowm.xchange.ripple.dto.account;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.ripple.dto.RippleException;

public interface ITransferFeeSource {
  public BigDecimal getTransferFeeRate(String address) throws RippleException, IOException;
}
