package record;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UsageRecord
{

	private StateData previousState;
	private History history;

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
		} else
		{
			previousState.finish(newState.startTime());
			
			if (previousState.shouldBeRecorded())
				history.write(previousState.toString());
			
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
