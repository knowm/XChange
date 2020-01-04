package org.knowm.xchange.deribit.v2.dto;

public enum GrantType {
  /** using email and and password as when logging on to the website */
  password,
  /** using the access key and access secret that can be found on the API page on the website */
  client_credentials,
  /**
   * using the access key that can be found on the API page on the website and user generated
   * signature. The signature is calculated using some fields provided in the request, using formula
   * described here https://docs.deribit.com/v2/#authentication
   */
  client_signature,
  /** using a refresh token that was received from an earlier invocation */
  refresh_token;
}
