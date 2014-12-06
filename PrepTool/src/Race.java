import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Race {
	public String title;
	public String description;
	public String type;
	public boolean allowCustomisedCandidate;
	public int maxAnswer = 1;
	public List<String> candidates = new ArrayList<String>();
	public List<String> descriptions = new ArrayList<String>();
	
	public void setTitle(String t) {
		this.title = t;
	}
	
	public void setDescription(String d) {
		this.description = d;
		String[] lines = description.split("\n|\r");
		descriptions = new ArrayList<String>(Arrays.asList(lines));
	}
	
	public void addCandidate(String candidate) {
		candidates.add(candidate);
	}
	
	public void setCandidateList(List<String> canList) {
		this.candidates = new ArrayList<String>(canList);
	}
	
	public void removeCandidateByIndex(int index) {
		candidates.remove(index);
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setMaxAnswer(int maxAnswer) {
		this.maxAnswer = maxAnswer;
	}
	
	public void setAllowCustomisedCandidate(boolean enabled) {
		this.allowCustomisedCandidate = enabled;
	}
}
