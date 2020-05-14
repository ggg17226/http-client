package cn.aghost.http.client.object;

import okhttp3.MediaType;

public class Mimes {
  public static final MediaType JSON_UTF8 = MediaType.parse("application/json; charset=utf-8");
  public static final MediaType XML_UTF8 = MediaType.parse("application/xml; charset=utf-8");
  public static final MediaType PLAIN_TEXT_UTF8 = MediaType.parse("text/plain; charset=utf-8");
  public static final MediaType HTML_UTF8 = MediaType.parse("text/html; charset=utf-8");
}
