package test.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import record.Day;
import record.History;
import record.Period;

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
	public List<Day> retrieveDays()
	{

		if ("".equals(contents))
			return Collections.emptyList();
		else
		{
			
			return new ArrayList<>();
		}
	}

}
