package info.bitrich.xchangestream.ftx.dto;

public class FtxWebsocketCredential {
  private final String apiKey;
  private final String secretKey;
  private final String userName;

  public FtxWebsocketCredential(String apiKey, String secretKey, String userName) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
    this.userName = userName;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String getUserName() {
    return userName;
  }
}
