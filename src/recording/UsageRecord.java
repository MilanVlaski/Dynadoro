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
			if (newPeriod.date().compareTo(currentPeriod.date()) > 0)
				finishAtMidnightAndStartAgain(currentPeriod);

			finishAndRecord(currentPeriod, newPeriod.startTime());
		}
		currentPeriod = newPeriod;
	}

	private void finishAtMidnightAndStartAgain(Period period)
	{
		LocalTime midnight = LocalTime.of(0, 0, 0);
		LocalDate today = period.date();
		
		finishAndRecord(currentPeriod,
				LocalDateTime.of(today, midnight.minusSeconds(1)));

		currentPeriod = new Period(period.type(),
		        LocalDateTime.of(today.plusDays(1), midnight),
		        period.shouldBeRecorded());
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
