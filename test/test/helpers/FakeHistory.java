package test.helpers;

import java.util.List;

import record.*;

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

	public FakeHistory(ProductivityClock productivityClock)
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public String read()
	{ return contents; }

	@Override
	public void write(String contents)
	{ this.contents += contents + "\n"; }

	@Override
	public List<Period> retrievePeriods()
	{ return UsageHistory.parsePeriods(contents); }

	@Override
	public List<ProductivityClock> retrieveClocks()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
