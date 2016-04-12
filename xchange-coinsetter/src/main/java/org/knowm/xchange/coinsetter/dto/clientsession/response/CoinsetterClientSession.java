package org.knowm.xchange.coinsetter.dto.clientsession.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinsetter.dto.CoinsetterResponse;

/**
 * Response for login as a customer and create a session.
 */
public class CoinsetterClientSession extends CoinsetterResponse {

  private final UUID uuid;
  private final UUID customerUuid;
  private final String customerPasswordStatus;
  private final String username;
  private final String customerStatus;

  /**
   * @param uuid Client session ID, if success.
   * @param message If login success, message will be "OK". Otherwise, it will description the issue.
   * @param customerUuid Customer UUID.
   * @param requestStatus Either "SUCCESS" or "FAILURE".
   * @param customerPasswordStatus ACTIVE
   * @param username User's unique username, if success.
   * @param customerStatus Customer status (i.e. ACTIVE, PENDING_EMAIL_CONFIRMATION, etc.).
   */
  public CoinsetterClientSession(@JsonProperty("uuid") UUID uuid, @JsonProperty("message") String message,
      @JsonProperty("customerUuid") UUID customerUuid, @JsonProperty("requestStatus") String requestStatus,
      @JsonProperty("customerPasswordStatus") String customerPasswordStatus, @JsonProperty("username") String username,
      @JsonProperty("customerStatus") String customerStatus) {

    super(message, requestStatus);
    this.uuid = uuid;
    this.customerUuid = customerUuid;
    this.customerPasswordStatus = customerPasswordStatus;
    this.username = username;
    this.customerStatus = customerStatus;
  }

  public UUID getUuid() {

    return uuid;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public String getCustomerPasswordStatus() {

    return customerPasswordStatus;
  }

  public String getUsername() {

    return username;
  }

  public String getCustomerStatus() {

    return customerStatus;
  }

  @Override
  public String toString() {

    return "CoinsetterClientSession [uuid=" + uuid + ", customerUuid=" + customerUuid + ", customerPasswordStatus=" + customerPasswordStatus
        + ", username=" + username + ", customerStatus=" + customerStatus + "]";
  }

}
