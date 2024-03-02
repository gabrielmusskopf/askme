package br.com.gabrielgmusskopf.myquestion.domain.util;

import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MathUtil {

  private static final Random RANDOM = new Random();

  public long random(long low, long high) {
    if (low == high) {
      return low;
    }
    if (low > high) {
      long aux = low;
      low = high;
      high = aux;
    }
    return RANDOM.nextLong(high - low) + low;
  }

}
