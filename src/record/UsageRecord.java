package record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsageRecord {

	private final List<StateInfo> states = new ArrayList<>();

	@Override
	public String toString() {
		if (states.isEmpty())
			return "";
		else
			return states.stream()
					.filter((state) -> state.getsRecorded())
					.map(StateInfo::toString)
					.collect(Collectors.joining("\n", "", "\n"));
	}

	public void capture(StateInfo stateInfo) {
		finishPrevious(stateInfo.getStartTime());
		states.add(stateInfo);
	}

	private void finishPrevious(int endTime) {
		if (!states.isEmpty()) {
			StateInfo latestState = states.get(states.size() - 1);
			latestState.finish(endTime);
//			write the latest state to file, because its finished!
		}
	}
}
