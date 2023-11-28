package record;

import java.util.List;

public interface History
{
	String read();
	void write(String text);
	List<Period> retrievePeriods();
	List<ProductivityClock> retrieveClocks();
}
