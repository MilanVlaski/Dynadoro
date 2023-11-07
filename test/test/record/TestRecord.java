package test.record;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static test.TestTimer.TWENTY_FIVE;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import record.StateInfo;
import record.UsageRecord;
import test.TestTimer.Moment;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;
import timer.state.Working;

class TestRecord {

	@Mock
	Clock mockClock;
	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	Moment moment;
	UsageRecord record;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		moment = new Moment(1699368029);
		record = new UsageRecord();
		timer.startRecording(record);
	}

	@Test
	void recordShouldBeEmpty() {
		UsageRecord record = new UsageRecord();
		assertEquals("", record.toString());
	}

	@Test
	void stateShouldProvideStateInfoObject() {
		StateInfo stateInfo = new StateInfo("Working", moment.current());
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown", stateInfo.toString());
	}

	@Test
	void stateShouldProvideStateInfoObject1() {
		StateInfo stateInfo = new StateInfo("Working", moment.after(60 * 5));
		assertEquals("2023-11-07, Tuesday, Working, 15:45, unknown", stateInfo.toString());
	}

	// TODO begin, then break/pause.
	@Test
	void shouldRecordWorking_WhileWorking() {
		when(mockClock.currentTimeSeconds()).thenReturn(moment.current());

		timer.begin();

		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown\n", record.toString());
	}

	@Test
	void shouldRecordWorking_FiveMinutes() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(5 * 60));

		timer.begin();
		timer.reset();

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:45\n", record.toString());
	}

	@Test
	void shouldRecordWorking_TwentyFive_ThenTakingBreakFive() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE * 60));

		timer.begin();
		timer.takeBreak();

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
				+ "2023-11-07, Tuesday, Break, 16:05, unknown\n", record.toString());
	}

}
