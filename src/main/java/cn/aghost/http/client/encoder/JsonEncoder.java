package cn.aghost.http.client.encoder;

import cn.aghost.http.client.object.EncodePayload;
import cn.aghost.http.client.object.Mimes;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonEncoder implements BaseEncoder<Object> {

  public static EncodePayload encode(Object body) {
    EncodePayload encodePayload = new EncodePayload();
    encodePayload.setBody(
        JSON.toJSONString(
                body,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNonStringKeyAsString
                // ,SerializerFeature.WriteDateUseDateFormat
                )
            .getBytes());
    encodePayload.setContentType(Mimes.JSON_UTF8);
    return encodePayload;
  }
}
