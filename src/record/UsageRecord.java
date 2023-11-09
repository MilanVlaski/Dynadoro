package record;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsageRecord
{

	private final List<StateInfo> states = new ArrayList<>();
	private File file;

	public UsageRecord(File file)
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

	public void capture(StateInfo newState)
	{
		if (!states.isEmpty())
		{
			StateInfo previousState = states.get(states.size() - 1);
			finishAndWrite(previousState, newState.startTime());
		}
		states.add(newState);
	}

	private void finishAndWrite(StateInfo previousState, int endTime)
	{
		previousState.finish(endTime);

		if (previousState.shouldBeRecorded())
			file.write(previousState.toString() + "\n");
	}
}
