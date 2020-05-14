package cn.aghost.http.client.utils;

import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

/** http执行器 */
public class BaseHttpExecutor {
  /**
   * 构建builder
   *
   * @return builder
   */
  public static Request.Builder buildBaseReq() {
    return new Request.Builder().cacheControl(new CacheControl.Builder().noCache().build());
  }

  /**
   * 执行同步请求
   *
   * @param method http method
   * @param url url地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 请求体的消息类型
   * @param clientConfig client配置
   * @return 请求返回值
   * @throws IOException
   */
  public static HttpResponse executeWithBody(
      @NotNull String method,
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig)
      throws IOException {
    if (contentType == null) {
      contentType = MediaType.parse("text/plan; charset=utf-8");
    }
    if (body == null) {
      body = "".getBytes();
    }
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder =
        buildBaseReq().url(url).method(method, RequestBody.create(contentType, body));
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    try (Response rsp = client.newCall(req).execute()) {
      return new HttpResponse(rsp);
    }
  }

  /**
   * 执行异步请求
   *
   * @param method http method
   * @param url url地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 请求体的消息类型
   * @param clientConfig client配置
   * @param httpCallback 异步callback
   */
  public static void executeWithBodyAsync(
      @NotNull String method,
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig,
      @NotNull HttpCallback httpCallback) {
    if (contentType == null) {
      contentType = MediaType.parse("text/plan; charset=utf-8");
    }
    if (body == null) {
      body = "".getBytes();
    }
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder =
        buildBaseReq().url(url).method(method, RequestBody.create(contentType, body));
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    executeAsync(httpCallback, client, req);
  }

  /**
   * 执行异步请求
   *
   * @param method http method
   * @param url url地址
   * @param headers 请求头
   * @param body 请求体
   * @param contentType 请求体的消息类型
   * @param clientConfig client配置
   * @param callback 异步callback
   */
  public static void executeWithBodyAsync(
      @NotNull String method,
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable byte[] body,
      @Nullable MediaType contentType,
      @Nullable ClientConfig clientConfig,
      @NotNull Callback callback) {
    if (contentType == null) {
      contentType = MediaType.parse("text/plan; charset=utf-8");
    }
    if (body == null) {
      body = "".getBytes();
    }
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder =
        buildBaseReq().url(url).method(method, RequestBody.create(contentType, body));
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    executeAsync(callback, client, req);
  }

  /**
   * 执行异步请求
   *
   * @param httpCallback 异步callback
   * @param client http client
   * @param req http request
   */
  public static void executeAsync(
      @NotNull HttpCallback httpCallback, @NotNull OkHttpClient client, @NotNull Request req) {
    client
        .newCall(req)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(@NotNull Call call, @NotNull IOException e) {
                httpCallback.onFailure(call, e);
              }

              @Override
              public void onResponse(@NotNull Call call, @NotNull Response response)
                  throws IOException {
                httpCallback.onSuccess(call, new HttpResponse(response));
              }
            });
  }

  /**
   * 执行异步请求（okhttp3原生callback）
   *
   * @param callback 异步callback
   * @param client http client
   * @param req http request
   */
  public static void executeAsync(
      @NotNull Callback callback, @NotNull OkHttpClient client, @NotNull Request req) {
    client.newCall(req).enqueue(callback);
  }
}
