package eu.sdk4ed.uom.td.analysis.calculations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import eu.sdk4ed.uom.td.analysis.artifact.ClassMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.PackageMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.ProjectArtifact;
import eu.sdk4ed.uom.td.analysis.database.DatabaseGetData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;


public class AverageLocCalculation 
{
	private BufferedReader br;
	private ArrayList<ClassMetrics> classMetrics = new ArrayList<ClassMetrics>();
	private ArrayList<PackageMetrics> packageMetrics = new ArrayList<PackageMetrics>();

	public void setMetricsToClassLevel(String projectName, int versionNum) throws NumberFormatException, IOException, SQLException
	{
		String line;
		HashMap<String, ArrayList<Double>> ChangeProneness = new HashMap<String, ArrayList<Double>>();

		ChangeProneness = ChangeProneness("rem_and_cpm_metrics_classLevel.csv");
		System.out.println("Current path to read file: **********" + System.getProperty("user.dir"));
		// read specific version file
		br = new BufferedReader(new FileReader("output" + versionNum + ".csv"));	
		//System.out.println("Version: " + i);
		while ((line = br.readLine()) != null) 
		{
			if (line.contains(".")) 
			{
				if (!line.contains("test") && !line.contains("Test")) 
				{

					String[] parts = line.split(";");
					String className = parts[0].replaceAll("\\.", "/");

					double wmc = Double.parseDouble(parts[1]);
					double dit = Double.parseDouble(parts[2]);
					double cbo = Double.parseDouble(parts[4]);
					double rfc = Double.parseDouble(parts[5]);
					double lcom = Double.parseDouble(parts[6]);
					double wmc_dec = parts[7].contains("-1000") ? 0 : Double.parseDouble(parts[7]);
					double nocc = Double.parseDouble(parts[9]);
					double mpc = Double.parseDouble(parts[13]);
					double dac = Double.parseDouble(parts[14]);
					double size1 = Double.parseDouble(parts[15]);
					double size2 = Double.parseDouble(parts[16]);
					double dsc = Double.parseDouble(parts[17]);
					double noh = Double.parseDouble(parts[18]);
					double ana = Double.parseDouble(parts[19]);
					double dam = Double.parseDouble(parts[20]);
					double dcc = Double.parseDouble(parts[21]);
					double camc = Double.parseDouble(parts[22]);
					double moa = Double.parseDouble(parts[23]);
					double mfa = Double.parseDouble(parts[24]);
					double nop = Double.parseDouble(parts[25]);
					double cis = Double.parseDouble(parts[26]);
					double nom = Double.parseDouble(parts[27]);
					double Reusability = Double.parseDouble(parts[28]);
					double Flexibility = Double.parseDouble(parts[29]);
					double Understandability = Double.parseDouble(parts[30]);
					double Functionality = Double.parseDouble(parts[31]);
					double Extendibility = Double.parseDouble(parts[32]);
					double Effectiveness = Double.parseDouble(parts[33]);
					double FanIn = Double.parseDouble(parts[34]);
					String scope = "FIL";

					ArrayList<Double> list = new ArrayList<Double>();
					//list = ChangeProneness.get(className);

					if (ChangeProneness.get(className) == null)
					{
						list.add(0.0);
						list.add(0.0);
					}
					else
					{
						list = ChangeProneness.get(className);
					}


					DatabaseSaveData saveInDataBase = new DatabaseSaveData();
					saveInDataBase.saveMetricsInDatabase(projectName, versionNum, className, scope, wmc, dit, cbo, rfc, lcom, wmc_dec, nocc, mpc, dac, size1, size2,
							dsc, noh, ana, dam, dcc, camc, moa, mfa, nop, cis, nom, Reusability, Flexibility, Understandability, Functionality, Extendibility, Effectiveness, FanIn,list.get(0), list.get(1));

					ClassMetrics cm = new ClassMetrics(projectName, className);
					cm.metricsfromMetricsCalculator(mpc, wmc, dit, nocc, rfc, lcom, wmc_dec, dac, size1, size2);
					cm.metricsfromChangeProneness(list.get(0), list.get(1));
					classMetrics.add(cm);
				}

			}
		}
	}

