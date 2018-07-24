package org.knowm.xchange.bity.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.List;

public class BityOrder {

  @JsonProperty("category")
  private String category;

  @JsonProperty("inputtransactions")
  private List<BityInputTransaction> bityInputTransactions = null;

  @JsonProperty("outputtransactions")
  private List<BityOutputTransaction> bityOutputTransactions = null;

  @JsonProperty("person")
  private String person;

  @JsonProperty("resource_uri")
  private String resourceUri;

  @JsonProperty("status")
  private String status;

  @JsonProperty("timestamp_created")
  private Date timestampCreated;

  @JsonProperty("category")
  public String getCategory() {
    return category;
  }

  @JsonProperty("category")
  public void setCategory(String category) {
    this.category = category;
  }

  @JsonProperty("bityInputTransactions")
  public List<BityInputTransaction> getBityInputTransactions() {
    return bityInputTransactions;
  }

  @JsonProperty("bityInputTransactions")
  public void setBityInputTransactions(List<BityInputTransaction> bityInputTransactions) {
    this.bityInputTransactions = bityInputTransactions;
  }

  @JsonProperty("bityOutputTransactions")
  public List<BityOutputTransaction> getBityOutputTransactions() {
    return bityOutputTransactions;
  }

  @JsonProperty("bityOutputTransactions")
  public void setBityOutputTransactions(List<BityOutputTransaction> bityOutputTransactions) {
    this.bityOutputTransactions = bityOutputTransactions;
  }

  @JsonProperty("person")
  public String getPerson() {
    return person;
  }

  @JsonProperty("person")
  public void setPerson(String person) {
    this.person = person;
  }

  @JsonProperty("resource_uri")
  public String getResourceUri() {
    return resourceUri;
  }

  @JsonProperty("resource_uri")
  public void setResourceUri(String resourceUri) {
    this.resourceUri = resourceUri;
  }

  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  @JsonProperty("timestamp_created")
  public Date getTimestampCreated() {
    return timestampCreated;
  }

  @JsonProperty("timestamp_created")
  public void setTimestampCreated(Date timestampCreated) {
    this.timestampCreated = timestampCreated;
  }
}
