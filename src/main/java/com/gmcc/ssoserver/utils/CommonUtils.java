package com.gmcc.ssoserver.utils;

import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	public static Integer generateSmsCode() {
		return new Random().nextInt(899999) + 100000;
	}

	/**
	 * Call the specific uri with GET method, please make sure to consume the HttpEntity by calling
	 * EntityUtils.consume().
	 * 
	 * @param uri
	 * @return
	 */
	public static String httpGet(String uri) {
		String result = "";
		HttpEntity httpEntity = null;
		HttpGet httpGet;

		try {
			httpGet = new HttpGet(uri);
		} catch (IllegalArgumentException iae) {
			LOGGER.error("Invalid URL {} for httpGet.", uri, iae);
			httpGet = null;
		}

		if (httpGet != null) {
			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse httpResponse = httpClient.execute(httpGet);) {
				LOGGER.info("Sending request with GET method to {}.", httpGet.getURI());
				httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);
				EntityUtils.consumeQuietly(httpEntity);
			} catch (Exception e) {
				LOGGER.error("Failed to send http get request to {}.", uri, e);
			}
		}

		return result;
	}
}
