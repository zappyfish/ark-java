package io.ark.core.network.request;

import io.ark.core.network.response.v1.Peer;
import io.ark.core.util.ResponseUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.Map;
import java.util.concurrent.Callable;

public class PostRequest extends Request implements Callable<String> {

  private Object payload;

  public PostRequest(Peer peer, Map<String, String> headers, String endpoint, Object payload) {
    super(peer, headers, endpoint);
    try {
      conn.setRequestMethod("POST");
    } catch (ProtocolException e) {
      // This will never happen.
    }
    conn.setDoOutput(true);
    this.payload = payload;
  }

  @Override
  public String call() throws Exception {
    doPost();
    conn.getResponseCode();
    return getResponse();
  }

  private void doPost() throws IOException {
    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
    wr.writeBytes(ResponseUtils.getObjectAsJson(payload));
    wr.flush();
    wr.close();
  }

}