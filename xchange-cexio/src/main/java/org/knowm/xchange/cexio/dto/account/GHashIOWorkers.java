package org.knowm.xchange.cexio.dto.account;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Author: veken0m
 */

public class GHashIOWorkers {

  private final Map<String, GHashIOWorker> workers = new HashMap<>();

  public Map<String, GHashIOWorker> getWorkers() {

    return workers;
  }

  @JsonAnySetter
  public void setWorker(String name, GHashIOWorker worker) {

    this.workers.put(name, worker);
  }

}
