package br.com.gabrielgmusskopf.askme.domain.util;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryUtil {

  private static final String SPECIAL_CHARACTERS_REGEX = "[^a-zA-Z0-9]";
  private static final String SPACE_REPLACER_CHARACTER = "_";

  public List<String> normalizeAll(List<String> categories) {
    return categories.stream().map(CategoryUtil::normalize).toList();
  }

  public String normalize(String category) {
    return Normalizer.normalize(category, Form.NFD)
        .replaceAll("\\p{M}", "")
        .replace(SPECIAL_CHARACTERS_REGEX, "")
        .toUpperCase()
        .replace(" ", SPACE_REPLACER_CHARACTER).trim();
  }

}
