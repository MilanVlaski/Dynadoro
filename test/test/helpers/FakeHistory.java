package test.helpers;

import java.util.List;

import record.*;
import record.clock.ProductivityClock;

public class FakeHistory implements History
{

	private String contents = "";
	private List<ProductivityClock> clocks;

	public FakeHistory(String contents)
	{ this.contents = contents; }

	public FakeHistory()
	{}

	public FakeHistory(List<Period> states)
	{
		for (Period period : states)
			write(period.toString());
	}

	public FakeHistory(List<ProductivityClock> clocks, List<Period> periods)
	{
		this(periods);
		this.clocks = clocks;
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
	{ return clocks; }

}
