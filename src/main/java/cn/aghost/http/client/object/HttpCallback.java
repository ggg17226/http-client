package cn.aghost.http.client.object;

import okhttp3.Call;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/** http异步请求callback */
public interface HttpCallback {

  void onFailure(@NotNull Call call, @NotNull IOException e);

  void onSuccess(@NotNull Call call, @NotNull HttpResponse response);
}
