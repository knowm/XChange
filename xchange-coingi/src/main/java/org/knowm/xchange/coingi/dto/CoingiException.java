package org.knowm.xchange.coingi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;
import si.mazi.rescu.HttpStatusExceptionSupport;

public class CoingiException extends HttpStatusExceptionSupport {
  private List<Map<String, Object>> errors;

  public CoingiException(@JsonProperty("errors") Object errors) {
    super(getMessage(errors));

    if (errors instanceof List) {
      try {
        this.errors = (List<Map<String, Object>>) errors;
      } catch (Exception ignore) {
      }
    }
  }

  private static String getMessage(Object errors) {
    if (errors instanceof Map) {
      try {
        List<Map<String, Object>> err = (List<Map<String, Object>>) errors;
        final StringBuilder sb = new StringBuilder();
        for (Map<String, Object> error : err) {
          for (String key : error.keySet()) {
            if (sb.length() > 0) {
              sb.append(" -- ");
            }
            sb.append(error.get(key));
          }
        }

        return sb.toString();
      } catch (Exception ignore) {
      }
    }

    return String.valueOf(errors);
  }

  public List<Map<String, Object>> getErrors() {
    return errors;
  }
}
