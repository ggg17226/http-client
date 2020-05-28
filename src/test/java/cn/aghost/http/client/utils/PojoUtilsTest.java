package cn.aghost.http.client.utils;

import cn.aghost.http.client.Get;
import cn.aghost.http.client.TestObject;
import cn.aghost.http.client.exceptions.ClientNotFoundException;
import cn.aghost.http.client.object.EncodePayload;
import cn.aghost.http.client.object.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Slf4j
class PojoUtilsTest {

  @Test
  void checkPojoAnnotation() {
    boolean b = PojoUtils.checkPojoAnnotation(JSON.class);
    assert b;
    log.info("{}", b);
  }

  @Test
  void doEncode() throws IllegalAccessException, InvocationTargetException {
    EncodePayload encodePayload = PojoUtils.doEncode(new TestObject());
    log.info(JSON.toJSONString(encodePayload));
  }

  @Test
  void doDecode()
      throws IOException, IllegalAccessException, InvocationTargetException,
          ClientNotFoundException {
    HttpResponse httpResponse = Get.doGet("https://file.aghost.cn/mmmmyipaddr.php");
    TestObject testObject = PojoUtils.doDecode(TestObject.class, httpResponse);
    log.info(JSON.toJSONString(testObject));
    JSONObject jsonObject = PojoUtils.doDecode(JSONObject.class, httpResponse);
    log.info(JSON.toJSONString(jsonObject));
  }
}
