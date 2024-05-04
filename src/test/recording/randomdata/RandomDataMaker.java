package test.recording.randomdata;

import static recording.State.RESTING;
import static recording.State.WORKING;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import recording.Session;

public class RandomDataMaker
{
	List<Session> sessions = new ArrayList();
	Random r = new Random();

	public String make()
	{
		LocalDate today = LocalDate.now();

		for (int i = 0; i < 30; i++)
		{
			createDay(today);
			today = today.plusDays(1);
		}

		return sessions.stream()
		        .map(Session::toString)
		        .collect(Collectors.joining("\n"))
		        .concat("\n");
	}

	private void createDay(LocalDate date)
	{
		int numOfSessions = r.nextInt(1, 6);
		var startTime = LocalTime.of(r.nextInt(9, 13), r.nextInt(0, 59));

		createSessions(date, numOfSessions, startTime);
	}

	private void createSessions(LocalDate date, int numOfSessions, LocalTime startTime)
	{
		for (int i = 0; i < numOfSessions; i++)
		{
			Duration duration = createSession(date, startTime);
			startTime = startTime.plus(duration);
		}
	}

	private Duration createSession(LocalDate date, LocalTime startTime)
	{
		Duration workDuration = Duration.ofMinutes(r.nextInt(10, 90));
		Duration restDuration = workDuration.dividedBy(5);
		LocalTime endTime = startTime.plus(workDuration);

		Session work = new Session(WORKING, date, startTime, endTime);
		Session rest = new Session(RESTING, date, endTime, endTime.plus(restDuration));

		sessions.add(work);
		sessions.add(rest);

		return workDuration.plus(restDuration);
	}

	public static void main(String[] args)
	{
		var maker = new RandomDataMaker();
		System.out.println(maker.make());
	}
}
