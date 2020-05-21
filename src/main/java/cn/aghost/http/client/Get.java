package cn.aghost.http.client;

import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.utils.BaseHttpExecutor;
import cn.aghost.http.client.utils.PojoUtils;
import okhttp3.Callback;
import okhttp3.Headers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Get {
  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return 返回值
   * @throws IOException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public static <T> T doGet(@NotNull String url, Class<T> clazz)
      throws IOException, InvocationTargetException, IllegalAccessException {
    HttpResponse httpResponse = doGet(url, null, (ClientConfig) null);
    return PojoUtils.doDecode(clazz, httpResponse);
  }
  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return 返回值
   * @throws IOException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public static <T> T doGet(@NotNull String url, @Nullable Headers headers, Class<T> clazz)
      throws IOException, InvocationTargetException, IllegalAccessException {
    HttpResponse httpResponse = doGet(url, headers, (ClientConfig) null);
    return PojoUtils.doDecode(clazz, httpResponse);
  }

  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return 返回值
   * @throws IOException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public static <T> T doGet(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable ClientConfig clientConfig,
      Class<T> clazz)
      throws IOException, InvocationTargetException, IllegalAccessException {
    HttpResponse httpResponse = doGet(url, headers, clientConfig);
    return PojoUtils.doDecode(clazz, httpResponse);
  }

  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @return 返回值
   * @throws IOException
   */
  public static HttpResponse doGet(@NotNull String url) throws IOException {
    return doGet(url, null, (ClientConfig) null);
  }

  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @return 返回值
   * @throws IOException
   */
  public static HttpResponse doGet(@NotNull String url, @Nullable Headers headers)
      throws IOException {
    return doGet(url, headers, (ClientConfig) null);
  }

  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @return 返回值
   * @throws IOException
   */
  public static HttpResponse doGet(
      @NotNull String url, @Nullable Headers headers, @Nullable ClientConfig clientConfig)
      throws IOException {
    return BaseHttpExecutor.executeGet(url, headers, clientConfig);
  }

  /**
   * 执行异步http get请求
   *
   * @param url 请求地址
   * @param httpCallback callback类
   */
  public static void doGetAsync(@NotNull String url, @NotNull HttpCallback httpCallback) {
    doGetAsync(url, null, null, httpCallback);
  }
  /**
   * 执行异步http get请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param httpCallback callback类
   */
  public static void doGetAsync(
      @NotNull String url, @Nullable Headers headers, @NotNull HttpCallback httpCallback) {
    doGetAsync(url, headers, null, httpCallback);
  }
  /**
   * 执行异步http get请求
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @param httpCallback callback类
   */
  public static void doGetAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable ClientConfig clientConfig,
      @NotNull HttpCallback httpCallback) {
    BaseHttpExecutor.executeGetAsync(url, headers, clientConfig, httpCallback);
  }

  /**
   * 执行异步http get请求 (okhttp3 原生异步)
   *
   * @param url 请求地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @param callback callback类
   */
  public static void doGetAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable ClientConfig clientConfig,
      @NotNull Callback callback) {
    BaseHttpExecutor.executeGetAsync(url, headers, clientConfig, callback);
  }
}
