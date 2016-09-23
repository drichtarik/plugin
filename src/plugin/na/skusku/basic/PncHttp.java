package plugin.na.skusku.basic;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PncHttp {

	public String post(String body, String url) {

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity(body);
			request.addHeader("content-type", "application/json");
			request.addHeader("accept", "application/json");
			request.setEntity(params);
			HttpResponse result = httpClient.execute(request);

			String json = EntityUtils.toString(result.getEntity(), "UTF-8");

			return json;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
