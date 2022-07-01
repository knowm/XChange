package org.knowm.xchange.service.trade.params;

public class DefaultCancelOrderByUserReferenceParams implements CancelOrderByUserReferenceParams {

  private String userReference;

  public DefaultCancelOrderByUserReferenceParams() {}

  public DefaultCancelOrderByUserReferenceParams(String userReference) {
    this.userReference = userReference;
  }

  @Override
  public String getUserReference() {
    return userReference;
  }
}
