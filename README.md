# http-client

## 使用说明
引入maven依赖
```xml
<dependency>
    <groupId>cn.aghost</groupId>
    <artifactId>http-client</artifactId>
    <version>1.0.6</version>
</dependency>
```
使用示例(High Level)  
```java
    @Data
    @HttpCodec
    public class TestObject {
      private String addr;
    }


    TestObject testObject =
        Get.doGet("https://file.aghost.cn/mmmmyipaddr.php?id=1", "tag", TestObject.class);

    TestObject testObject =
        Post.doPost(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            new TestObject(),
            TestObject.class);

    TestObject testObject =
        Put.doPut(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            new TestObject(),
            TestObject.class);

    TestObject testObject =
        Delete.doDelete(
            "https://file.aghost.cn/mmmmyipaddr.php?id=1",
            "tag",
            new TestObject(),
            TestObject.class);

```


使用示例(Low Level)  
```java
    //get请求
    HttpResponse httpResponse = Get.doGet("https://file.aghost.cn/mmmmyipaddr.php");
    log.info(JSON.toJSONString(httpResponse));

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
            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });

    //post请求
    HttpResponse httpResponse = Post.doPost("https://file.aghost.cn/mmmmyipaddr.php", null, null);

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
            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });


    //put请求
    HttpResponse httpResponse = Put.doPut("https://file.aghost.cn/mmmmyipaddr.php", null, null);

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
            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });


    //delete请求
    HttpResponse httpResponse =
        Delete.doDelete("https://file.aghost.cn/mmmmyipaddr.php", null, null);

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
            log.info(JSON.toJSONString(response));
            assert httpResponse.getContentType().equals("application/json");
          }
        });
```