	public HashMap<String, ArrayList<Double>> ChangeProneness(String fileName) throws IOException
	{
		HashMap<String, ArrayList<Double>> ChangeProneness = new HashMap<String, ArrayList<Double>>();

		String line;
		//"rem_and_cpm_metrics_classLevel.csv"
		// read specific version file
		File f = new File(fileName);

		if(f.exists()) 
		{ 
			br = new BufferedReader(new FileReader(fileName));	
			while ((line = br.readLine()) != null) 
			{
				if (line.contains(".")) 
				{				
					if (!line.contains("test") && !line.contains("Test")) 
					{
						String[] parts = line.split(",");
						String className = parts[0].replaceAll("\\.", "/");

						double rem = Double.parseDouble(parts[1]);
						double cpm = Double.parseDouble(parts[2]);
						ArrayList<Double> list = new ArrayList<Double>();
						list.add(rem);
						list.add(cpm);
						ChangeProneness.put(className, list);	
					}
				}
			}
		}

		return ChangeProneness;
	}

	public ArrayList<String> findAllClasses(ProjectArtifact p)
	{
		ArrayList<String> classes = new ArrayList<String>();

		for (int i = 0; i < p.getVersions().size(); i++)
		{
			for (int j = 0; j < p.getVersions().get(i).getPackages().size(); j++)
			{
				for (int z = 0; z < p.getVersions().get(i).getPackages().get(j).getClassInProject().size(); z++)
				{
					if (!classes.contains(p.getVersions().get(i).getPackages().get(j).getClassInProject().get(z).getClassName()))
					{
						classes.add(p.getVersions().get(i).getPackages().get(j).getClassInProject().get(z).getClassName());
					}

				}
			}
		}
		return classes;		
	}

	public ArrayList<String> findAllPackages(ProjectArtifact p)
	{
		ArrayList<String> packages = new ArrayList<String>();

		for (int i = 0; i < p.getVersions().size(); i++)
		{
			for (int j = 0; j < p.getVersions().get(i).getPackages().size(); j++)
			{
				if (!packages.contains(p.getVersions().get(i).getPackages().get(j).getPackageName()))
				{
					packages.add(p.getVersions().get(i).getPackages().get(j).getPackageName());
				}
			}
		}
		return packages;		
	}

	public void calculateLocClassLevel(ProjectArtifact p)
	{
		ArrayList<String> classes = findAllClasses(p);

		for (int cl = 0; cl < classes.size(); cl++)
		{
			String currentClass = classes.get(cl);
			//System.out.println("Current class: " + currentClass);
			ArrayList<Double> sizes = new ArrayList<Double>();

			for (int i = 0; i < p.getVersions().size(); i++)
			{
				System.out.println("Version: " +  i);
				System.out.println("Class current: " + currentClass);

				int flag = 0;
				int packId = 0;
				int classId = 0;
				for (int j = 0; j < p.getVersions().get(i).getPackages().size(); j++)
				{	
					System.out.println("Classes: "+ p.getVersions().get(i).getPackages().get(j).getPackageName());

					for (int z = 0; z < p.getVersions().get(i).getPackages().get(j).getClassInProject().size(); z++)
					{		
						if (currentClass.equals(p.getVersions().get(i).getPackages().get(j).getClassInProject().get(z).getClassName())) 
						{
							sizes.add(p.getVersions().get(i).getPackages().get(j).getClassInProject().get(z).getSize1());
							flag = 1;
							classId = z;
							break;
						}	
					}
					if (flag == 1)
					{
						packId = j;
						break;		
					}

				}

				//if (flag == 0)
				//continue;


				System.out.println("Flag: " + flag);
				double x = 0;
				int aboveZero = 0;

				if (sizes.size() == 1)
				{
					x = 0; //sizes.get(0);
					aboveZero = 1;
				}
				else if (sizes.size() == 2)
				{
					x = Math.abs(sizes.get(1) - sizes.get(0));
					aboveZero = 1;
				}

				for (int l = 1 ; l < sizes.size(); l++)
				{
					double diff = Math.abs(sizes.get(l) - sizes.get(l-1));
					x = x + diff;
					aboveZero++;
				}

				if (!Double.isNaN(x/aboveZero))
				{
					if (currentClass.equals(p.getVersions().get(i).getPackages().get(packId).getClassInProject().get(classId).getClassName()))
					{
						p.getVersions().get(i).getPackages().get(packId).getClassInProject().get(classId).setAverageLOCChange(x/aboveZero);
						//System.out.println("For class: " + p.getVersions().get(i).getPackages().get(packId).getClassInProject().get(classId).getClassName());
						//System.out.println("The LOC is: " + x/sizes.size());
					}
				}
			}
		}
	}

