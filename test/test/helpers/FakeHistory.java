package test.helpers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import record.Day;
import record.History;
import record.StateData;

public class FakeHistory implements History
{

	private String contents = "";

	public FakeHistory(String contents)
	{ this.contents = contents; }

	public FakeHistory()
	{}

	public FakeHistory(List<StateData> states)
	{
		for (StateData stateData : states)
			write(stateData.toString());
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
		return Collections.emptyList();

	}

}
