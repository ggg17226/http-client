package cn.aghost.http.client.object;

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
  /** 全局最大发起连接数 */
  private int maxRequest;
  /** 单个host最大发起连接数 */
  private int maxRequestPerHost;
  /** 单位 秒 */
  private int readTimeout;
  /** 单位 秒 */
  private int connectTimeout;
  /** 单位 秒 */
  private int writeTimeout;
  /** 是否强制http1，为false时当http2可用就用http2 */
  private boolean forceHttp1;
  /** 代理配置 */
  @Nullable private Proxy proxy;
}
