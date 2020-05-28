package cn.aghost.http.client.utils;

import cn.aghost.http.client.exceptions.DuplicateHttpClientNameException;
import cn.aghost.http.client.exceptions.HttpClientNotFoundException;
import cn.aghost.http.client.object.ClientConfig;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** http client 初始化和保持工具类 */
@Slf4j
public class HttpClientUtil {
  /** 默认连接器名 */
  public static final String DEFAULT_CLIENT_NAME = "default_client";
  /** 默认全局最大连接数 */
  public static final int DEFAULT_MAX_REQUEST = 500;
  /** 单个host最大发起连接数 */
  public static final int DEFAULT_MAX_REQUEST_PER_HOST = 10;
  /** 超时时间 单位 秒 */
  public static final int DEFAULT_TIMEOUT = 30;
  /** 是否强制http1，为false时当http2可用就用http2 */
  public static final boolean DEFAULT_FORCE_HTTP1 = false;
  /** 是否跳过ssl检查 */
  public static final boolean DEFAULT_SKIP_SSL_CHECK = false;
  /** 默认代理配置 */
  public static final Proxy DEFAULT_PROXY = null;
  /** http client map */
  private static Map<String, OkHttpClient> clientMap = new ConcurrentHashMap<>();
  /** 初始化默认client的锁 */
  private static Lock defaultClientLock = new ReentrantLock();

  /**
   * 根据连接配置构造连接名
   *
   * @param clientConfig 连接配置
   * @return 连接名
   */
  public static String buildClientName(@Nullable ClientConfig clientConfig) {
    if (clientConfig == null
        || clientConfig.getMaxRequest() < 1
        || clientConfig.getMaxRequestPerHost() < 1
        || clientConfig.getReadTimeout() < 1
        || clientConfig.getConnectTimeout() < 1
        || clientConfig.getWriteTimeout() < 1) {
      return DEFAULT_CLIENT_NAME;
    }
    StringBuilder sb = new StringBuilder();
    sb.append(clientConfig.getMaxRequest());
    sb.append("-");
    sb.append(clientConfig.getMaxRequestPerHost());
    sb.append("-");
    sb.append(clientConfig.getReadTimeout());
    sb.append("-");
    sb.append(clientConfig.getConnectTimeout());
    sb.append("-");
    sb.append(clientConfig.getWriteTimeout());
    sb.append("-");
    sb.append(clientConfig.isForceHttp1());
    sb.append("-");
    sb.append(clientConfig.isSkipSslCheck());
    sb.append("-");
    sb.append(JSON.toJSONString(clientConfig.getProxy()));

    return DigestUtils.md5Hex(sb.toString());
  }

  /**
   * 根据config获取连接
   *
   * @param clientConfig 连接配置
   * @return 连接
   */
  public static OkHttpClient getClient(@Nullable ClientConfig clientConfig) {
    String clientName = buildClientName(clientConfig);
    return (DEFAULT_CLIENT_NAME.equals(clientName))
        ? getClient()
        : getClient(clientName, clientConfig);
  }

  /**
   * 获取默认client
   *
   * @return 默认client
   */
  public static OkHttpClient getClient() {
    if (!clientMap.containsKey(DEFAULT_CLIENT_NAME)) {
      try (LockWrapper l = new LockWrapper(defaultClientLock)) {
        l.lock();
        createClient();
      } catch (Exception e) {
      }
    }
    return clientMap.get(DEFAULT_CLIENT_NAME);
  }

  /**
   * 根据名字获取client
   *
   * @param clientName client名
   * @return client
   * @throws HttpClientNotFoundException 无此名字的client
   */
  public static OkHttpClient getClient(@NotNull String clientName)
      throws HttpClientNotFoundException {
    if (!clientMap.containsKey(clientName)) {
      throw new HttpClientNotFoundException();
    }
    return clientMap.get(clientName);
  }

  /**
   * 根据名字获取client，若不存在就创建
   *
   * @param clientName client名
   * @param clientConfig client配置
   * @return client
   */
  public static OkHttpClient getClient(
      @NotNull String clientName, @NotNull ClientConfig clientConfig) {
    if (!clientMap.containsKey(clientName)) {
      try {
        createClient(clientName, clientConfig);
      } catch (Exception e) {
        return null;
      }
    }
    return clientMap.get(clientName);
  }

  /**
   * 创建client
   *
   * @param clientName client名
   * @param clientConfig client配置
   * @throws DuplicateHttpClientNameException 已有相同名字的client存在
   */
  public static synchronized void createClient(String clientName, ClientConfig clientConfig)
      throws DuplicateHttpClientNameException, KeyManagementException, NoSuchAlgorithmException {
    log.debug("create client({}),{}", clientName, clientConfig.toString());
    if (clientMap.containsKey(clientName)) {
      throw new DuplicateHttpClientNameException();
    }

    Dispatcher dispatcher = new Dispatcher();
    dispatcher.setMaxRequests(clientConfig.getMaxRequest());
    dispatcher.setMaxRequestsPerHost(clientConfig.getMaxRequestPerHost());
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    builder
        .readTimeout(clientConfig.getReadTimeout(), TimeUnit.SECONDS)
        .connectTimeout(clientConfig.getConnectTimeout(), TimeUnit.SECONDS)
        .writeTimeout(clientConfig.getWriteTimeout(), TimeUnit.SECONDS)
        .dispatcher(dispatcher)
        .protocols(
            clientConfig.isForceHttp1()
                ? Arrays.asList(Protocol.HTTP_1_1)
                : Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2));
    if (clientConfig.isSkipSslCheck()) {
      setDoesNotCheckSsl(builder);
    }
    if (clientConfig.getProxy() != null) {
      builder.proxy(clientConfig.getProxy());
    }
    clientMap.put(clientName, builder.build());
  }

  private static void setDoesNotCheckSsl(OkHttpClient.Builder builder)
      throws NoSuchAlgorithmException, KeyManagementException {
    final TrustManager[] trustAllCerts =
        new TrustManager[] {
          new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                java.security.cert.X509Certificate[] chain, String authType) {}

            @Override
            public void checkServerTrusted(
                java.security.cert.X509Certificate[] chain, String authType) {}

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return new java.security.cert.X509Certificate[] {};
            }
          }
        };
    final SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
    final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
    builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
    builder.hostnameVerifier((s, sslSession) -> true);
  }

  /**
   * 创建默认client
   *
   * @throws DuplicateHttpClientNameException 已有相同名字的client存在
   */
  private static void createClient()
      throws DuplicateHttpClientNameException, NoSuchAlgorithmException, KeyManagementException {
    ClientConfig clientConfig =
        new ClientConfig(
            null,
            DEFAULT_MAX_REQUEST,
            DEFAULT_MAX_REQUEST_PER_HOST,
            DEFAULT_TIMEOUT,
            DEFAULT_TIMEOUT,
            DEFAULT_TIMEOUT,
            DEFAULT_FORCE_HTTP1,
            DEFAULT_SKIP_SSL_CHECK,
            DEFAULT_PROXY);
    createClient(DEFAULT_CLIENT_NAME, clientConfig);
  }
}
