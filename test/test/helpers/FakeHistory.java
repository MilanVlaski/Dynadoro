package test.helpers;

import java.util.List;

import recording.*;
import recording.clock.ProductivityClock;

public class FakeHistory implements History
{

	private String contents = "";
	private List<ProductivityClock> clocks;

	public FakeHistory(String contents)
	{ this.contents = contents; }

	public FakeHistory()
	{}

	public FakeHistory(List<Session> states)
	{
		for (Session session : states)
		{
			write(session.toString());
		}
	}

	public FakeHistory(List<ProductivityClock> clocks, List<Session> sessions)
	{
		this(sessions);
		this.clocks = clocks;
	}

	@Override
	public String read()
	{ return contents; }

	@Override
	public void write(String contents)
	{ this.contents += contents + "\n"; }

	@Override
	public List<Session> retrievePeriods()
	{ return UsageHistory.parsePeriods(contents); }

	@Override
	public List<ProductivityClock> retrieveClocks()
	{ return clocks; }

}
