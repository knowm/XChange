package org.knowm.xchange.coinsetter.dto.clientsession.request;

/**
 * Request for login as a customer and create a session.
 */
public class CoinsetterLoginRequest {

  private final String username;
  private final String password;
  private final String ipAddress;

  /**
   * Constructs login request.
   *
   * @param username User's unique username.
   * @param password User's password (clear text).
   * @param ipAddress Public IP address.
   */
  public CoinsetterLoginRequest(String username, String password, String ipAddress) {

    this.username = username;
    this.password = password;
    this.ipAddress = ipAddress;
  }

  public String getUsername() {

    return username;
  }

  public String getPassword() {

    return password;
  }

  public String getIpAddress() {

    return ipAddress;
  }

}
