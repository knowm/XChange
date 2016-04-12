package org.knowm.xchange.btcchina.service.fix.fix44;

import org.knowm.xchange.btcchina.service.fix.field.AccReqID;

import quickfix.FieldNotFound;
import quickfix.field.Account;
import quickfix.field.MsgType;
import quickfix.fix44.Message;

public class AccountInfoRequest extends Message {

  private static final long serialVersionUID = 20141122L;

  public static final String MSGTYPE = "U1000";

  public AccountInfoRequest() {

    getHeader().setField(new MsgType(MSGTYPE));
  }

  public void set(Account value) {

    setField(value);
  }

  public Account get(Account value) throws FieldNotFound {

    getField(value);
    return value;
  }

  public Account getAccount() throws FieldNotFound {

    Account value = new Account();
    getField(value);
    return value;
  }

  public boolean isSet(Account field) {

    return isSetField(field);
  }

  public boolean isSetAccount() {

    return isSetField(Account.FIELD);
  }

  public void set(AccReqID value) {

    setField(value);
  }

  public AccReqID get(AccReqID value) throws FieldNotFound {

    getField(value);
    return value;
  }

  public AccReqID getAccReqID() throws FieldNotFound {

    AccReqID value = new AccReqID();
    getField(value);
    return value;
  }

  public boolean isSet(AccReqID field) {

    return isSetField(field);
  }

  public boolean isSetAccReqID() {

    return isSetField(AccReqID.FIELD);
  }

}
