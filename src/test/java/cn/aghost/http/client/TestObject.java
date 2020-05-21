package cn.aghost.http.client;

import cn.aghost.http.client.annotation.HttpCodec;
import lombok.Data;

@Data
@HttpCodec
public class TestObject {
  private String addr;
}
