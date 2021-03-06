package cn.aghost.http.client;

import cn.aghost.http.client.exceptions.ClientNotFoundException;
import cn.aghost.http.client.object.ClientConfig;
import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.object.Mimes;
import cn.aghost.http.client.utils.BaseHttpExecutor;
import cn.aghost.http.client.utils.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.Proxy;

import static java.lang.Thread.sleep;

@Slf4j
class HttpClient1Test {

  @Test
  void doGet()
      throws IOException, InterruptedException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    BaseHttpExecutor.setLogFlag(true);
    BaseHttpExecutor.setAutoDecodeBody(true);
    HttpResponse httpResponse = Get.doGet("https://file.aghost.cn/mmmmyipaddr.php?id=1");
    assert httpResponse.getContentType().equals("application/json");
    ClientConfig clientConfig = new ClientConfig();
    clientConfig.setSkipSslCheck(true);
    TestObject testObject =
        Get.doGet(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            null,
            clientConfig,
            TestObject.class);
    log.info(JSON.toJSONString(testObject));
    Get.doGetAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
            assert httpResponse.getContentType().equals("application/json");
          }
        });
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void doPost()
      throws IOException, InterruptedException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse =
        Post.doPost("https://file.aghost.cn/mmmmyipaddr.php", "{}".getBytes(), Mimes.JSON_UTF8);
    assert httpResponse.getContentType().equals("application/json");
    TestObject testObject =
        Post.doPost(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            new TestObject(),
            TestObject.class);
    log.info(JSON.toJSONString(testObject));
    Post.doPostAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        "{}".getBytes(),
        Mimes.JSON_UTF8,
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
            assert httpResponse.getContentType().equals("application/json");
          }
        });
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void doPut()
      throws IOException, InterruptedException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse =
        Put.doPut("https://file.aghost.cn/mmmmyipaddr.php", "{}".getBytes(), Mimes.JSON_UTF8);
    assert httpResponse.getContentType().equals("application/json");
    TestObject testObject =
        Put.doPut(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            new TestObject(),
            TestObject.class);
    log.info(JSON.toJSONString(testObject));
    Put.doPutAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        "{}".getBytes(),
        Mimes.JSON_UTF8,
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
            assert httpResponse.getContentType().equals("application/json");
          }
        });
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void doDelete()
      throws IOException, InterruptedException, InvocationTargetException, IllegalAccessException,
          ClientNotFoundException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse =
        Delete.doDelete("https://file.aghost.cn/mmmmyipaddr.php", "{}".getBytes(), Mimes.JSON_UTF8);
    assert httpResponse.getContentType().equals("application/json");

    TestObject testObject =
        Delete.doDelete(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            new TestObject(),
            TestObject.class);
    log.info(JSON.toJSONString(testObject));

    Delete.doDeleteAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        "{}".getBytes(),
        Mimes.JSON_UTF8,
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
            assert httpResponse.getContentType().equals("application/json");
          }
        });
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void ddBuildClientNameTest() {
    InetSocketAddress inetSocketAddress = new InetSocketAddress("1.1.1.1", 1234);
    log.info(JSON.toJSONString(inetSocketAddress));
    Proxy proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddress);
    ClientConfig clientConfig =
        new ClientConfig(
            null, 1111133211, 666664321, 533245555, 444123444, 333213333, false, true, proxy);
    log.info(HttpClientUtil.buildClientName(clientConfig));
  }
}
