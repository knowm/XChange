package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
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
  "country"
})
public final class BitmexAccount extends AbstractHttpResponseAware {

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

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public Integer getId() {
    return id;
  }

  public Integer getOwnerId() {
    return ownerId;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public String getCreated() {
    return created;
  }

  public String getLastUpdated() {
    return lastUpdated;
  }

  public BitmexAccountPreferences getPreferences() {
    return preferences;
  }

  public String gettFAEnabled() {
    return tFAEnabled;
  }

  public String getAffiliateID() {
    return affiliateID;
  }

  public String getPgpPubKey() {
    return pgpPubKey;
  }

  public String getCountry() {
    return country;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }
}
