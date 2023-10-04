package timer.state;

import timer.Timer.TimerState;

public interface TimerStateI {
	int time();
	void begin();
	void takeBreak();
}
