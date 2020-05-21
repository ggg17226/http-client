package cn.aghost.http.client.decoder;

import cn.aghost.http.client.object.HttpResponse;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

public class JsonDecoder<T> implements BaseDecoder<T> {

  public static <T> T decode(Class<T> clazz, HttpResponse resp) {
    String bodyString = null;
    if (resp.getBody() == null || resp.getBody().length < 2) {
      return null;
    }
    if (StringUtils.isNotBlank(resp.getBodyString())) {
      bodyString = resp.getBodyString();
    } else if (StringUtils.isNotBlank(resp.getCharset())
        && StringUtils.isNotBlank(resp.getContentType())) {
      bodyString = parseString(resp.getBody(), Charset.forName(resp.getCharset()));
    } else {
      bodyString = parseString(resp.getBody(), Charset.defaultCharset());
    }
    return JSON.parseObject(bodyString, clazz);
  }

  private static String parseString(byte[] data, Charset charset) {
    return new String(data, charset);
  }
}
