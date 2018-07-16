package org.knowm.xchange.coindirect.dto.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoindirectErrors {
  List<CoindirectError> errorList;
}
