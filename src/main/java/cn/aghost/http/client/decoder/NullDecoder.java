package cn.aghost.http.client.decoder;

import cn.aghost.http.client.object.HttpResponse;

/** 只会返回空的decoder */
public class NullDecoder implements BaseDecoder<Object> {
  public static Object decode(Class<Object> clazz, HttpResponse resp) {
    return null;
  }
}
