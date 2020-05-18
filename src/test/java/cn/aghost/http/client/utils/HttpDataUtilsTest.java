package cn.aghost.http.client.utils;

import cn.aghost.http.client.object.SchemesEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
class HttpDataUtilsTest {

  @Test
  void doDecodeQueryStringTest() {
    String url = "https://file.aghost.cn/mmmmyipaddr.php?data=%E6%B5%8B%E8%AF%95&id=1&id=2&id=";
    Map<String, List<String>> stringStringMap = HttpDataUtils.decodeQueryString(url);
    log.info(JSON.toJSONString(stringStringMap));
    assert stringStringMap.size() == 2;
    assert stringStringMap.containsKey("id")
        && stringStringMap.get("id").size() == 3
        && stringStringMap.get("id").contains("1")
        && stringStringMap.get("id").contains("2");
    assert stringStringMap.containsKey("data")
        && stringStringMap.get("data").size() == 1
        && stringStringMap.get("data").get(0).equals("测试");
  }

  @Test
  void doBuildUrlTest() {
    Map<String, List<String>> q = new TreeMap<>();
    List<String> idList = new ArrayList<>();
    idList.add("1");
    idList.add("2");
    idList.add("");
    List<String> dataList = new ArrayList<>();
    dataList.add("测试");
    q.put("id", idList);
    q.put("data", dataList);
    String s = HttpDataUtils.buildUrl(SchemesEnum.https, "file.aghost.cn", "mmmmyipaddr.php", q);
    log.info(s);
    assert s.equals("https://file.aghost.cn/mmmmyipaddr.php?data=%E6%B5%8B%E8%AF%95&id=1&id=2&id=");
  }
}
