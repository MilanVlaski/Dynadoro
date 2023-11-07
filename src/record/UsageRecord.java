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
		// TODO fix temporal coupling
		finishPrevious(stateInfo.getStartTime());
		states.add(stateInfo);
	}

	public void finishPrevious(int endTime) {
		if (!states.isEmpty()) {
			StateInfo latestState = states.get(states.size() - 1);
			latestState.finish(endTime);
		}
	}
}
