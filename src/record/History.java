package record;

import java.util.List;

public interface History
{
	String read();
	void write(String text);
	List<Day> retrieveDays();
}
