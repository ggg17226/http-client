package cn.aghost.http.client.utils;

import cn.aghost.http.client.annotation.TestAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.util.Set;

@Slf4j
public class MyAnnotationUtils {

  public static void searchAnnotation() {
    Reflections reflections =
        new Reflections("", new SubTypesScanner(false), new TypeAnnotationsScanner());
    Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(TestAnnotation.class);
    typesAnnotatedWith.forEach(
        c -> {
          log.info(c.toString());
        });
  }
}
