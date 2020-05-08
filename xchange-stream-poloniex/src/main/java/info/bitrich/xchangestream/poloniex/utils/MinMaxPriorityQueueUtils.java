package info.bitrich.xchangestream.poloniex.utils;

import com.google.common.collect.MinMaxPriorityQueue;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinMaxPriorityQueueUtils {
  public static <T> List toList(MinMaxPriorityQueue<T> queue, Comparator<? super T> comparator) {
    List<T> list = new ArrayList<T>(queue.size());
    for (T e : queue) list.add(e);
    if (comparator != null) {
      list.sort(comparator);
    }
    return list;
  }
}
