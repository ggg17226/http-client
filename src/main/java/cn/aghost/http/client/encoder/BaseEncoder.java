package cn.aghost.http.client.encoder;

public interface BaseEncoder<T> {
  byte[] encode(Class<T> body);
}
