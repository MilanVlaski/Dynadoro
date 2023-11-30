package timer.state;

import java.time.LocalDateTime;

public interface Resumable
{
	void resume(LocalDateTime now, int from);
}
