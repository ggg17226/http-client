package cn.aghost.http.client;

import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.utils.BaseHttpExecutor;
import cn.aghost.http.client.utils.HttpClientUtil;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class Get {

  /**
   * 执行http get请求
   *
   * @param url 请求地址
   * @return 返回值
   * @throws IOException
   */
  public static HttpResponse doGet(@NotNull String url) throws IOException {
    return doGet(url, null, null);
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
    return doGet(url, headers, null);
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

    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder = BaseHttpExecutor.buildBaseReq().url(url).get();
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();

    try (Response rsp = client.newCall(req).execute()) {
      return new HttpResponse(rsp);
    }
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
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder = BaseHttpExecutor.buildBaseReq().url(url).get();
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    BaseHttpExecutor.executeAsync(httpCallback, client, req);
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
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder = BaseHttpExecutor.buildBaseReq().url(url).get();
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    BaseHttpExecutor.executeAsync(callback, client, req);
  }
}
