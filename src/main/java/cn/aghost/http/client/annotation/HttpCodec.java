package cn.aghost.http.client.annotation;

import cn.aghost.http.client.decoder.BaseDecoder;
import cn.aghost.http.client.decoder.JsonDecoder;
import cn.aghost.http.client.encoder.BaseEncoder;
import cn.aghost.http.client.encoder.JsonEncoder;

import java.lang.annotation.*;

/** 通过注解指定实体类的codec */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpCodec {
  /**
   * 指定实体类的decoder，不指定时默认返回json
   *
   * @return
   */
  Class<? extends BaseDecoder> decoder() default JsonDecoder.class;
  /**
   * 指定实体类的encoder，不指定时默认返回json解码
   *
   * @return
   */
  Class<? extends BaseEncoder> encoder() default JsonEncoder.class;
}
