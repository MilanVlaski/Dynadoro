package test.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static test.TestTimer.REST_DURATION;
import static test.TestTimer.WORK_DURATION;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import display.Display;
import record.History;
import record.UsageRecord;
import test.helpers.FakeHistory;
import test.helpers.Moment;
import timer.Timer;
import timer.counter.Counter;

class TestRecording
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
	void shouldRecordWorking_AndStartOfResting()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterMinutes(WORK_DURATION));

		assertEquals("2023-11-07, Tuesday, Resting, 16:05, unknown",
		        record.toString());
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n",
		        fakeHistory.read());
	}

	@Test
	void shouldRecordWork_AndRest()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterMinutes(WORK_DURATION));
		timer.reset(moment.afterMinutes(REST_DURATION));

		assertEquals("2023-11-07, Tuesday, Idle, 16:10, unknown", record.toString());
		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
		        + "2023-11-07, Tuesday, Resting, 16:05, 16:10\n", fakeHistory.read());
	}

	@Test
	void shouldRecordWorkAndRest_EvenIfRestingForLongerThanAllowed()
	{
		timer.begin(moment.current());
		timer.rest(moment.afterMinutes(WORK_DURATION));
		timer.reset(moment.afterMinutes(60));

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 16:05\n"
		        + "2023-11-07, Tuesday, Resting, 16:05, 17:05\n", fakeHistory.read());
	}

	@Test
	void shouldRecordAFinishedWorkSession_BecauseOfPause()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterMinutes(3));

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:43\n", fakeHistory.read());
	}

	@Test
	void shouldRecordWorkTwice_BeforeAndAfterPause()
	{
		timer.begin(moment.current());
		timer.pause(moment.afterMinutes(2));
		timer.resume(moment.afterMinutes(3));
		timer.reset(moment.afterMinutes(4));

		assertEquals("2023-11-07, Tuesday, Working, 15:40, 15:42\n"
		        + "2023-11-07, Tuesday, Working, 15:45, 15:49\n", fakeHistory.read());
	}

}