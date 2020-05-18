package cn.aghost.http.client.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

@Slf4j
class HttpDataUtilsTest {

  @Test
  void doDecodeQueryStringTest() {
    String url = "http://example.com/test?id=1&data=%E6%B5%8B%E8%AF%95&id=2&id=";
    Map<String, List<String>> stringStringMap = HttpDataUtils.decodeQueryString(url);
    log.info(JSON.toJSONString(stringStringMap));
    assert stringStringMap.size() == 2;
    assert stringStringMap.containsKey("id")
        && stringStringMap.get("id").size() == 2
        && stringStringMap.get("id").contains("1")
        && stringStringMap.get("id").contains("2");
    assert stringStringMap.containsKey("data")
        && stringStringMap.get("data").size() == 1
        && stringStringMap.get("data").get(0).equals("测试");
  }
}
