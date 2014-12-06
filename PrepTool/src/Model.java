import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


public class Model {
	private GUIView view;
	private List<Race> list;
	
	public List<Race> getRaceList() {
		return new ArrayList<Race>(this.list);
	}
	
	public Model() {
		list = new ArrayList<Race>();
	}
	
	public void setView (GUIView view) {
		this.view = view;
	}
	
	public void print(String msg) {
		System.out.println(msg);
	}
	
	public Race getRaceByIndex(int index) {
		return list.get(index);
	}
	
	public void addRace(Race race) {
		list.add(race);
	}

	public void replaceRaceByIndex(int index, Race race) {
		list.remove(index);
		list.add(index, race);
		for (Race r : list)
			System.out.println("-" + r.title);
	}

	public void removeRaceByIndex(int index) {
		list.remove(index);
	}

	public void generateBallot(String directory) throws IOException, TemplateException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("."));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template temp = cfg.getTemplate("index.ftl");
		
		Map root = new HashMap();
		root.put("raceCount", list.size());
		List raceList = new ArrayList();
		/**
		 * scan the list of races
		 * for each race, establish a map containing the title, description, and list of candidates
		 * then put the map for each race into the root map
		 */
		for (Race race : list) {
			Map raceMap = new HashMap();
			raceMap.put("title", race.title);
			raceMap.put("description", race.description);
			raceMap.put("descriptions", race.descriptions);
			raceMap.put("maxAnswer", race.maxAnswer);
			raceMap.put("allowCustomisedCandidate", race.allowCustomisedCandidate);
			List<String> candidateList = new ArrayList<String>();			
			for (String cand :  race.candidates)
				candidateList.add(cand);
			raceMap.put("candidates", candidateList);
			raceList.add(raceMap);
		}
		root.put("raceList", raceList);
		
		OutputStream outputStream = new FileOutputStream(directory + File.separator +"ballot.html");
		Writer out = new OutputStreamWriter(outputStream);
		temp.process(root, out);
		out.flush();
		out.close();
		
