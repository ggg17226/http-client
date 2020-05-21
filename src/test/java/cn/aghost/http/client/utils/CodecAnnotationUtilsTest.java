package cn.aghost.http.client.utils;

import cn.aghost.http.client.TestObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;

@Slf4j
class CodecAnnotationUtilsTest {

  @Test
  void searchAnnotation() throws Exception {
    Map<Class<?>, CodecAnnotationUtils.CodecPayload> codecMap =
        CodecAnnotationUtils.searchAnnotation();
    log.info("check codec:{}", codecMap.containsKey(TestObject.class));
  }
}
