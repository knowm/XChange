package org.knowm.xchange.bitmex.dto.account;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id",
                       "ownerId",
                       "firstname",
                       "lastname",
                       "username",
                       "email",
                       "phone",
                       "created",
                       "lastUpdated",
                       "preferences",
                       "TFAEnabled",
                       "affiliateID",
                       "pgpPubKey",
                       "country"})
public final class BitmexAccount {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("ownerId")
  private Integer ownerId;
  @JsonProperty("firstname")
  private String firstname;
  @JsonProperty("lastname")
  private String lastname;
  @JsonProperty("username")
  private String username;
  @JsonProperty("email")
  private String email;
  @JsonProperty("phone")
  private String phone;
  @JsonProperty("created")
  private String created;
  @JsonProperty("lastUpdated")
  private String lastUpdated;
  @JsonProperty("preferences")
  private BitmexAccountPreferences preferences;
  @JsonProperty("TFAEnabled")
  private String tFAEnabled;
  @JsonProperty("affiliateID")
  private String affiliateID;
  @JsonProperty("pgpPubKey")
  private String pgpPubKey;
  @JsonProperty("country")
  private String country;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<>();

}