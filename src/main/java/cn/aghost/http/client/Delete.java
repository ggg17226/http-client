package cn.aghost.http.client;

import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.EncodePayload;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.utils.BaseHttpExecutor;
import cn.aghost.http.client.utils.PojoUtils;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Delete {
  /**
   * delete同步请求
   *
   * @param url 请求地址
   * @param param 请求体
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return T类型的类
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws IOException
   */
  public static <T> T doDelete(@NotNull String url, @NotNull Object param, Class<T> clazz)
      throws InvocationTargetException, IllegalAccessException, IOException {
    return doDelete(url, null, param, null, clazz);
  }
  /**
   * delete同步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param param 请求体
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return T类型的类
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws IOException
   */
  public static <T> T doDelete(
      @NotNull String url, @Nullable Headers headers, @NotNull Object param, Class<T> clazz)
      throws InvocationTargetException, IllegalAccessException, IOException {
    return doDelete(url, headers, param, null, clazz);
  }

  /**
   * delete同步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param param 请求体
   * @param clientConfig 连接配置
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return T类型的类
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws IOException
   */
  public static <T> T doDelete(
      @NotNull String url,
      @Nullable Headers headers,
      @NotNull Object param,
      @Nullable ClientConfig clientConfig,
      Class<T> clazz)
      throws InvocationTargetException, IllegalAccessException, IOException {
    EncodePayload encodePayload = PojoUtils.doEncode(param);
    HttpResponse httpResponse =
        doDelete(
            url, headers, encodePayload.getBody(), encodePayload.getContentType(), clientConfig);
    return PojoUtils.doDecode(clazz, httpResponse);
  }

  /**
   * delete同步请求
   *
   * @param url 请求地址
   * @param body 请求体
   * @param contentType 内容类型
   * @return 请求返回
   * @throws IOException
   */
  public static HttpResponse doDelete(
      @NotNull String url, @Nullable byte[] body, @Nullable MediaType contentType)
      throws IOException {
    return doDelete(url, null, body, contentType, null);
  }
  /**
   * delete同步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @return 请求返回
   * @throws IOException
   */
  public static HttpResponse doDelete(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType)
      throws IOException {
    return doDelete(url, headers, body, contentType, null);
  }
  /**
   * delete同步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param clientConfig 连接器配置
   * @return 请求返回
   * @throws IOException
   */
  public static HttpResponse doDelete(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig)
      throws IOException {
    return BaseHttpExecutor.executeWithBody(
        "DELETE", url, headers, body, contentType, clientConfig);
  }
  /**
   * delete异步请求
   *
   * @param url 请求地址
   * @param body 请求体
   * @param contentType 内容类型
   * @param httpCallback 异步callback
   */
  public static void doDeleteAsync(
      @NotNull String url,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @NotNull HttpCallback httpCallback) {
    doDeleteAsync(url, null, body, contentType, null, httpCallback);
  }
  /**
   * delete异步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param httpCallback 异步callback
   */
  public static void doDeleteAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @NotNull HttpCallback httpCallback) {
    doDeleteAsync(url, headers, body, contentType, null, httpCallback);
  }
  /**
   * delete异步请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param clientConfig 连接器配置
   * @param httpCallback 异步callback
   */
  public static void doDeleteAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig,
      @NotNull HttpCallback httpCallback) {
    BaseHttpExecutor.executeWithBodyAsync(
        "DELETE", url, headers, body, contentType, clientConfig, httpCallback);
  }
  /**
   * delete异步请求(okhttp3 原生异步)
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 内容类型
   * @param clientConfig 连接器配置
   * @param callback 异步callback
   */
  public static void doDeleteAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig,
      @NotNull Callback callback) {
    BaseHttpExecutor.executeWithBodyAsync(
        "DELETE", url, headers, body, contentType, clientConfig, callback);
  }
}
