package recording;

import java.time.*;

public class UsageRecord
{

	private final History history;
	private Period currentPeriod;

	public UsageRecord(History history)
	{ this.history = history; }

	public void capture(Period newPeriod)
	{
		if (currentPeriod != null)
		{
			if (!(newPeriod.date().compareTo(currentPeriod.date()) > 0))
			{
				finishAndRecord(currentPeriod, newPeriod.startTime());
			}
			else
			{
				LocalTime midnight = LocalTime.of(0, 0, 0);
				LocalDate today = currentPeriod.date();

				finishAndRecord(currentPeriod,
				        LocalDateTime.of(today, midnight.minusSeconds(1)));

				finishAndRecord(new Period(currentPeriod.type(),
				        LocalDateTime.of(today.plusDays(1), midnight),
				        currentPeriod.shouldBeRecorded()), newPeriod.startTime());
			}
		}
		currentPeriod = newPeriod;
	}

	private void finishAndRecord(Period period, LocalDateTime at)
	{
		period.finish(at);
		if (period.shouldBeRecorded())
			history.write(period.toString());
	}

	public void finishAndRecordCurrentNow(LocalDateTime now)
	{
		if (currentPeriod != null)
			finishAndRecord(currentPeriod, now);
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
