package org.knowm.xchange.therock.dto.trade;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

/** @author Pnk */
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class TheRockMeta {

  private long totalCount;
  private TheRockMetaItem first;
  private TheRockMetaItem previous;
  private TheRockMetaItem current;
  private TheRockMetaItem next;
  private TheRockMetaItem last;

  public long getTotalCount() {
    return totalCount;
  }

  public TheRockMetaItem getFirst() {
    return first;
  }

  public TheRockMetaItem getPrevious() {
    return previous;
  }

  public TheRockMetaItem getCurrent() {
    return current;
  }

  public TheRockMetaItem getNext() {
    return next;
  }

  public TheRockMetaItem getLast() {
    return last;
  }
}
