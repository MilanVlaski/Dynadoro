package record;

import java.util.ArrayList;
import java.util.List;

public class UsageRecord {

	private List<StateInfo> states = new ArrayList<>();

	@Override
	public String toString() {
		String result = "";

		for (StateInfo stateInfo : states)
			result += stateInfo.toString() + "\n";

		return result;
	}

	public void capture(StateInfo stateInfo) {
		states.add(stateInfo);
	}
}
