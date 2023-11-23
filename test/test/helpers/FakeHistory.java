package test.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.text.DateFormatter;

import record.Day;
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
		if (contents.equals(""))
			return Collections.emptyList();
		else
		{
			System.out.println("CONTENTS: " + contents);
			List<Period> result = new ArrayList<>();

			String regex = "(\\d+\\-\\d+\\-\\d+).*?([A-Za-z]+).*?(\\d+:\\d+).*?(\\d+:\\d+)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(contents);

			while (matcher.find())
			{
				String dateString = matcher.group(1);
				String activity = matcher.group(2);
				String startString = matcher.group(3);
				String endString = matcher.group(4);

				System.out.println(
				        String.join(" ", dateString, startString, activity, endString));

				LocalDate date = LocalDate.parse(dateString,
				        DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				LocalTime start = LocalTime.parse(startString,
				        DateTimeFormatter.ofPattern("HH:mm"));
				LocalTime end = LocalTime.parse(endString,
				        DateTimeFormatter.ofPattern("HH:mm"));

				LocalDateTime startDateTime = LocalDateTime.of(date, start);
				LocalDateTime endDateTime = LocalDateTime.of(date, end);

				result.add(new Period(State.WORKING, startDateTime, endDateTime));
			}

			return result;
		}
	}

}
