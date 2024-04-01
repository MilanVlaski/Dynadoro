package recording;

import java.util.List;

import recording.clock.ProductivityClock;

public interface History
{
	String read();
	void write(String text);
	List<Period> retrievePeriods();
	List<ProductivityClock> retrieveClocks();
}
