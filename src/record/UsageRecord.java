package record;

import java.time.LocalDateTime;

public class UsageRecord
{

	private final History history;
	private Period currentState;

	public UsageRecord(History history)
	{ this.history = history; }

	@Override
	public String toString()
	{
		if (currentState != null)
			return currentState.toString();
		else
			return "";
	}

	public void capture(Period newState)
	{
		if (currentState != null)
			finishAndWrite(currentState, newState.startTime());

		currentState = newState;
	}

	private void finishAndWrite(Period previousState, LocalDateTime endTime)
	{
		previousState.finish(endTime);

		if (previousState.shouldBeRecorded())
			history.write(previousState.toString());
	}

}
