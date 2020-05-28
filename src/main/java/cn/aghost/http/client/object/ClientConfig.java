package cn.aghost.http.client.object;

import cn.aghost.http.client.utils.HttpClientUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.net.Proxy;

/** http client 配置 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientConfig {
  private String tag = null;
  /** 全局最大发起连接数 */
  private int maxRequest = HttpClientUtil.DEFAULT_MAX_REQUEST;
  /** 单个host最大发起连接数 */
  private int maxRequestPerHost = HttpClientUtil.DEFAULT_MAX_REQUEST_PER_HOST;
  /** 单位 秒 */
  private int readTimeout = HttpClientUtil.DEFAULT_TIMEOUT;
  /** 单位 秒 */
  private int connectTimeout = HttpClientUtil.DEFAULT_TIMEOUT;
  /** 单位 秒 */
  private int writeTimeout = HttpClientUtil.DEFAULT_TIMEOUT;
  /** 是否强制http1，为false时当http2可用就用http2 */
  private boolean forceHttp1 = HttpClientUtil.DEFAULT_FORCE_HTTP1;
  /** 是否跳过ssl检查 */
  private boolean skipSslCheck = HttpClientUtil.DEFAULT_SKIP_SSL_CHECK;
  /** 代理配置 */
  @Nullable private Proxy proxy = null;
}
