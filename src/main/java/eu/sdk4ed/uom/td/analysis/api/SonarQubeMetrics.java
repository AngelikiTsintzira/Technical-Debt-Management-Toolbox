package eu.sdk4ed.uom.td.analysis.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;

public class SonarQubeMetrics 
{
	private String server;

	ConcurrentHashMap<String, ArtifactMetrics> artifacts = new ConcurrentHashMap<String, ArtifactMetrics>();

	public SonarQubeMetrics(String server) 
	{
		this.server = server;
		this.artifacts = new ConcurrentHashMap<String, ArtifactMetrics>();
	}

	public void getPrincipalMetricsFromSonarqube(ArrayList<String> artifactsIDs, String artifactType, String projectName, String language, String commitSha, int version) throws JSONException, URISyntaxException 
	{
		for (String artifactID : artifactsIDs) 
		{
			if (artifactID.contains(".xml")) 
			{
				continue;
			}
			
			try {

				String metricsApiUrl = this.server + "/api/measures/component?"
						+ "metricKeys=code_smells,bugs,vulnerabilities,duplicated_lines_density,classes,complexity,functions,ncloc,statements,comment_lines_density"
						+ "&component=" + artifactID;

				String encodedUrl = encodeURL(metricsApiUrl);
				
				System.out.println("Encode URL: " + encodedUrl);
 
				CloseableHttpClient httpClient = HttpClients.createDefault();
				HttpGet getRequest = new HttpGet(encodedUrl);
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException(
							"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

				String output;

				ArtifactMetrics artifactObject = new ArtifactMetrics(projectName, artifactID, language, commitSha, version, artifactType);

				while ((output = br.readLine()) != null) 
				{
					JSONObject obj = new JSONObject(output);
					JSONArray array = obj.getJSONObject("component").getJSONArray("measures");
					String artifactPath = obj.getJSONObject("component").getString("path");
					
					artifactObject.setArtifactName(artifactPath);
					/*
					if (artifactType == "FIL")
					{
						String[] names = artifactPath.split("\\.");
						artifactObject.setArtifactName(names[0]);
						//setArtifactnames(names[0]);
					}
					else
					{
						artifactObject.setArtifactName(artifactPath);
						//setArtifactnames(artifactPath);
					}
					*/
					
					for (int i = 0; i < array.length(); i++) 
					{
						String metric = array.getJSONObject(i).getString("metric");
						String value = array.getJSONObject(i).getString("value");
						artifactObject = findIssue(metric, Double.parseDouble(value), artifactObject);
					}
					
					//checkSizes(array);

				} // end of while

				int td = getTechnicalDebtFromSonarqube(artifactID);
				
				artifactObject.setTechnicalDebt(td);
				
				artifacts.put(artifactObject.getArtifactName(), artifactObject);

				httpClient.close();				

			} catch (ClientProtocolException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
		} //end of for

		System.out.println("Metrics from sonar api retrieved with success!");
	}

	public int getTechnicalDebtFromSonarqube(String artifactID) throws URISyntaxException 
	{
		int td = 0;
		
		if (artifactID.contains("xml")) 
		{
			return 0;
		}
		try {

			String metricsApiUrl = this.server + "/api/issues/search?" + "facetMode=effort"
					+ "&facets=types" + "&componentKeys=" + artifactID;

			String encodedUrl = encodeURL(metricsApiUrl);

			CloseableHttpClient httpClient = HttpClients.createDefault();

			HttpGet getRequest = new HttpGet(encodedUrl);
			getRequest.addHeader("accept", "application/json");

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException(
						"Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			while ((output = br.readLine()) != null) 
			{

				String[] technicalDebt = output.split("effortTotal");
				String[] temp = technicalDebt[1].split(",");
				temp[0] = temp[0].replaceAll(":", "");
				temp[0] = temp[0].replaceAll("\"", "");
				td = Integer.parseInt(temp[0]);
			}

			httpClient.close();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return td;
	}

	public String encodeURL(String url) throws URISyntaxException, MalformedURLException
	{
		URL unstructuredURL = new URL(url);
		URI uri = new URI(unstructuredURL.getProtocol(), unstructuredURL.getUserInfo(), IDN.toASCII(unstructuredURL.getHost()), unstructuredURL.getPort(), unstructuredURL.getPath(), unstructuredURL.getQuery(), unstructuredURL.getRef());
		String encodedURL = uri.toASCIIString(); 
		encodedURL = encodedURL.replace("+", "%2b");
		return encodedURL;
	}
	
	public ConcurrentHashMap<String, ArtifactMetrics> getArtifacts()
	{
		return this.artifacts;
	}

	public ArtifactMetrics findIssue(String metricName, Double value,  ArtifactMetrics artifactObject) {
		/*
		 * SonarQube API does not return metrics to a specific order so we have to check
		 * every time what metric we got and to add it in the right array in the right
		 * order.
		 */
		
		if (metricName.equals("classes"))
		{
			artifactObject.setNumOfClasses(value);
		}
		else if (metricName.equals("complexity"))
		{
			artifactObject.setComplexity(value);
		}
		else if (metricName.equals("statements"))
		{
			artifactObject.setStatements(value);
		}
		else if (metricName.equals("functions"))
		{
			artifactObject.setFunctions(value);
		}
		else if (metricName.equals("ncloc"))
		{
			artifactObject.setNcloc(value);
		}
		else if (metricName.equals("code_smells"))
		{
			artifactObject.setCodeSmells(value);
		}
		else if (metricName.equals("bugs"))
		{
			artifactObject.setBugs(value);
		}
		else if (metricName.equals("vulnerabilities"))
		{
			artifactObject.setVulnerabilities(value);
		}
		else if (metricName.equals("duplicated_lines_density"))
		{
			artifactObject.setDuplicationsDensity(value);
		}
		else if (metricName.equals("comment_lines_density"))
		{
			artifactObject.setCommentsDensity(value);
		}
		
		return artifactObject;
	}
}
