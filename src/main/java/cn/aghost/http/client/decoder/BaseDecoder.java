package cn.aghost.http.client.decoder;

import cn.aghost.http.client.object.HttpResponse;

public interface BaseDecoder<T> {
  T decode(HttpResponse resp);
}
