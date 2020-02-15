package main.java.breakingPointTool.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ApiCall 
{		
	private ArrayList<Double> classes;
	private ArrayList<Double> complexity;
	private ArrayList<Double> functions;
	private ArrayList<Double> ncloc;
	private ArrayList<Double> statements;
	private ArrayList<Double> comment_lines_density;
	private ArrayList<Integer> technical_debt;
	private ArrayList<String> artifactNames;
	
	private ArrayList<Double> codeSmells;
	private ArrayList<Double> vulnerabilities;
	private ArrayList<Double> bugs;
	private ArrayList<Double> duplicated_lines_density;
	
	private String server;
	
	public ApiCall (String server)
	{
		this.server = server;
	}
	
	//TODO ta exception na anoiksw se stathero diktuo, ta ekleisa epeidi o server petaei refuse an exei polu forto
	public void getMetricCommentsDensityFromApi(ArrayList<String> classesIDs)
	{
		comment_lines_density = new ArrayList<>();
		
		for (String clIDs : classesIDs) 
		{
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet( this.server + 
						"/api/measures/component?" + "metricKeys=comment_lines_density" + 
							"&component=" + clIDs);
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					continue;
					//throw new RuntimeException("Failed : HTTP error code : "
							//+ response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())));

				String output;
				while ((output = br.readLine()) != null) 
				{
					//System.out.println(output);
					String[] commentDen = output.split("comment_lines_density");
					if (commentDen.length == 1)
					{
						this.comment_lines_density.add((double) 0);
					}
					else
					{
						String[] temp = commentDen[1].split("value");
						//System.out.println(temp[1]);
						temp[0] = temp[1].replaceAll(":", "");
						temp[0] = temp[0].replaceAll("\"", "");
						String[] cd = temp[0].split("}");
						//System.out.println(cd[0]);
						this.comment_lines_density.add(Double.parseDouble(cd[0]));
						//System.out.println("Comments Density: " + Double.parseDouble(cd[0]));
					}
				}

				httpClient.getConnectionManager().shutdown();
				//httpClient.close();

			} catch (ClientProtocolException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
	
		}
		System.out.println("Comments Density from sonar api retrieved with success!");
		
	}
	
	public void getMetricsFromApiSonarClassLevel(ArrayList<String> classesIDs, String language, String path) 
	{
		classes = new ArrayList<>();
		complexity = new ArrayList<>();
		functions = new ArrayList<>();
		ncloc = new ArrayList<>();
		statements = new ArrayList<>();
		technical_debt = new ArrayList<>();
		artifactNames = new ArrayList<>();
		
		bugs = new ArrayList<>();
		codeSmells = new ArrayList<>();
		vulnerabilities = new ArrayList<>();
		duplicated_lines_density = new ArrayList<>();
		
		for (String clIDs : classesIDs) 
		{
			if (clIDs.contains("xml"))
				{
				continue;
				
				}
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet( this.server +
						"/api/measures/component?" + "metricKeys=code_smells,bugs,vulnerabilities,duplicated_lines_density,classes,complexity,functions,ncloc,statements" + 
				"&component=" + clIDs);
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					//throw new RuntimeException("Failed : HTTP error code : "
						//	+ response.getStatusLine().getStatusCode());
					continue;
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())));

				String output;
					
				while ((output = br.readLine()) != null) 
				{
					// Temporary variables
					//System.out.println(output);

					if (output.contains(path))
					{

						//System.out.println(output);
						String[] tempMetricsArray;
						String[] tempValueArray;
						String tempVariable, tempVariableValue;
						// Metric
						String[] splittedMetrics = output.split(":");

						// only for java
						//String[] tempName = clIDs.split(path);

						// only for c++ and c
						//String[] tempName = clIDs.split(":");
						//System.out.println("Name of artifact: " + tempName[1]);
						String[] tempName1;
						if (language.equals("Java"))
						{
							String[] tempName = clIDs.split(path);
							tempName1 = tempName[1].split(".java");
							setArtifactnames(tempName1[0]);
						}

						else if (language.equals("C++"))
						{
							String[] tempName = clIDs.split(":");
							setArtifactnames(tempName[1]);
							/*
							 * String s = tempName[1].substring(tempName[1].length()-4); if
							 * (s.contains(".cpp")) tempName1 = tempName[1].split(".cpp"); else tempName1 =
							 * tempName[1].split(".h");
							 */
						}
						else
						{
							String[] tempName = clIDs.split(":");
							setArtifactnames(tempName[1]);
							/*
							 * String s = tempName[1].substring(tempName[1].length()-2);
							 * System.out.println(s); if (s.contains(".c")) tempName1 =
							 * tempName[1].split(".c"); else tempName1 = tempName[1].split(".h");
							 */
						}
						//setArtifactnames("src/" + tempName1[0]);

						tempMetricsArray = splittedMetrics[10].split(",");

						// Metric	
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("First metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[11].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("First value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						tempMetricsArray = splittedMetrics[12].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Second metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[13].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Second value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						tempMetricsArray = splittedMetrics[14].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						// Value
						tempValueArray = splittedMetrics[15].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						tempMetricsArray = splittedMetrics[16].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Fourth metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[17].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Fourth value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						
						// New code principal
						tempMetricsArray = splittedMetrics[18].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[19].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Third value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						
						tempMetricsArray = splittedMetrics[20].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[21].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Third value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						
						tempMetricsArray = splittedMetrics[22].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[23].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Third value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						
						tempMetricsArray = splittedMetrics[24].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[25].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Third value: " +  tempVariableValue);

						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						
						
						// Metric
						if (splittedMetrics.length > 27) 
						{
							tempMetricsArray = splittedMetrics[26].split(",");
							tempVariable = tempMetricsArray[0].replaceAll("\"", "");
							//System.out.println("Fifth metrics: " +  tempVariable);
							// Value
							tempValueArray = splittedMetrics[27].split("}");
							tempVariableValue = tempValueArray[0].replaceAll("\"", "");
							//System.out.println("Fifth value: " +  tempVariableValue);

							findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						}
						else
						{
							setFunctions(-1.0);
						}
					}

				} //end of while

				httpClient.getConnectionManager().shutdown();
				//httpClient.close();

			} catch (ClientProtocolException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		System.out.println("Metrics from sonar api retrieved with success!");
	}
	
	public void getMetricsFromSonarPackageLevel(ArrayList<String> packagesIDs, String language, String path)
	{
		classes = new ArrayList<>();
		complexity = new ArrayList<>();
		functions = new ArrayList<>();
		ncloc = new ArrayList<>();
		statements = new ArrayList<>();
		technical_debt = new ArrayList<>();
		artifactNames = new ArrayList<>();
		
		bugs = new ArrayList<>();
		codeSmells = new ArrayList<>();
		vulnerabilities = new ArrayList<>();
		duplicated_lines_density = new ArrayList<>();

		
		for (String clIDs : packagesIDs) 
		{
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet( this.server +
						"/api/measures/component?" + "metricKeys=code_smells,bugs,vulnerabilities,duplicated_lines_density,classes,complexity,functions,ncloc,statements" + 
				"&component=" + clIDs);
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					continue;
					//throw new RuntimeException("Failed : HTTP error code : "
							//+ response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())));

				String output;					
				while ((output = br.readLine()) != null) 
				{
					// Temporary variables
					//System.out.println(output);

					if (output.contains(path))
					{
						String[] tempMetricsArray;
						String[] tempValueArray;
						String tempVariable, tempVariableValue;
						// Metric
						String[] splittedMetrics = output.split(":");

						// only for java
						

						// only for c++ and c
						//String[] tempName = clIDs.split(":");

						//System.out.println("Name of artifact: " + clIDs);
						String[] tempName1;
						if (language.equals("Java"))
						{
							String[] tempName = clIDs.split(path);
							tempName1 = tempName[1].split(".java");
							setArtifactnames(tempName1[0]);
						}

						else if (language.equals("C++"))
						{
							String[] tempName = clIDs.split(":");
							setArtifactnames(tempName[1]);
							/*
							 * String s = tempName[1].substring(tempName[1].length()-4); if
							 * (s.contains(".cpp")) tempName1 = tempName[1].split(".cpp"); else tempName1 =
							 * tempName[1].split(".h");
							 */
						}
						else
						{
							String[] tempName = clIDs.split(":");
							setArtifactnames(tempName[1]);
							/*
							 * String s = tempName[1].substring(tempName[1].length()-2);
							 * System.out.println(s); if (s.contains(".c")) tempName1 =
							 * tempName[1].split(".c"); else tempName1 = tempName[1].split(".h");
							 */
						}


						//String[] splittedMetrics = output.split(":");
						tempMetricsArray = splittedMetrics[9].split(",");
						// Value
						//String[] splittedValues = output.split("}");

						// Metric	
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("First metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[10].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("First value: " +  tempVariableValue);
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						tempMetricsArray = splittedMetrics[11].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Second metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[12].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Second value: " +  tempVariableValue);
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						tempMetricsArray = splittedMetrics[13].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[14].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Third value: " +  tempVariableValue);
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						// Metric
						tempMetricsArray = splittedMetrics[15].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Fourth metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[16].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Fourth value: " +  tempVariableValue);
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						
						// new code
						
						// Metric
						tempMetricsArray = splittedMetrics[17].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[18].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						
						// Metric
						tempMetricsArray = splittedMetrics[19].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[20].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						
						// Metric
						tempMetricsArray = splittedMetrics[21].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[22].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						
						// Metric
						tempMetricsArray = splittedMetrics[23].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Third metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[24].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						
						// Metric
						if (splittedMetrics.length > 26) 
						{
							tempMetricsArray = splittedMetrics[25].split(",");
							tempVariable = tempMetricsArray[0].replaceAll("\"", "");
							//System.out.println("Fifth metrics: " +  tempVariable);
							// Value
							tempValueArray = splittedMetrics[26].split("}");
							tempVariableValue = tempValueArray[0].replaceAll("\"", "");
							//System.out.println("Fifth value: " +  tempVariableValue);
							findIssue(tempVariable,Double.parseDouble(tempVariableValue));
						}
						else
						{
							setFunctions(-1.0);
						}
					}
				}

				httpClient.getConnectionManager().shutdown();
				//httpClient.close();

			} catch (ClientProtocolException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		System.out.println("Metrics from sonar api retrieved with success!");
	}
	
	public void getMetricsFromSonarProjectLevel(ArrayList<String> projectsIDs)
	{
		classes = new ArrayList<>();
		complexity = new ArrayList<>();
		functions = new ArrayList<>();
		ncloc = new ArrayList<>();
		statements = new ArrayList<>();
		technical_debt = new ArrayList<>();
		artifactNames = new ArrayList<>();
		
		for (String clIDs : projectsIDs) 
		{
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet( this.server + 
						"/api/measures/component?" + "metricKeys=code_smells,bugs,vulnerabilities,classes,complexity,functions,ncloc,statements" + 
				"&component=" + clIDs);
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) {
					continue;
					//throw new RuntimeException("Failed : HTTP error code : "
						//	+ response.getStatusLine().getStatusCode());
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())));

				String output;					
				while ((output = br.readLine()) != null) 
				{
					//System.out.println(output);
					// Temporary variables
					String[] tempMetricsArray;
					String[] tempValueArray;
					String tempVariable, tempVariableValue;
					
					String[] splittedMetrics = output.split(":");

					String[] tempName = output.split("name\":\"");
					String[] tempName1 = tempName[1].split("\"");
 					setArtifactnames(tempName1[0]);						
					
					// Metric
					//String[] splittedMetrics = output.split(":");
					tempMetricsArray = splittedMetrics[7].split(",");
					// Value
					//String[] splittedValues = output.split("}");
			
					// Metric	
					tempVariable = tempMetricsArray[0].replaceAll("\"", "");
					//System.out.println("First metrics: " +  tempVariable);
					// Value
					tempValueArray = splittedMetrics[8].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("First value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					// Metric
					tempMetricsArray = splittedMetrics[9].split(",");
					tempVariable = tempMetricsArray[0].replaceAll("\"", "");
					//System.out.println("Second metrics: " +  tempVariable);
					// Value
					tempValueArray = splittedMetrics[10].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("Second value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					// Metric
					tempMetricsArray = splittedMetrics[11].split(",");
					tempVariable = tempMetricsArray[0].replaceAll("\"", "");
					//System.out.println("Third metrics: " +  tempVariable);
					// Value
					tempValueArray = splittedMetrics[12].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("Third value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					// Metric
					tempMetricsArray = splittedMetrics[13].split(",");
					tempVariable = tempMetricsArray[0].replaceAll("\"", "");
					//System.out.println("Fourth metrics: " +  tempVariable);
					// Value
					tempValueArray = splittedMetrics[14].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("Fourth value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					
					// new code
					
					tempValueArray = splittedMetrics[15].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("Fourth value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					
					tempValueArray = splittedMetrics[16].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("Fourth value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					
					tempValueArray = splittedMetrics[17].split("}");
					tempVariableValue = tempValueArray[0].replaceAll("\"", "");
					//System.out.println("Fourth value: " +  tempVariableValue);
					findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					// Metric
					if (splittedMetrics.length > 19) 
					{
						tempMetricsArray = splittedMetrics[18].split(",");
						tempVariable = tempMetricsArray[0].replaceAll("\"", "");
						//System.out.println("Fifth metrics: " +  tempVariable);
						// Value
						tempValueArray = splittedMetrics[19].split("}");
						tempVariableValue = tempValueArray[0].replaceAll("\"", "");
						//System.out.println("Fifth value: " +  tempVariableValue);
						findIssue(tempVariable,Double.parseDouble(tempVariableValue));
					}
					else
					{
						setFunctions(-1.0);
					}
	
				}

				httpClient.getConnectionManager().shutdown();
				//httpClient.close();

			} catch (ClientProtocolException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		System.out.println("Metrics from sonar api retrieved with success!");
	}

	public void getTDFromApiSonar(ArrayList<String> classesIDs)
	{
		for (String clIDs : classesIDs) 
		{
			if (clIDs.contains("xml"))
			{
				continue;
			
			}
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet(this.server + 
						"/api/issues/search?" + "facetMode=effort" +
				"&facets=types" + "&componentKeys=" + clIDs);
				getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() != 200) 
				{
					httpClient.getConnectionManager().shutdown();
					//httpClient.close();
					throw new RuntimeException("Failed : HTTP error code : "
							+ response.getStatusLine().getStatusCode());					
				}

				BufferedReader br = new BufferedReader(
						new InputStreamReader((response.getEntity().getContent())));

				String output;
				while ((output = br.readLine()) != null) 
				{
					String[] technicalDebt = output.split("effortTotal");
					String[] temp = technicalDebt[1].split(",");
					temp[0] = temp[0].replaceAll(":", "");
					temp[0] = temp[0].replaceAll("\"", "");
					this.technical_debt.add(Integer.parseInt(temp[0]));
					//System.out.println("TD: " + Integer.parseInt(temp[0]));
					//System.out.println(output);
				}

				httpClient.getConnectionManager().shutdown();
				//httpClient.close();

			} catch (ClientProtocolException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		System.out.println("Technical Debt from sonar api retrieved with success!");
	}
	
	public void setBugs(Double b)
	{
		this.bugs.add(b);
	}
	
	public void setCodeSmells(Double b)
	{
		this.codeSmells.add(b);
	}
	
	public void setVulnerabilities(Double b)
	{
		this.vulnerabilities.add(b);
	}
	
	public void setNumofClasses(Double b)
	{
		this.classes.add(b);
	}
	
	public void setArtifactnames(String n)
	{
		this.artifactNames.add(n);
	}
	
	public void setComplexity(Double cs)
	{
		this.complexity.add(cs);
	}
	
	public void setncloc(Double v)
	{
		this.ncloc.add(v);
	}
	
	public void setStatements(Double d)
	{
		this.statements.add(d);
	}
	
	public void setFunctions(Double c)
	{
		this.functions.add(c);
	}
	
	public void setTechnicalDebt(Integer td)
	{
		this.technical_debt.add(td);
	}
	
	public void setCommentsDensity(Double cd)
	{
		 this.comment_lines_density.add(cd);
	}
	
	public void setDuplicationsDensity(Double cd)
	{
		 this.duplicated_lines_density.add(cd);
	}
	
	public ArrayList<Double> getCommentsDensity()
	{
		return this.comment_lines_density;
	}
	
	public ArrayList<String> getArtifactNames()
	{
		return this.artifactNames;
	}
	
	public ArrayList<Double> getNumOfClasses()
	{
		return this.classes;
	}
	
	public ArrayList<Double> getComplexity()
	{
		return this.complexity;
	}
	
	public ArrayList<Double> getStatements()
	{
		return this.statements;
	}
	
	public ArrayList<Double> getFunctions()
	{
		return this.functions;
	}
	
	public ArrayList<Double> getNcloc()
	{
		return this.ncloc;
	}
	
	public ArrayList<Integer> getTechnicalDebt()
	{
		return this.technical_debt;
	}
	
	public ArrayList<Double> getBugs()
	{
		return this.bugs;
	}
	
	public ArrayList<Double> getCodeSmells()
	{
		return this.codeSmells;
	}
	
	public ArrayList<Double> getVulnerabilities()
	{
		return this.vulnerabilities;
	}
	
	public ArrayList<Double> getDuplicationsDensity()
	{
		return this.duplicated_lines_density;
	}
	
	public void findIssue(String metricName, Double value)
	{
		/* SonarQube API does not return metrics to a specific order so we have to check 
		 * every time what metric we got and to add it in the right array in the right order.
		 */
		if (metricName.equals("classes"))
			setNumofClasses(value);
		else if (metricName.equals("complexity"))
			setComplexity(value);
		else if (metricName.equals("statements"))
			setStatements(value);
		else if (metricName.equals("functions"))
			setFunctions(value);
		else if (metricName.equals("ncloc"))
			setncloc(value);
		else if (metricName.equals("code_smells"))
			setCodeSmells(value);
		else if (metricName.equals("bugs"))
			setBugs(value);
		else if (metricName.equals("vulnerabilities"))
			setVulnerabilities(value);
		else if (metricName.equals("duplicated_lines_density"))
			setDuplicationsDensity(value);
	}
}