//		this.printBallot(this.list);
		String scriptFolderName = "ballot";
		this.buildFolder(directory, scriptFolderName);
		this.copyFolder(new File(scriptFolderName), new File(directory + File.separator + scriptFolderName));
		view.showMessage("ballot has been saved");
	}
	
	private void buildFolder(String directory, String scriptFolderPath) {
    	File dir = new File(directory);
		if(dir.isDirectory()) {
    		//if directory not exists, create it
    		newFolder(directory + File.separator + scriptFolderPath);
    	}
	}
	
	public void newFolder(String folderPath) { 
	    try { 
	      String filePath = folderPath; 
	      File myFilePath = new File(filePath); 
	      if (!myFilePath.exists()) { 
	        myFilePath.mkdir(); 
	      } 
	    } catch (Exception e) { 
	      e.printStackTrace(); 
	    } 
	} 

	public Map getRootMap() {
		Map root = new HashMap();
		root.put("raceCount", list.size());
		List raceList = new ArrayList();
		/**
		 * scan the list of races
		 * for each race, establish a map containing the title, description, and list of candidates
		 * then put the map for each race into the root map
		 */
		for (Race race : list) {
			Map raceMap = new HashMap();
			raceMap.put("title", race.title);
			raceMap.put("description", race.description);
			raceMap.put("descriptions", race.descriptions);
			raceMap.put("maxAnswer", race.maxAnswer);
			raceMap.put("allowCustomisedCandidate", race.allowCustomisedCandidate);
			List<String> candidateList = new ArrayList<String>();			
			for (String cand :  race.candidates)
				candidateList.add(cand);
			raceMap.put("candidates", candidateList);
			raceList.add(raceMap);
		}
		root.put("raceList", raceList);
		return root;
	}
	
	public void printBallot(List<Race> list) {
		System.out.println("BALLOT:\n---");
		// scan the list of Races
		for (Race race : list) {
			System.out.println("title: " + race.title);
			System.out.println("desc: " + race.description);
			System.out.println("type: " + race.type);
			System.out.println("max: " + race.maxAnswer);
			System.out.print("candidates: ");
			for (String cand : race.candidates)
				System.out.print(cand + ", ");
			System.out.println("\n---");
		}
	}

	public void saveBallotToFile(String filePath, String fileName) {
		Map root = getRootMap();
		this.writeBallotObject(root, filePath);
	}
	
	public void writeBallotObject(Map rootMap, String fileName) {
		try {
			FileOutputStream outStream = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
			objectOutputStream.writeObject(rootMap);
			outStream.close();
			System.out.println("object written successfully");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readBallotFromFile(String filePath, String fileName) {
		Map rootMap = readObject(filePath);
		if (rootMap == null) {
			view.showMessage(fileName + " is not a valid ballot file");
			return;
		}
		List<Race> newRaceList = getListFromRootMap(rootMap);
		this.list = newRaceList;
		view.updateRaceList();
	}
	
	public List<Race> getListFromRootMap(Map rootMap) {
		List raceList = (List) rootMap.get("raceList");
		List<Race> newRaceList = new ArrayList<Race>();
		for (int i = 0; i < raceList.size(); i++) {
			Object object = raceList.get(i);
			if (object instanceof HashMap) {
				Map raceMap = (HashMap)object;
				Race newRace = new Race();
				newRace.setTitle((String) raceMap.get("title"));
				newRace.setDescription((String) raceMap.get("description"));
				newRace.setMaxAnswer((int) raceMap.get("maxAnswer"));
				newRace.setAllowCustomisedCandidate((boolean) raceMap.get("allowCustomisedCandidate"));
				newRace.setCandidateList((List<String>) raceMap.get("candidates"));
				newRaceList.add(newRace);
			}
		}
		return newRaceList;
	}

	public Map readObject(String filePath){
		FileInputStream freader;
		Map rootMap = null;
		try {
			freader = new FileInputStream(filePath);
			ObjectInputStream objectInputStream = new ObjectInputStream(freader);
			Object object = objectInputStream.readObject();
			if (object instanceof HashMap)
				rootMap = (HashMap)object;
			else 
				rootMap = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return rootMap;
	}
	
	/**
	 * move the selected race up/down
	 * @param selectedIndex
	 * @param step 1 for down, -1 for up
	 * @return the index of the selected race after the movement
	 */
	public int moveRaceByOneStep(int selectedIndex, int step) {
		if (selectedIndex < 0 || selectedIndex >= list.size() || selectedIndex + step < 0 || selectedIndex + step >= list.size())
			return selectedIndex;
		// right now we only allow the value of steps to be 1 or -1
		if (step != 1 && step != -1)
			return selectedIndex;
		int neighbourIndex = selectedIndex + step;
		Collections.swap(list, selectedIndex, neighbourIndex);
		return neighbourIndex;
	}
	
	 public static void copyFolder(File src, File dest)
		    	throws IOException{
		 
		    	if(src.isDirectory()){
		 
		    		//if directory not exists, create it
		    		if(!dest.exists()){
		    		   dest.mkdir();
		    		   System.out.println("Directory copied from " 
		                              + src + "  to " + dest);
		    		}
		 
		    		//list all the directory contents
		    		String files[] = src.list();
		 
		    		for (String file : files) {
		    		   //construct the src and dest file structure
		    		   File srcFile = new File(src, file);
		    		   File destFile = new File(dest, file);
		    		   //recursive copy
		    		   copyFolder(srcFile,destFile);
		    		}
		 
		    	}else{
		    		//if file, then copy it
		    		//Use bytes stream to support all file types
		    		InputStream in = new FileInputStream(src);
		    	        OutputStream out = new FileOutputStream(dest); 
		 
		    	        byte[] buffer = new byte[1024];
		 
		    	        int length;
		    	        //copy the file content in bytes 
		    	        while ((length = in.read(buffer)) > 0){
		    	    	   out.write(buffer, 0, length);
		    	        }
		 
		    	        in.close();
		    	        out.close();
		    	        System.out.println("File copied from " + src + " to " + dest);
		    	}
		    }
}


