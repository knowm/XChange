package org.knowm.xchange.enigma.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EnigmaCredentials {
  @JsonProperty("username")
  private String username;

  @JsonProperty("password")
  private String password;
}
