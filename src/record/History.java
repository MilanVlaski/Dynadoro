package record;

import java.util.List;

import record.clock.ProductivityClock;

public interface History
{
	String read();
	void write(String text);
	List<Period> retrievePeriods();
	List<ProductivityClock> retrieveClocks();
}
