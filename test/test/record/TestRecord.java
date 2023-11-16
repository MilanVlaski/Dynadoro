package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import record.History;
import record.StateData;
import record.UsageRecord;
import test.Moment;
import test.TestTimer;
import timer.Timer;
import timer.counter.Counter;

class TestRecord
{

	@Mock
	Display dummyDisplay;
	@Mock
	Counter dummyCounter;

	@InjectMocks
	Timer timer;

	//
	Moment moment;
	UsageRecord record;
	History fakeHistory;

	
	@BeforeEach
	void setup()
	{
		MockitoAnnotations.openMocks(this);
		
		fakeHistory = new FakeHistory();
		record = new UsageRecord(fakeHistory);
		timer.startRecording(record);
		
		
		moment = new Moment(LocalDateTime.of(2023, 11, 7, 15, 40));
	}

	@Test
	void recordShouldBeEmpty()
	{ assertEquals("", record.toString()); }

	@Test
	void stateShouldProvideStateInfoObject()
	{
		StateData stateData = new StateData("Working", moment.current(), true);
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown",
		        stateData.toString());
	}
//
//	@Test
//	void stateShouldProvideStateInfoObject1()
//	{
//		StateData stateData = new StateData(State.WORKING, moment.after(60 * 5));
//		assertEquals("2023-11-07, Tuesday, Working, 15:45, unknown",
//		        stateData.toString());
//	}
//
//	@Test
//	void shouldRecordWorking_WhileWorking() {
//		when(mockClock.currentTimeSeconds()).thenReturn(moment.current());
//
//		timer.begin();
//
//		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown\n", record.toString());
//		assertEquals("", fakeHistory.read());
//	}
//
//	@Test
//	void shouldRecordWorking_FiveMinutes() {
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(moment.current(), moment.after(5 * 60));
//
//		timer.begin();
//		timer.reset();
//
//		String expected = "2023-11-07, Tuesday, Working, 15:40, 15:45\n";
//		assertEquals(expected, record.toString());
//		assertEquals(expected, fakeHistory.read());
//	}
//
//	@Test
//	void shouldWriteToNonEmptyFileCorrectly()
//	{
//		String previousData = "data-data-data\n";
//		History fakeHistory = new FakeHistory(previousData);
//		UsageRecord record = new UsageRecord(fakeHistory);
//
//		when(mockClock.currentTimeSeconds())
//		        .thenReturn(moment.current(), moment.after(5 * 60));
//		//
//
//		timer.startRecording(record);
//		timer.begin();
//		timer.reset();
//
//		String runtimeData = "2023-11-07, Tuesday, Working, 15:40, 15:45\n";
//		assertEquals(runtimeData, record.toString());
//		assertEquals(previousData + runtimeData, fakeHistory.read());
//	}
//
//	@Test
//	void shouldRecordWorking_TwentyFive_ThenRestingFiveMinutes() {
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(moment.current(), moment.after(TWENTY_FIVE * 60));
//
//		timer.begin();
//		timer.rest();
//
//		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
//				+ "2023-11-07, Tuesday, Resting, 16:05, unknown\n", record.toString());
//	}
//
//	@Test
//	void shouldRecordWorkAndThenRest() {
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(moment.current(), moment.after(TWENTY_FIVE * 60),
//							moment.after(REST_DURATION * 60));
//
//		timer.begin();
//		timer.rest();
//		timer.reset();
//
//		String expected = "2023-11-07, Tuesday, Working, 15:40, 16:05\n"
//				+ "2023-11-07, Tuesday, Resting, 16:05, 16:10\n";
//		assertEquals(expected, record.toString());
//		assertEquals(expected, fakeHistory.read());
//	}
//
//	@Test
//	void shouldRecordWorkAndThenRest_IfRestIsntCancelled() {
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(moment.current(), moment.after(TWENTY_FIVE * 60),
//							moment.after(8 * 60));
//
//		timer.begin();
//		timer.rest();
//		timer.reset();
//
//		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
//				+ "2023-11-07, Tuesday, Resting, 16:05, 16:13\n", record.toString());
//	}
//
//	@Test
//	void shouldRecordAFinishedWorkSession_BecauseOfPause() {
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(moment.current(), moment.after(3 * 60));
//
//		timer.begin();
//		timer.pause();
//		
//		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:43\n", record.toString());
//	}
//
//	@Test
//	void shouldRecordWorkTwice_AfterPartuseAndResume() {
//		when(mockClock.currentTimeSeconds())
//				.thenReturn(moment.current(), moment.after(3 * 60),
//						moment.after(2 * 60));
//		
//		timer.begin();
//		timer.pause();
//		timer.resume();
//		
//		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:43\n"
//				+ "2023-11-07, Tuesday, Working, 15:45, unknown\n", record.toString());
//	}

}