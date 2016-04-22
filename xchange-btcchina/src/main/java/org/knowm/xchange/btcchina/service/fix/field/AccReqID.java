package org.knowm.xchange.btcchina.service.fix.field;

import quickfix.StringField;

public class AccReqID extends StringField {

  private static final long serialVersionUID = 20141122L;

  public static final int FIELD = 8000;

  public AccReqID() {

    super(FIELD);
  }

  public AccReqID(String data) {

    super(FIELD, data);
  }

}
