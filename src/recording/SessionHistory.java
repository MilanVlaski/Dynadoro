package recording;

import java.nio.file.*;
import java.util.Collections;
import java.util.List;

public class SessionHistory implements History2
{

	private static final String userHome = System.getProperty("user.home");
	private static final String appName = "Dynadoro";
	private static final Path directory = Paths.get(userHome, appName);

	private final Path sessionsFile;

	public SessionHistory(String fileName)
	{
		sessionsFile = directory.resolve(fileName + ".json");
	}

	@Override
	public List<Period> getSessions()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void capture(Period period)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public List<Day> getDays()
	{
		if (Files.notExists(sessionsFile))
			return Collections.emptyList();
		return null;
	}

}
