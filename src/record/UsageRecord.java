package record;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UsageRecord {

	private final List<StateInfo> states = new ArrayList<>();

	@Override
	public String toString() {
		String result = "";
		List<StateInfo> statesThatMatter = states.stream()
				.filter((state) -> state.matters())
				.toList();

		for (StateInfo stateInfo : statesThatMatter)
			result += stateInfo.toString() + "\n";

		return result;
	}

	public void capture(StateInfo stateInfo) {
		finishPrevious(stateInfo.getStartTime());
		states.add(stateInfo);
	}

	private void finishPrevious(int endTime) {
		if (!states.isEmpty()) {
			StateInfo latestState = states.get(states.size() - 1);
			latestState.finish(endTime);
		}
	}
}
