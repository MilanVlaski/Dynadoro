package recording;

import java.util.List;

public class RealHistory implements History2
{

	private final History history;

	public RealHistory(History history)
	{
		this.history = history;
	}

	@Override
	public List<Period> getSessions()
	{
		return history.retrievePeriods();
	}

	@Override
	public void capture(Period period)
	{
		history.write(period.toString());
	}

}
