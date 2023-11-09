package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.TestTimer.BREAK_DURATION;
import static test.TestTimer.TWENTY_FIVE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import record.StateInfo;
import record.UsageFile;
import record.UsageRecord;
import record.StateInfo.State;
import test.TestTimer.Moment;
import timer.Clock;
import timer.Timer;
import timer.counter.Counter;

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
	UsageFile mockEmptyFile = new EmptyFile();

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		moment = new Moment(1699368029);
		
		record = new UsageRecord(mockEmptyFile);
		timer.startRecording(record);
	}

	@Test
	void recordShouldBeEmpty() {
		assertEquals("", record.toString());
	}

	@Test
	void stateShouldProvideStateInfoObject() {
		StateInfo stateInfo = new StateInfo(State.WORKING, moment.current());
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown", stateInfo.toString());
	}

	@Test
	void stateShouldProvideStateInfoObject1() {
		StateInfo stateInfo = new StateInfo(State.WORKING, moment.after(60 * 5));
		assertEquals("2023-11-07, Tuesday, Working, 15:45, unknown", stateInfo.toString());
	}

	@Test
	void shouldRecordWorking_WhileWorking() {
		when(mockClock.currentTimeSeconds()).thenReturn(moment.current());

		timer.begin();

		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown\n", record.toString());
		assertEquals("", mockEmptyFile.read());
	}

	@Test
	void shouldRecordWorking_FiveMinutes() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(5 * 60));

		timer.begin();
		timer.reset();

		String expected = "2023-11-07, Tuesday, Working, 15:40, 15:45\n";
		assertEquals(expected, record.toString());
		assertEquals(expected, mockEmptyFile.read());
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

	@Test
	void shouldRecordWorkAndThenBreak() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE * 60),
							moment.after(BREAK_DURATION * 60));

		timer.begin();
		timer.takeBreak();
		timer.reset();

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
				+ "2023-11-07, Tuesday, Break, 16:05, 16:10\n", record.toString());
	}
	
	@Test
	void shouldRecordWorkAndThenBreak_IfBreakIsntCancelled() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(TWENTY_FIVE * 60),
							moment.after(8 * 60));

		timer.begin();
		timer.takeBreak();
		timer.reset();

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
				+ "2023-11-07, Tuesday, Break, 16:05, 16:13\n", record.toString());
	}
	
	@Test
	void shouldRecordAFinishedWorkSession_BecauseOfPause() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(3 * 60));

		timer.begin();
		timer.pause();
		
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:43\n", record.toString());
	}

	@Test
	void shouldRecordWorkTwice_AfterPauseAndResume() {
		when(mockClock.currentTimeSeconds())
				.thenReturn(moment.current(), moment.after(3 * 60),
						moment.after(2 * 60));

		timer.begin();
		timer.pause();
		timer.resume();
		
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:43\n"
				+ "2023-11-07, Tuesday, Working, 15:45, unknown\n", record.toString());
	}
}
