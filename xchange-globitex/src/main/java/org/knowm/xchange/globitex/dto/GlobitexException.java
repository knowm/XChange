package org.knowm.xchange.globitex.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.InvocationResult;

public class GlobitexException extends HttpStatusIOException {

  @JsonProperty("errors")
  private final List<GlobitexError> errors = new ArrayList<>();

  public GlobitexException(String message, InvocationResult invocationResult) {
    super(message, invocationResult);
    System.out.println("Test " + message);
  }

  //    public GlobitexException(
  //            @JsonProperty("errors") List<GlobitexError> errors) {
  //        this.errors = errors;
  //    }

  public List<GlobitexError> getErrors() {
    return errors;
  }
  //
  //    @Override
  //    public String toString() {
  //        return "GlobitexException{" +
  //                "erros=" + errors +
  //                '}';
  //    }
}
