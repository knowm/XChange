package org.knowm.xchange.btcchina.service.fix.field;

import java.math.BigDecimal;

import quickfix.DecimalField;

public class Amount extends DecimalField {

  private static final long serialVersionUID = 20141122L;

  public static final int FIELD = 8001;

  public Amount() {

    super(FIELD);
  }

  public Amount(BigDecimal data) {

    super(FIELD, data);
  }

  public Amount(double data) {

    super(FIELD, new BigDecimal(data));
  }

}
