package cn.aghost.http.client.encoder;

import cn.aghost.http.client.object.EncodePayload;

/** 只会返回空的encoder */
public class NullEncoder implements BaseEncoder<Object> {

  public static EncodePayload encode(Object body) {
    return null;
  }
}
