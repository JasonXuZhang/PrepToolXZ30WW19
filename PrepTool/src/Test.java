import freemarker.template.*;

import java.util.*;
import java.io.*;

public class Test {
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void main(String[] args) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("."));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Template temp = cfg.getTemplate("test.ftl");
		/* 创建数据模型 */
		Map root = new HashMap();
		root.put("question", "New straight party vote by XU ZHANG");
		Map latest = new HashMap();
		root.put("latestProduct", latest);
		latest.put("url", "products/greenmouse.html");
		latest.put("name", "green mouse");
		
		List<String> lists = new ArrayList<String>();
		lists.add("Orange Jason");
		lists.add("Yellwo jason");
		lists.add("Tan jason");
		lists.add("Gold jason");
		root.put("lists", lists);
		
		/* 将模板和数据模型合并 */
		OutputStream outputStream = new FileOutputStream("/Users/xuzhang/Desktop/test.html");
		Writer out = new OutputStreamWriter(outputStream);
		temp.process(root, out);
		out.flush();
	}
	
	  public void testFileChooser() throws IOException, ClassNotFoundException {
		    HashMap<String, Object> fileObj = new HashMap<String, Object>();

		    ArrayList<String> cols = new ArrayList<String>();
		    cols.add("a");
		    cols.add("b");
		    cols.add("c");
		    fileObj.put("mylist", cols);
		    {
		        File file = new File("temp");
		        FileOutputStream f = new FileOutputStream(file);
		        ObjectOutputStream s = new ObjectOutputStream(f);
		        s.writeObject(fileObj);
		        s.close();
		    }
		    File file = new File("temp");
		    FileInputStream f = new FileInputStream(file);
		    ObjectInputStream s = new ObjectInputStream(f);
		    HashMap<String, Object> fileObj2 = (HashMap<String, Object>) s.readObject();
		    s.close();
		    ArrayList<String> result = (ArrayList<String>) fileObj2.get("myList");
		    for (String r : result)
		    	System.out.println(r);
	  }
}