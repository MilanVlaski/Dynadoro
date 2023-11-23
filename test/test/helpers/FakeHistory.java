package test.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import record.History;
import record.Period;
import record.Period.State;

public class FakeHistory implements History
{

	private String contents = "";

	public FakeHistory(String contents)
	{ this.contents = contents; }

	public FakeHistory()
	{}

	public FakeHistory(List<Period> states)
	{
		for (Period period : states)
			write(period.toString());
	}

	@Override
	public String read()
	{ return contents; }

	@Override
	public void write(String contents)
	{ this.contents += contents + "\n"; }

	@Override
	public List<Period> retrievePeriods()
	{
		List<Period> result = new ArrayList<>();
		parse(contents, result);
		return result;
	}

	private static void parse(CharSequence input, List<Period> list)
	{
		String regex = "(\\d+\\-\\d+\\-\\d+),\\s*(\\w+),\\s*(\\w+),\\s(\\d+:\\d+).*?(\\d+:\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find())
		{
			String dateString = matcher.group(1);
			String stateString = matcher.group(3);
			String startTimeString = matcher.group(4);
			String endTimeString = matcher.group(5);

			LocalDate date = LocalDate.parse(dateString, Period.dateFormat);
			LocalTime startTime = LocalTime.parse(startTimeString, Period.hourFormat);
			LocalTime endTime = LocalTime.parse(endTimeString, Period.hourFormat);

			LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
			LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

			list.add(new Period(State.of(stateString),
			        startDateTime, endDateTime));
		}
	}
}
