package eu.sdk4ed.uom.td.analysis.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

// This class returns shas of versions of a GIT repository or 10 commit shas if repository 
// has not specify tags.
public class CommitShas 
{	
	private String shasMainEndpoint = "http://195.251.210.147:8989/api/sdk4ed/internal/git/tags-or-commits?url=";
	
	public ArrayList<String> getShas(String url, String username, String password) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException
	{
		System.out.println("---- SHAS from repository are processing ----");
		ArrayList<String> shas = new ArrayList<String>();
		String apiURL = null;
		
		// private repository add username and password
		if (password.length() > 2)
		{
			apiURL = shasMainEndpoint + url + "&username=" + username + "&password=" + password;
		}
		else
		{
			apiURL = shasMainEndpoint + url;
		}
				
		URL unstructuredURL = new URL(apiURL);
		URI uri = new URI(unstructuredURL.getProtocol(), unstructuredURL.getUserInfo(), IDN.toASCII(unstructuredURL.getHost()), unstructuredURL.getPort(), unstructuredURL.getPath(), unstructuredURL.getQuery(), unstructuredURL.getRef());
		String encodedURL = uri.toASCIIString(); 
		
		shas =  callAPI(encodedURL, true);
				
		//If not tags on repo
		if (shas.size() == 0)
		{
			encodedURL = encodedURL + "&commits=10";
			shas = callAPI(encodedURL, false);
		}
		
		System.out.println("---- SHAS from repository retured ----");
		return shas;
	}
	
	public ArrayList<String> callAPI(String url, Boolean flag)
	{
		ArrayList<String> shas = new ArrayList<String>();
		
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("Error while getting commit shas: " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			
			while ((output = br.readLine()) != null) 
			{
				// Change output JSON to a readable format
				output = "{\"shas\":" + output + "}";

				JSONObject obj = new JSONObject(output);

				JSONArray array = obj.getJSONArray("shas");
				
				int numOfTags = 0;
				
				for (int i = 0; i < array.length(); i++) 
				{
					String sha = array.getJSONObject(i).getString("sha");
					
					numOfTags++;

					if (array.getJSONObject(i).get("tagName").equals(null) && flag == true) 
					{
						return new ArrayList<String>();
					}
					
					shas.add(sha);
				}
				
				if (numOfTags < 3 && flag == true)
					return new ArrayList<String>();
			}

			httpClient.close();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return shas;
		
	}

}
