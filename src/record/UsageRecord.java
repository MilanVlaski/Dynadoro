package record;

import java.time.LocalDateTime;

public class UsageRecord
{

	private final History history;
	private StateData previousState;

	public UsageRecord(History history)
	{ this.history = history; }

	@Override
	public String toString()
	{
		if (previousState != null)
			return previousState.toString();
		else
			return "";
	}

	public void capture(StateData newState)
	{
		if (previousState == null)
		{
			previousState = newState;
		}
		else
		{
			finishAndWrite(previousState, newState.startTime());
			previousState = null;
		}
	}

	private void finishAndWrite(StateData previousState, LocalDateTime endTime)
	{
		previousState.finish(endTime);

		if (previousState.shouldBeRecorded())
			history.write(previousState.toString());
	}

}
