package cn.aghost.http.client.encoder;

import cn.aghost.http.client.object.EncodePayload;

public interface BaseEncoder<T> {
  static <T> EncodePayload encode(T body) {
    return null;
  }
}
