package cn.aghost.http.client.decoder;

import cn.aghost.http.client.object.HttpResponse;

public interface BaseDecoder<T> {

  static <T> T decode(Class<T> clazz, HttpResponse resp) {
    return null;
  }
}
