package cn.aghost.http.client.object;

import lombok.Data;
import okhttp3.MediaType;

@Data
public class EncodePayload {
  /** http body */
  private byte[] body;
  /** http content type */
  private MediaType contentType;
}
