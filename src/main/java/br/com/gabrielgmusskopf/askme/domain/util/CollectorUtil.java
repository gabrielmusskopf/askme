package br.com.gabrielgmusskopf.askme.domain.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CollectorUtil {

  private static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(
      Collectors.toCollection(ArrayList::new),
      list -> {
        Collections.shuffle(list);
        return list;
      }
  );

  @SuppressWarnings("unchecked")
  public static <T> Collector<T, ?, List<T>> toShuffledList() {
    return (Collector<T, ?, List<T>>) SHUFFLER;
  }

}
