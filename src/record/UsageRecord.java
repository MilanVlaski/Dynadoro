package record;

import java.time.LocalDateTime;

public class UsageRecord
{

	private final History history;
	private Period currentPeriod;

	public UsageRecord(History history)
	{ this.history = history; }

	@Override
	public String toString()
	{
		if (currentPeriod != null)
			return currentPeriod.toString();
		else
			return "";
	}

	public void capture(Period newPeriod)
	{
		if (currentPeriod != null)
		{
			currentPeriod.finish(newPeriod.startTime());
			
			if (currentPeriod.shouldBeRecorded())
				history.write(currentPeriod.toString());
		}
		currentPeriod = newPeriod;
	}

}
