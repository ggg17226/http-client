package cn.aghost.http.client;

import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.utils.BaseHttpExecutor;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class Put {
  /**
   * put同步请求
   *
   * @param url 请求地址
   * @param body 请求体
   * @param contentType 内容类型
   * @return 请求返回
   * @throws IOException
   */
  public static HttpResponse doPut(
      @NotNull String url, @Nullable byte[] body, @Nullable MediaType contentType)
      throws IOException {
    return doPut(url, null, body, contentType, null);
  }
  /**
   * put同步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @return 请求返回
   * @throws IOException
   */
  public static HttpResponse doPut(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType)
      throws IOException {
    return doPut(url, headers, body, contentType, null);
  }
  /**
   * put同步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param clientConfig 连接器配置
   * @return 请求返回
   * @throws IOException
   */
  public static HttpResponse doPut(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig)
      throws IOException {
    return BaseHttpExecutor.executeWithBody("PUT", url, headers, body, contentType, clientConfig);
  }
  /**
   * put异步请求
   *
   * @param url 请求地址
   * @param body 请求体
   * @param contentType 内容类型
   * @param httpCallback 异步callback
   */
  public static void doPutAsync(
      @NotNull String url,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @NotNull HttpCallback httpCallback) {
    doPutAsync(url, null, body, contentType, null, httpCallback);
  }
  /**
   * put异步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param httpCallback 异步callback
   */
  public static void doPutAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @NotNull HttpCallback httpCallback) {
    doPutAsync(url, headers, body, contentType, null, httpCallback);
  }
  /**
   * put异步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param clientConfig 连接器配置
   * @param httpCallback 异步callback
   */
  public static void doPutAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig,
      @NotNull HttpCallback httpCallback) {
    BaseHttpExecutor.executeWithBodyAsync(
        "PUT", url, headers, body, contentType, clientConfig, httpCallback);
  }
  /**
   * put异步请求(okhttp3 原生异步)
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param clientConfig 连接器配置
   * @param callback 异步callback
   */
  public static void doPutAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig,
      @NotNull Callback callback) {
    BaseHttpExecutor.executeWithBodyAsync(
        "PUT", url, headers, body, contentType, clientConfig, callback);
  }
}
