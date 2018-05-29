package org.knowm.xchange.bibox.dto;

import java.util.List;
import org.knowm.xchange.bibox.dto.BiboxPagedResponses.BiboxPage;

/**
 * paged result
 *
 * @param <T>
 */
public class BiboxPagedResponses<T> extends BiboxSingleResponse<BiboxPage<T>> {

  public static class BiboxPage<I> {
    private int count;
    private int page;
    private List<I> items;

    public int getCount() {
      return count;
    }

    public int getPage() {
      return page;
    }

    public List<I> getItems() {
      return items;
    }
  }
}
