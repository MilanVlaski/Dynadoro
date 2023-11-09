package record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsageRecord
{

	private final List<StateInfo> states = new ArrayList<>();
	private UsageFile file;

	public UsageRecord(UsageFile file)
	{ this.file = file; }

	@Override
	public String toString()
	{
		if (states.isEmpty())
			return "";
		else
			return states.stream()
			        .filter((state) -> state.shouldBeRecorded())
			        .map(StateInfo::toString)
			        .collect(Collectors.joining("\n", "", "\n"));
	}

	public void capture(StateInfo stateInfo)
	{
		if (!states.isEmpty())
		{
			StateInfo latestState = states.get(states.size() - 1);
			finishAndWrite(latestState, stateInfo.getStartTime());
		}
		states.add(stateInfo);
	}

	private void finishAndWrite(StateInfo latestState, int endTime)
	{
		latestState.finish(endTime);

		if (latestState.shouldBeRecorded())
			file.write(latestState.toString() + "\n");
	}
}
