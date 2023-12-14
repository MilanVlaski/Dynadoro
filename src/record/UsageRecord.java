package record;

import java.time.LocalDateTime;

public class UsageRecord
{

	private final History history;
	private Period currentPeriod;

	public UsageRecord(History history)
	{ this.history = history; }

	public void capture(Period newPeriod)
	{
		finishAndWriteCurrent(newPeriod.startTime());

		currentPeriod = newPeriod;
	}

	@Override
	public String toString()
	{
		if (currentPeriod != null)
			return currentPeriod.toString();
		else
			return "";
	}

	public void finishAndWriteCurrent(LocalDateTime now)
	{
		if (currentPeriod != null)
		{
			currentPeriod.finish(now);

			if (currentPeriod.shouldBeRecorded())
				history.write(currentPeriod.toString());
		}
	}
}
