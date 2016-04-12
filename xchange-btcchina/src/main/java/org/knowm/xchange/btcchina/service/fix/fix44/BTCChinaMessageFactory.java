package org.knowm.xchange.btcchina.service.fix.fix44;

import quickfix.Message;
import quickfix.fix44.MessageFactory;

/**
 * {@link MessageFactory} that added BTCChina customized message support.
 */
public class BTCChinaMessageFactory extends MessageFactory {

  @Override
  public Message create(String beginString, String msgType) {

    if (AccountInfoRequest.MSGTYPE.equals(msgType)) {
      return new AccountInfoRequest();
    }

    if (AccountInfoResponse.MSGTYPE.equals(msgType)) {
      return new AccountInfoResponse();
    }

    return super.create(beginString, msgType);
  }

}
