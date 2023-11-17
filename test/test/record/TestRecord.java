package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.TestTimer.WORK_DURATION;

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
import test.helpers.FakeHistory;
import test.helpers.Moment;
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
	void stateDataShouldWriteProperly()
	{
		StateData stateData = new StateData("Working", moment.current(), true);
		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown",
		        stateData.toString());
	}

	@Test
	void stateDataShouldWriteProperly_AfterItsFinished()
	{
		StateData stateData = new StateData("Working", moment.current(), true);
		stateData.finish(moment.afterMinutes(5));
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:45",
		        stateData.toString());
	}

	@Test
	void shouldRecordWorking_WhileWorking()
	{
		timer.begin(moment.current());

		assertEquals("2023-11-07, Tuesday, Working, 15:40, unknown", record.toString());
		assertEquals("", fakeHistory.read());
	}

	@Test
	void shouldRecordWorking_FiveMinutes()
	{
		timer.begin(moment.current());
		timer.reset(moment.afterMinutes(5));

		assertEquals("2023-11-07, Tuesday, Idle, 15:45, unknown", record.toString());
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:45\n", fakeHistory.read());
	}

	@Test
	void shouldWriteToFile_WithDataInIt_Correctly()
	{
		String previousData = "data-data-data\n";
		History fakeHistory = new FakeHistory(previousData);
		UsageRecord record = new UsageRecord(fakeHistory);
		//

		timer.startRecording(record);
		timer.begin(moment.current());
		timer.reset(moment.afterMinutes(5));

		assertEquals("2023-11-07, Tuesday, Idle, 15:45, unknown", record.toString());
		assertEquals(previousData + "2023-11-07, Tuesday, Working, 15:40, 15:45\n",
		        fakeHistory.read());
	}

	@Test
	void shouldRecordWorking_ThenResting()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterMinutes(WORK_DURATION));

		assertEquals("2023-11-07, Tuesday, Resting, 16:05, unknown",
		        record.toString());
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n",
		        fakeHistory.read());
	}
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