package cn.aghost.http.client.utils;

import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpDataUtils {
  @Nullable
  public static Map<String, List<String>> decodeQueryString(String url) {
    if (StringUtils.isBlank(url)) {
      return null;
    }
    Map<String, List<String>> paramMap = new HashMap<>();
    try {
      HttpUrl httpUrl = HttpUrl.parse(url);

      httpUrl
          .queryParameterNames()
          .forEach(
              name -> {
                try {
                  if (StringUtils.isNotBlank(name)) {
                    List<String> valList = httpUrl.queryParameterValues(name);
                    //                    List<String> noBlankValList = new ArrayList<>();
                    //                    valList.forEach(
                    //                        val -> {
                    //                          if (StringUtils.isNotBlank(val)) {
                    //                            noBlankValList.add(val);
                    //                          }
                    //                        });
                    paramMap.put(name, valList);
                  }
                } catch (Exception e) {
                }
              });
    } catch (Exception e) {
      return null;
    }
    if (paramMap.size() == 0) {
      return null;
    }
    return paramMap;
  }
}
