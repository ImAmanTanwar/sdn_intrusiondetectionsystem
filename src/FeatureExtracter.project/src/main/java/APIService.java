
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;

public class APIService {

    static void sendReq(String address,String port,String data) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        StringEntity params = new StringEntity("details="+data);
        HttpPost httpPost = new HttpPost("http://"+address+":"+port+"/api/send/");
        httpPost.addHeader("content-type","application/x-www-form-urlencoded");
        httpPost.setEntity(params);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        try {
            if(response.getStatusLine().getStatusCode()==200) {

            }
        }
        finally {
            response.close();
        }
    }
}
