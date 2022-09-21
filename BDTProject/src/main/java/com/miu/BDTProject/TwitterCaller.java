package com.miu.BDTProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.BDTProject.model.Tweet;
import com.miu.BDTProject.model.TweetData;

@Service
public class TwitterCaller {

	@Value("${bearer-token}")
	private String bearerToken;

	@Autowired
	private StreamBridge streambridge;

	@Autowired
	private ObjectMapper objectMapper;

	public void connectTwitter() {
		if (null != bearerToken) {
			try {
				connectStream(bearerToken);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out
					.println("There was a problem getting your bearer token. Please make sure you set the BEARER_TOKEN environment variable");
		}
	}

	/*
	 * This method calls the sample stream endpoint and streams Tweets from it
	 */
	private void connectStream(String bearerToken) throws IOException,
			URISyntaxException {
		Tweet tweet;
		HttpClient httpClient = HttpClients
				.custom()
				.setDefaultRequestConfig(
						RequestConfig.custom()
								.setCookieSpec(CookieSpecs.STANDARD).build())
				.build();

		URIBuilder uriBuilder = new URIBuilder(
				"https://api.twitter.com/2/tweets/sample/stream");

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization",
				String.format("Bearer %s", bearerToken));

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					(entity.getContent())));
			String line = reader.readLine();
			while (line != null) {
				try {
					tweet = objectMapper.readValue(line, Tweet.class);
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("error");
					tweet = null;
				}
				if (tweet != null) {
					System.out.println(tweet.getData());
					streambridge.send("tweetProducer-out-0", tweet.toString());
				}
				line = reader.readLine();
			}
		}
	}
}
