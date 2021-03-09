package info.bitrich.xchangestream.util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** @author Foat Akhmadeev 05/06/2018 */
public class CollectionUtils {
  static <T> Iterable<T> descendingIterable(List<? extends T> list) {
    return () -> {
      ListIterator<? extends T> li = list.listIterator(list.size());
      return new Iterator<T>() {
        public boolean hasNext() {
          return li.hasPrevious();
        }

        public T next() {
          return li.previous();
        }
      };
    };
  }
}
