import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObjectToFile {
	public String fileName;
	
	public void setFileName(String fileName) {
		
	}
	
	public void writeObject() {
		try {
			
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("name", "foolfish");
			
			List<String> list = new ArrayList<String>();
			list.add("hello");
			list.add("everyone");
			list.add("jason");
			
			
			FileOutputStream outStream = new FileOutputStream("/Users/xuzhang/Desktop/1.txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
			
			objectOutputStream.writeObject(map);
			objectOutputStream.writeObject(list);
			outStream.close();
			System.out.println("successful");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readObject(){
		FileInputStream freader;
		try {
			freader = new FileInputStream("/Users/xuzhang/Desktop/1.txt");
			ObjectInputStream objectInputStream = new ObjectInputStream(freader);
			HashMap<String,String> map = new HashMap<String,String>();
			 map = (HashMap<String, String>) objectInputStream.readObject();
			 ArrayList<String> list = new ArrayList<String>();
			 list = (ArrayList<String>) objectInputStream.readObject();
			 System.out.println(map.get("name") + ":");
			 for (String l : list) {
				 System.out.println(l);
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String args[]){
		ObjectToFile of = new ObjectToFile();
//		of.writeObject();
		of.readObject();
	}
}
