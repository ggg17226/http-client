package cn.aghost.http.client.utils;

import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.object.LogLevelEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.UUID;

/** http执行器 */
@Slf4j
public class BaseHttpExecutor {

  private static volatile boolean logFlag = false;
  private static volatile LogLevelEnum logLevel = LogLevelEnum.info;
  private static volatile boolean autoDecodeBody = false;

  public static boolean isAutoDecodeBody() {
    return autoDecodeBody;
  }

  public static synchronized void setAutoDecodeBody(boolean autoDecodeBody) {
    BaseHttpExecutor.autoDecodeBody = autoDecodeBody;
  }

  public static LogLevelEnum getLogLevel() {
    return logLevel;
  }

  public static synchronized void setLogLevel(LogLevelEnum logLevel) {
    BaseHttpExecutor.logLevel = logLevel;
  }

  public static boolean getLogFlag() {
    return logFlag;
  }

  public static synchronized void setLogFlag(boolean logFlag) {
    BaseHttpExecutor.logFlag = logFlag;
  }

  private static void execLog(String format, Object... arg) {
    switch (logLevel) {
      case trace:
        log.trace(format, arg);
        break;
      case debug:
        log.debug(format, arg);
        break;
      case info:
        log.info(format, arg);
        break;
      case warn:
        log.warn(format, arg);
        break;
      case error:
        log.error(format, arg);
        break;
    }
  }

  private static void execLog(JSON data) {
    String logData =
        JSON.toJSONString(
            data,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNonStringKeyAsString,
            SerializerFeature.WriteDateUseDateFormat);
    switch (logLevel) {
      case trace:
        log.trace(logData);
        break;
      case debug:
        log.debug(logData);
        break;
      case info:
        log.info(logData);
        break;
      case warn:
        log.warn(logData);
        break;
      case error:
        log.error(logData);
        break;
    }
  }

  /**
   * 构建builder
   *
   * @return builder
   */
  public static Request.Builder buildBaseReq() {
    return new Request.Builder().cacheControl(new CacheControl.Builder().noCache().build());
  }