	public void calculateLocPackageLevel(ProjectArtifact p)
	{		
		ArrayList<String> packages = findAllPackages(p);

		for (int pk = 0; pk < packages.size(); pk++)
		{
			String currentPackage = packages.get(pk);
			//System.out.println("Current package: " + currentPackage);
			ArrayList<Double> sizes = new ArrayList<Double>();

			for (int i = 0; i < p.getVersions().size(); i++)
			{
				//System.out.println("Version: " +  i);
				int packId = 0;
				for (int j = 0; j < p.getVersions().get(i).getPackages().size(); j++)
				{							
					if (currentPackage.equals(p.getVersions().get(i).getPackages().get(j).getPackageName())) 
					{
						sizes.add(p.getVersions().get(i).getPackages().get(j).getSize1());
						packId = j;
						break;
					}	
				}

				double x = 0;
				int aboveZero = 0;

				if (sizes.size() == 1)
				{
					x = 0; //sizes.get(0);
					aboveZero = 1;
				}
				else if (sizes.size() == 2)
				{
					x = Math.abs(sizes.get(1) - sizes.get(0));
					aboveZero = 1;
				}

				for (int l = 1 ; l < sizes.size(); l++)
				{
					double diff = Math.abs(sizes.get(l) - sizes.get(l-1));
					x = x + diff;
					//if (diff > 0)
					aboveZero++;

				}

				if (!Double.isNaN(x/aboveZero))
				{
					System.out.println(currentPackage);
					if (currentPackage.equals(p.getVersions().get(i).getPackages().get(packId).getPackageName()))
					{
						p.getVersions().get(i).getPackages().get(packId).setAverageLocChange(x/aboveZero);
						//System.out.println("For package: " + p.getVersions().get(i).getPackages().get(packId).getPackageName());
						//System.out.println("The LOC is: " + x/sizes.size());
					}
				}
			}
		}
	}

	public void setClassToPackageLevel(ArrayList<String> longNamePackage, String projName, int version) throws NumberFormatException, SQLException, IOException
	{		
		for (int i = 0; i < longNamePackage.size(); i++)
		{
			PackageMetrics p = new PackageMetrics(projName,longNamePackage.get(i));
			this.packageMetrics.add(p);
		}

		HashMap<String, ArrayList<Double>> ChangePronenessPackage = new HashMap<String, ArrayList<Double>>();

		ChangePronenessPackage = ChangeProneness("rem_and_cpm_metrics_packageLevel.csv");

		for (int i = 0; i < this.packageMetrics.size(); i++)
		{
			String packName = this.packageMetrics.get(i).getPackageName();


			for (int j = 0; j < this.classMetrics.size(); j++)
			{
				int index = this.classMetrics.get(j).getClassName().lastIndexOf("/");

				if (index >= 0)
				{
					String packNameOfClass = this.classMetrics.get(j).getClassName().substring(0,index);

					// apo equals egine contains
					if (packName.contains(packNameOfClass))
					{

						int first = packName.indexOf(packNameOfClass);
						int len = packNameOfClass.length();

						if ((first + 1 + len) < packName.length())
							continue;

						System.out.println("Class " + this.classMetrics.get(j) + " assigned to package " + packName);
						this.packageMetrics.get(i).setClassInPackage(this.classMetrics.get(j));
						this.packageMetrics.get(i).setPackageName(packNameOfClass);


						ArrayList<Double> list = new ArrayList<Double>();
						//list = ChangePronenessPackage.get(packName);

						if (ChangePronenessPackage.get(packNameOfClass) == null)
						{
							list.add(0.0);
							list.add(0.0);
						}
						else
						{
							list = ChangePronenessPackage.get(packNameOfClass);
						}

						this.packageMetrics.get(i).metricsfromChangeProneness(list.get(0), list.get(1));
						//this.packageMetrics.get(i).print();

					}
				}
			}

			this.packageMetrics.get(i).calculateMetricsPackageLevel(version);
		}
	}

