package recording;

import java.time.LocalDateTime;

public class UsageRecord
{

	private final History history;
	private Period currentPeriod;

	public UsageRecord(History history)
	{ this.history = history; }

	public void capture(Period newPeriod)
	{
		finishAndRecordCurrent(newPeriod.startTime());
		currentPeriod = newPeriod;
	}

	public void finishAndRecordCurrent(LocalDateTime now)
	{
		if (currentPeriod != null)
		{
			currentPeriod.finish(now);
		
			if (currentPeriod.shouldBeRecorded())
				history.write(currentPeriod.toString());
		}
	}

	@Override
	public String toString()
	{
		if (currentPeriod != null)
			return currentPeriod.toString();
		else
			return "";
	}

}
