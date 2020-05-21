package cn.aghost.http.client.object;

import cn.aghost.http.client.utils.BaseHttpExecutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.Headers;
import okhttp3.Protocol;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.Charset;

/** http resp 对象 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
  /** http协议 */
  private Protocol protocol;
  /** 请求返回状态码 */
  private int code;
  /** 返回头 */
  private Headers headers;
  /** 返回体 */
  private byte[] body;
  /** 是否成功 */
  private boolean successful;
  /** 是否经过重定向 */
  private boolean redirect;
  /** 返回body 类型 */
  @Nullable private String contentType;
  /** 返回body 编码 */
  @Nullable private String charset;
  /** 自动转换的字符串型的返回体 */
  @Nullable private String bodyString;

  public HttpResponse(Response rsp) throws IOException {
    this.protocol = rsp.protocol();
    this.code = rsp.code();
    this.headers = rsp.headers();
    this.body = rsp.body().bytes();
    this.successful = rsp.isSuccessful();
    this.redirect = rsp.isRedirect();
    this.headers
        .names()
        .forEach(
            name -> {
              if (StringUtils.isNotBlank(name)
                  && StringUtils.isNotBlank(name)
                  && "content-type".equals(name.toLowerCase().trim())) {
                String value = this.headers.get(name);
                this.contentType = value.trim().toLowerCase();
              }
            });
    if (StringUtils.isNotBlank(this.contentType) && StringUtils.contains(this.contentType, "; ")) {
      String[] arr = this.contentType.split("; ");
      if (arr.length == 2) {
        this.contentType = arr[0].trim();
        this.charset = arr[1].replaceAll("charset=", "").trim();
        if (BaseHttpExecutor.isAutoDecodeBody()) {
          this.bodyString = this.autoDecodeBody();
        }
      }
    }
  }

  public String autoDecodeBody() {
    try {
      if (StringUtils.isNotBlank(this.bodyString)) {
        return this.bodyString;
      } else {
        if (StringUtils.isNotBlank(this.charset)) {
          Charset charset = Charset.forName(this.charset);
          return new String(body, charset);
        } else {
          return new String(body);
        }
      }
    } catch (Exception e) {
      return null;
    }
  }
}
