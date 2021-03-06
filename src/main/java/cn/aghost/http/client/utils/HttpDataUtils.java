package cn.aghost.http.client.utils;

import cn.aghost.http.client.object.SchemesEnum;
import okhttp3.HttpUrl;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpDataUtils {
  /**
   * 构建请求url
   *
   * @param scheme 协议 http/https
   * @param host 域名
   * @param path 请求uri
   * @param queryParam 请求url参数
   * @return 完整url
   */
  public static String buildUrl(
      @NotNull SchemesEnum scheme,
      @NotNull String host,
      @NotNull String path,
      @Nullable Map<String, List<String>> queryParam) {
    HttpUrl.Builder builder =
        new HttpUrl.Builder().scheme(scheme.toString()).host(host).addPathSegment(path);
    if (queryParam != null) {
      queryParam.forEach(
          (k, v) -> {
            if (v != null) v.forEach(val -> builder.addQueryParameter(k, val));
          });
    }
    return builder.build().toString();
  }

  /**
   * 解析url中的query string 参数
   *
   * @param url url
   * @return query string 参数
   */
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