	public void calculateLOCClassLevelNewVersion(HashMap<String, ClassMetrics> previous, ArrayList<PackageMetrics> currentVersion, int versions )
	{		
		for (int j = 0; j < currentVersion.size(); j++)
		{
			//currentVersion.get(j).getClassInProject(); // lista me vlasses
			// for each package

			for (int i = 0; i < currentVersion.get(j).getClassInProject().size(); i++)
			{
				// for each class
				ClassMetrics current = currentVersion.get(j).getClassInProject().get(i);
				if (previous.containsKey(currentVersion.get(j).getClassInProject().get(i).getClassName()))
				{
					ArrayList<Double> locs = new ArrayList<Double>();
					// Get k values from database
					DatabaseGetData dbCall = new DatabaseGetData();
					locs.addAll(dbCall.getLoCForArtifact(current.getClassName(), versions));

					// real number of versions
					int v = locs.size();

					if (v == 0)
						break;

					ClassMetrics previousClass = previous.get(currentVersion.get(j).getClassInProject().get(i).getClassName());

					System.out.println("Old average before multi : " + previousClass.getAverageLocChange());
					double oldLoc = Math.round(previousClass.getAverageLocChange() * (v-2));
					System.out.println("Old average loc: " + oldLoc);
					// TODO
					//  java.lang.IndexOutOfBoundsException: Index 7 out of bounds for length 7
					System.out.println("Current LOC: " + current.getSize1());
					System.out.println("Previous LOC: " + previousClass.getSize1());
					double currentLoc = Math.abs(current.getSize1() - previousClass.getSize1());

					double loc = (oldLoc + currentLoc) / (v-1); 
					System.out.println("Before and after: " + oldLoc + " " + loc);
					System.out.println("For class: " + current.getClassName() + 
							" the loc is: " + loc);
					currentVersion.get(j).getClassInProject().get(i).setAverageLOCChange(loc);
				}
			}
		}
	}

	public ArrayList<PackageMetrics> calculateLOCCPackageLevelNewVersion(HashMap<String, PackageMetrics> previous, ArrayList<PackageMetrics> currentVersion, int versions )
	{	
		for (int i = 0; i < currentVersion.size(); i++)
		{
			if (previous.containsKey(currentVersion.get(i).getPackageName()))
			{

				ArrayList<Double> locs = new ArrayList<Double>();
				// Get k values from database
				DatabaseGetData dbCall = new DatabaseGetData();
				locs.addAll(dbCall.getLoCForArtifact(currentVersion.get(i).getPackageName(), versions));

				// real number of versions
				int v = locs.size();

				if (v == 0)
					break;

				PackageMetrics c = previous.get(currentVersion.get(i).getPackageName());
				double oldLoc = Math.round(c.getAverageLocChange() * (v - 2));

				double currentLoc = Math.abs(currentVersion.get(i).getSize1() - c.getSize1());

				double loc = (oldLoc + currentLoc) / (v-1); 
				System.out.println("Before and after: " + oldLoc + " " + loc);

				System.out.println("For package: " + currentVersion.get(i).getPackageName() + 
						" the loc is: " + loc);
				currentVersion.get(i).setAverageLocChange(loc);
			}
		}

		return currentVersion;
	}

	public ArrayList<ClassMetrics> getObjectsClassMetrics()
	{
		return this.classMetrics;
	}

	public ArrayList<PackageMetrics> getObjectsPackageMetrics()
	{
		return this.packageMetrics;
	}

}
