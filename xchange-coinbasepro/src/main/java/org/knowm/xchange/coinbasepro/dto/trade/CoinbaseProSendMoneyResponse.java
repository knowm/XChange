package org.knowm.xchange.coinbasepro.dto.trade;

public class CoinbaseProSendMoneyResponse {

  private final Data data;

  public CoinbaseProSendMoneyResponse(Data data) {
    this.data = data;
  }

  public Data getData() {
    return data;
  }

  @Override
  public String toString() {
    return "CoinbaseExSendMoneyResponse{" + "data=" + data + '}';
  }

  public static class Data {
    private final String id;

    public Data(String id) {
      this.id = id;
    }

    public String getId() {
      return id;
    }

    @Override
    public String toString() {
      return "Data{" + "id='" + id + '\'' + '}';
    }
  }
}
