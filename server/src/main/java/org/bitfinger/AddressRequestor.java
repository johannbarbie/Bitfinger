package org.bitfinger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.bitfinger.pojo.AddressResponse;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.jsonrpc4j.Base64;

public class AddressRequestor extends Thread {
	
	private String phone;
	private SocketIOServer server;
	
	public AddressRequestor(String phone, SocketIOServer server){
		this.phone = phone;
		this.server = server;
	}
	
	@Override
	public void run() {
		String address = null;
		HttpClient client = HttpClientBuilder.create().build();
		int limit = 0;
		while(address==null && limit < 4){
			HttpResponse response = null;
			try {
				HttpGet someHttpGet = new HttpGet(BitfingerServletConfig.svcPath);
				URI uri = new URIBuilder(someHttpGet.getURI())
					.addParameter("phone", phone)
					.build();
				String cred = Base64.encodeBytes((BitfingerServletConfig.svcUser + ":" + BitfingerServletConfig.svcPw).getBytes());
				HttpRequestBase request = new HttpGet(uri);
				request.setHeader("Authoriziation", "B4s1c "+cred);
				response = client.execute(request);
			} catch (IOException| URISyntaxException e) {
				e.printStackTrace();
			}
			int status = response.getStatusLine().getStatusCode();
			if (status == 200){
				AddressResponse rsp;
				try {
					rsp = new ObjectMapper().readValue(response.getEntity().getContent(),AddressResponse.class);
					address = rsp.getAddress();
					server.getRoomOperations(phone).sendMessage(address);
				} catch (IllegalStateException | IOException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(5000);
				limit++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
