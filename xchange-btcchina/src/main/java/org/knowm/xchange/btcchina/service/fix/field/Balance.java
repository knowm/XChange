package org.knowm.xchange.btcchina.service.fix.field;

import quickfix.IntField;

public class Balance extends IntField {

  static final long serialVersionUID = 20141122L;

  public static final int FIELD = 9000;

  public Balance() {

    super(FIELD);
  }

  public Balance(int data) {

    super(FIELD, data);
  }

}
