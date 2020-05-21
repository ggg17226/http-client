package cn.aghost.http.client.utils;

import cn.aghost.http.client.annotation.HttpCodec;
import cn.aghost.http.client.decoder.BaseDecoder;
import cn.aghost.http.client.decoder.JsonDecoder;
import cn.aghost.http.client.encoder.BaseEncoder;
import cn.aghost.http.client.encoder.JsonEncoder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javassist.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** codec 工具类 */
@Slf4j
public class CodecAnnotationUtils {

  private static volatile boolean reflectionsLogCovered = false;

  private static synchronized void coverReflectionsLog()
      throws NotFoundException, CannotCompileException {
    if (reflectionsLogCovered) return;
    // 关闭烦人的找不到类的warn
    ClassPool pool = new ClassPool(true);
    CtClass ctClass = pool.getCtClass("org.reflections.util.Utils");
    CtMethod m = ctClass.getDeclaredMethod("findLogger");
    m.setBody("return null;");
    ctClass.toClass();
    ctClass.detach();
    reflectionsLogCovered = true;
  }

  /**
   * 扫描并构造默认编解码器
   *
   * @return 编解码器map
   */
  public static Map<Class<?>, CodecPayload> searchAnnotation()
      throws NoSuchFieldException, IllegalAccessException, NotFoundException,
          CannotCompileException {
    coverReflectionsLog();
    Map<Class<?>, CodecPayload> codecMap = new ConcurrentHashMap<>();
    ClassLoader contextClassLoader = ClasspathHelper.contextClassLoader();
    ClassLoader staticClassLoader = ClasspathHelper.staticClassLoader();
    Class<ClassLoader> classLoaderClass = ClassLoader.class;
    Field packages = classLoaderClass.getDeclaredField("packages");
    packages.setAccessible(true);
    HashMap<String, Package> packageContext =
        (HashMap<String, Package>) packages.get(contextClassLoader);
    HashMap<String, Package> packageStatic =
        (HashMap<String, Package>) packages.get(staticClassLoader);
    List<String> urlStrList = new ArrayList<>();
    for (String s : packageContext.keySet()) {
      if (s.startsWith("org.junit") || s.startsWith("junit") || s.startsWith("retrofit2")) {
        continue;
      }
      if (!urlStrList.contains(s)) {
        urlStrList.add(s);
      }
    }
    for (String k : packageStatic.keySet()) {
      if (!urlStrList.contains(k)) {
        urlStrList.add(k);
      }
    }
    List<URL> urls = new ArrayList<>();
    for (String u : urlStrList) {
      urls.addAll(ClasspathHelper.forPackage(u));
    }

    ConfigurationBuilder configurationBuilder =
        new ConfigurationBuilder()
            .setUrls(urls)
            .addScanners(new SubTypesScanner(true), new TypeAnnotationsScanner());
    Reflections reflections = new Reflections(configurationBuilder);

    Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(HttpCodec.class);
    for (Class<?> c : typesAnnotatedWith) {
      HttpCodec annotation = c.getAnnotation(HttpCodec.class);
      CodecPayload codecPayload = new CodecPayload();
      codecPayload.setDecoder(annotation.decoder());
      codecPayload.setEncoder(annotation.encoder());
      codecMap.put(c, codecPayload);
    }
    CodecPayload jsonCodec = new CodecPayload();
    jsonCodec.setEncoder(JsonEncoder.class);
    jsonCodec.setDecoder(JsonDecoder.class);
    codecMap.put(JSON.class, jsonCodec);
    codecMap.put(JSONArray.class, jsonCodec);
    codecMap.put(JSONObject.class, jsonCodec);
    return codecMap;
  }

  /** 存放 编解码器 的class引用 */
  @Data
  public static class CodecPayload {
    private Class<? extends BaseDecoder> decoder;
    private Class<? extends BaseEncoder> encoder;
  }
}
