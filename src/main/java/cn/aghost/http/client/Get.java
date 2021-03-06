package cn.aghost.http.client;

import cn.aghost.http.client.exceptions.ClientNotFoundException;
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
   * @param tag 请求tag，将覆盖client设置中的tag参数
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return 返回值
   * @throws IOException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public static <T> T doGet(@NotNull String url, @Nullable String tag, Class<T> clazz)
      throws IOException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    return doGet(url, tag, null, null, clazz);
  }
  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param tag 请求tag，将覆盖client设置中的tag参数
   * @param headers 请求头
   * @param clazz 返回值类型
   * @param <T> 泛型
   * @return 返回值
   * @throws IOException
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   */
  public static <T> T doGet(
      @NotNull String url, @Nullable String tag, @Nullable Headers headers, Class<T> clazz)
      throws IOException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    return doGet(url, tag, headers, null, clazz);
  }

  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @param tag 请求tag，将覆盖client设置中的tag参数
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
      @Nullable String tag,
      @Nullable Headers headers,
      @Nullable ClientConfig clientConfig,
      Class<T> clazz)
      throws IOException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    if (clientConfig == null) {
      clientConfig = new ClientConfig();
    }
    clientConfig.setTag(tag);
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
  public static HttpResponse doGet(@NotNull String url)
      throws IOException, ClientNotFoundException {
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
      throws IOException, ClientNotFoundException {
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
      throws IOException, ClientNotFoundException {
    return BaseHttpExecutor.executeGet(url, headers, clientConfig);
  }

  /**
   * 执行异步http get请求
   *
   * @param url 请求地址
   * @param httpCallback callback类
   */
  public static void doGetAsync(@NotNull String url, @NotNull HttpCallback httpCallback)
      throws ClientNotFoundException {
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
      @NotNull String url, @Nullable Headers headers, @NotNull HttpCallback httpCallback)
      throws ClientNotFoundException {
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
      @NotNull HttpCallback httpCallback)
      throws ClientNotFoundException {
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
      @NotNull Callback callback)
      throws ClientNotFoundException {
    BaseHttpExecutor.executeGetAsync(url, headers, clientConfig, callback);
  }
}
