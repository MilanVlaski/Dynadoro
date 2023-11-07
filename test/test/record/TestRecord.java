package test.record;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void recordShouldBeEmpty() {
		UsageRecord record = new UsageRecord();
		assertEquals("", record.toString());
	}

	@Test
	void stateShouldProvideStateInfoObject() {
		Working working = new Working(timer, 1699368029);
		StateInfo stateInfo = working.info();
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown", stateInfo.toString());
	}
	
	@Test
	void stateShouldProvideStateInfoObject1() {
		Working working = new Working(timer, 1699368029 + 60*5);
		StateInfo stateInfo = working.info();
		assertEquals("2023-11-07, Tuesday, Working, 15:45, unknown", stateInfo.toString());
	}

	// TODO begin then break/pause.
//	@Test
//	void shouldRecordWorking_WhileWorking() {
//		Moment m = new Moment(1700);
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(m.current());
//
//		UsageRecord record = new UsageRecord();
//		timer.startRecording(record);
//
//		timer.begin();
//
//		assertEquals("2023-11-07, Tuesday, Working, 14:00, unknown\n", record.toString());
//	}

//	@Test
//	void shouldRecordWorking_FiveMinutes() {
//		Moment m = new Moment(1700);
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(m.current(), m.after(5 * 60));
//
//		UsageRecord record = new UsageRecord();
//		timer.startRecording(record);
//
//		timer.begin();
//		timer.reset();
//
//		assertEquals("2023-11-07, Tuesday, Working, 14:00, 14:05\n", record.toString());
//	}

}