  /**
   * 执行同步get请求
   *
   * @param url url地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @return 请求返回值
   * @throws IOException
   */
  public static HttpResponse executeGet(
      @NotNull String url, @Nullable Headers headers, @Nullable ClientConfig clientConfig)
      throws IOException {
    String reqId = null;
    boolean logFlag = BaseHttpExecutor.logFlag;
    if (logFlag) {
      reqId = UUID.randomUUID().toString();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("method", "GET");
      jsonObject.put("reqId", reqId);
      jsonObject.put("url", url);
      jsonObject.put("query", HttpDataUtils.decodeQueryString(url));
      jsonObject.put("headers", headers);
      jsonObject.put("clientInfo", clientConfig);
      jsonObject.put("state", "preExec");
      execLog(jsonObject);
    }
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder = buildBaseReq().url(url).get();
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    try (Response rsp = client.newCall(req).execute()) {
      HttpResponse response = new HttpResponse(rsp);
      if (logFlag) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqId", reqId);
        jsonObject.put("resp", response);
        jsonObject.put("state", "execResult");
        execLog(jsonObject);
      }
      return response;
    } catch (IOException e) {
      if (logFlag) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqId", reqId);
        jsonObject.put("ex", e);
        jsonObject.put("state", "execEx");
        execLog(jsonObject);
      }
      throw e;
    }
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

    String reqId = null;
    boolean logFlag = BaseHttpExecutor.logFlag;
    if (logFlag) {
      reqId = UUID.randomUUID().toString();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("reqId", reqId);
      jsonObject.put("method", method);
      jsonObject.put("url", url);
      jsonObject.put("query", HttpDataUtils.decodeQueryString(url));
      jsonObject.put("headers", headers);
      jsonObject.put("clientInfo", clientConfig);
      jsonObject.put("body", body);
      jsonObject.put("contentType", contentType);
      jsonObject.put("state", "preExec");
      execLog(jsonObject);
    }

    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder =
        buildBaseReq().url(url).method(method, RequestBody.create(contentType, body));
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    try (Response rsp = client.newCall(req).execute()) {
      HttpResponse response = new HttpResponse(rsp);
      if (logFlag) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqId", reqId);
        jsonObject.put("resp", response);
        jsonObject.put("state", "execResult");
        execLog(jsonObject);
      }
      return response;
    } catch (IOException e) {
      if (logFlag) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqId", reqId);
        jsonObject.put("ex", e);
        jsonObject.put("state", "execEx");
        execLog(jsonObject);
      }
      throw e;
    }
  }
  /**
   * 执行异步Get请求
   *
   * @param url url地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @param httpCallback 异步callback
   */
  public static void executeGetAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable ClientConfig clientConfig,
      @NotNull HttpCallback httpCallback) {

    String reqId = null;
    boolean logFlag = BaseHttpExecutor.logFlag;
    if (logFlag) {
      reqId = UUID.randomUUID().toString();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("reqId", reqId);
      jsonObject.put("method", "GET");
      jsonObject.put("url", url);
      jsonObject.put("query", HttpDataUtils.decodeQueryString(url));
      jsonObject.put("headers", headers);
      jsonObject.put("clientInfo", clientConfig);
      jsonObject.put("state", "preExec");
      execLog(jsonObject);
    }

    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder = buildBaseReq().url(url).get();
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    executeAsync(httpCallback, client, req, logFlag, reqId);
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

    String reqId = null;
    boolean logFlag = BaseHttpExecutor.logFlag;
    if (logFlag) {
      reqId = UUID.randomUUID().toString();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("reqId", reqId);
      jsonObject.put("method", method);
      jsonObject.put("url", url);
      jsonObject.put("query", HttpDataUtils.decodeQueryString(url));
      jsonObject.put("headers", headers);
      jsonObject.put("clientInfo", clientConfig);
      jsonObject.put("body", body);
      jsonObject.put("contentType", contentType);
      jsonObject.put("state", "preExec");
      execLog(jsonObject);
    }

    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder =
        buildBaseReq().url(url).method(method, RequestBody.create(contentType, body));
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    executeAsync(httpCallback, client, req, logFlag, reqId);
  }
  /**
   * 执行异步Get请求
   *
   * @param url url地址
   * @param headers 请求头
   * @param clientConfig client配置
   * @param callback 异步callback
   */
  public static void executeGetAsync(
      @NotNull String url,
      @Nullable Headers headers,
      @Nullable ClientConfig clientConfig,
      @NotNull Callback callback) {
    String reqId = null;
    boolean logFlag = BaseHttpExecutor.logFlag;
    if (logFlag) {
      reqId = UUID.randomUUID().toString();
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("reqId", reqId);
      jsonObject.put("method", "GET");
      jsonObject.put("url", url);
      jsonObject.put("query", HttpDataUtils.decodeQueryString(url));
      jsonObject.put("headers", headers);
      jsonObject.put("clientInfo", clientConfig);
      jsonObject.put("state", "preExec");
      execLog(jsonObject);
    }
    OkHttpClient client = HttpClientUtil.getClient(clientConfig);
    Request.Builder builder = buildBaseReq().url(url).get();
    if (headers != null && headers.size() > 0) {
      builder.headers(headers);
    }
    Request req = builder.build();
    executeAsync(callback, client, req);
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
      @NotNull HttpCallback httpCallback,
      @NotNull OkHttpClient client,
      @NotNull Request req,
      boolean logFlag,
      @Nullable String reqId) {
    client
        .newCall(req)
        .enqueue(
            new Callback() {
              @Override
              public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (logFlag) {
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("reqId", reqId);
                  jsonObject.put("ex", e);
                  jsonObject.put("state", "execEx");
                  execLog(jsonObject);
                }

                httpCallback.onFailure(call, e);
              }

              @Override
              public void onResponse(@NotNull Call call, @NotNull Response response)
                  throws IOException {
                HttpResponse rsp = new HttpResponse(response);
                if (logFlag) {
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("reqId", reqId);
                  jsonObject.put("resp", rsp);
                  jsonObject.put("state", "execResult");
                  execLog(jsonObject);
                }
                httpCallback.onSuccess(call, rsp);
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
