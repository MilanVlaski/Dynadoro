package record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsageRecord
{

	private final List<StateData> states = new ArrayList<>();
	private History history;

	public UsageRecord(History history)
	{ this.history = history; }

	@Override
	public String toString()
	{
		if (states.isEmpty())
			return "";
		else
			return states.stream()
			        .filter((state) -> state.shouldBeRecorded())
			        .map(StateData::toString)
			        .collect(Collectors.joining("\n", "", "\n"));
	}

	public void capture(StateData newState)
	{
		if (!states.isEmpty())
		{
			StateData previousState = states.get(states.size() - 1);
			finishAndWrite(previousState, newState.startTime());
		}
		states.add(newState);
	}

	private void finishAndWrite(StateData previousState, int endTime)
	{
		previousState.finish(endTime);

		if (previousState.shouldBeRecorded())
			history.write(previousState.toString());
	}
}
