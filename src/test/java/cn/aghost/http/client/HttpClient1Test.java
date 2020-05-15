package cn.aghost.http.client;

import cn.aghost.http.client.object.HttpCallback;
import cn.aghost.http.client.object.HttpResponse;
import cn.aghost.http.client.utils.BaseHttpExecutor;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;

@Slf4j
class HttpClient1Test {

  @Test
  void doGet() throws IOException, InterruptedException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse = Get.doGet("https://file.aghost.cn/mmmmyipaddr.php");
//    log.info(JSON.toJSONString(httpResponse));
    assert httpResponse.getContentType().equals("application/json");
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
//            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void doPost() throws IOException, InterruptedException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse = Post.doPost("https://file.aghost.cn/mmmmyipaddr.php", null, null);
    assert httpResponse.getContentType().equals("application/json");
    Post.doPostAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        null,
        null,
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
//            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });
//    log.info(JSON.toJSONString(httpResponse));
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void doPut() throws IOException, InterruptedException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse = Put.doPut("https://file.aghost.cn/mmmmyipaddr.php", null, null);
    assert httpResponse.getContentType().equals("application/json");
    Put.doPutAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        null,
        null,
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
//            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });
//    log.info(JSON.toJSONString(httpResponse));
    sleep(1000);
  }

  @org.junit.jupiter.api.Test
  void doDelete() throws IOException, InterruptedException {
    BaseHttpExecutor.setLogFlag(true);
    HttpResponse httpResponse =
        Delete.doDelete("https://file.aghost.cn/mmmmyipaddr.php", null, null);
    assert httpResponse.getContentType().equals("application/json");
    Delete.doDeleteAsync(
        "https://file.aghost.cn/mmmmyipaddr.php",
        null,
        null,
        new HttpCallback() {
          @Override
          public void onFailure(@NotNull Call call, @NotNull IOException e) {
            log.error(ExceptionUtils.getStackTrace(e));
            assert e == null;
          }

          @Override
          public void onSuccess(@NotNull Call call, @NotNull HttpResponse response) {
//            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });
//    log.info(JSON.toJSONString(httpResponse));
    sleep(1000);
  }
}
