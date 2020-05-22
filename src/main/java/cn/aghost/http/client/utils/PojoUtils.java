package cn.aghost.http.client.utils;

import cn.aghost.http.client.decoder.BaseDecoder;
import cn.aghost.http.client.decoder.JsonDecoder;
import cn.aghost.http.client.encoder.BaseEncoder;
import cn.aghost.http.client.encoder.JsonEncoder;
import cn.aghost.http.client.object.EncodePayload;
import cn.aghost.http.client.object.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/** pojo类的编解码工具类 */
@Slf4j
public class PojoUtils {
  /** 用于存储具体pojo类对应的编解码器的map */
  private static Map<Class<?>, CodecAnnotationUtils.CodecPayload> codecMap;
  private static Class<? extends BaseEncoder> DEFAULT_ENCODER = JsonEncoder.class;
  private static Class<? extends BaseDecoder> DEFAULT_DECODER = JsonDecoder.class;

  static {
    try {
      codecMap = CodecAnnotationUtils.searchAnnotation();
    } catch (Exception e) {
      log.error("init codec fail,{}", ExceptionUtils.getStackTrace(e));
      System.exit(111);
    }
  }

  //  public static synchronized void setDefaultEncoder(Class<? extends BaseEncoder> defaultEncoder)
  // {
  //    DEFAULT_ENCODER = defaultEncoder;
  //  }
  //
  //  public static synchronized void setDefaultDecoder(Class<? extends BaseDecoder> defaultDecoder)
  // {
  //    DEFAULT_DECODER = defaultDecoder;
  //  }

  public static synchronized void addObjectMapping(
      Class<?> objectClazz, CodecAnnotationUtils.CodecPayload codec) {
    codecMap.put(objectClazz, codec);
  }

  /**
   * 检查pojo类是否注册了编解码器
   *
   * @param clazz 类的引用
   * @return
   */
  public static boolean checkPojoAnnotation(Class<?> clazz) {
    return codecMap.containsKey(clazz);
  }

  /**
   * 执行编码
   *
   * @param data 数据pojo类
   * @return 编码后的字节流
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static EncodePayload doEncode(Object data)
      throws IllegalAccessException, InvocationTargetException {
    Class<? extends BaseEncoder> encoder = chooseEncoder(data);
    Method encoderMethod = null;
    for (Method method : encoder.getMethods()) {
      if (method.getName().equals("encode")) {
        encoderMethod = method;
        break;
      }
    }
    if (encoderMethod == null) {
      throw new IllegalAccessException();
    }
    EncodePayload encode = (EncodePayload) encoderMethod.invoke(null, data);
    return encode;
  }

  /**
   * 执行解码
   *
   * @param clazz 返回类型
   * @param resp http response
   * @param <T> 返回值泛型
   * @return 解码后的实体类
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   */
  public static <T> T doDecode(Class<T> clazz, HttpResponse resp)
      throws IllegalAccessException, InvocationTargetException {
    Class<? extends BaseDecoder> decoder = chooseDecoder(clazz);
    Method decodeMethod = null;
    for (Method method : decoder.getMethods()) {
      if (method.getName().equals("decode")) {
        decodeMethod = method;
        break;
      }
    }
    if (decodeMethod == null) {
      throw new IllegalAccessException();
    }
    T result = (T) decodeMethod.invoke(null, clazz, resp);
    return result;
  }

  private static Class<? extends BaseEncoder> chooseEncoder(Object data) {
    if (!checkPojoAnnotation(data.getClass())) {
      return DEFAULT_ENCODER;
    } else {
      return codecMap.get(data.getClass()).getEncoder();
    }
  }

  private static <T> Class<? extends BaseDecoder> chooseDecoder(Class<T> clazz) {
    if (!checkPojoAnnotation(clazz)) {
      return DEFAULT_DECODER;
    } else {
      return codecMap.get(clazz).getDecoder();
    }
  }
}
