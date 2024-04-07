package recording;

import java.util.List;

public interface History2
{
	List<Period> getSessions();
	void capture(Period period);
}
