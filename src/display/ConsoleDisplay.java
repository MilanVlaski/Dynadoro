package display;

import java.util.ArrayList;
import java.util.List;

public class ConsoleDisplay implements Display {

	@Override
	public String display(int seconds) {
		int hours = seconds / 3600;
	    int minutes = (seconds % 3600) / 60;
	    int remainingSeconds = seconds % 60;
	    
	    List<String> result = new ArrayList<>();
	    
	    if(hours > 0)
	    	result.add(stringOfTime(hours));
	    
	    result.add(stringOfTime(minutes));
	    result.add(stringOfTime(remainingSeconds));
	    
	    
	    return String.join(":", result);
	}

	private String stringOfTime(int timeUnit) {
		return String.format("%02d", timeUnit);
	}

}
