package record;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UsageFile implements File
{

	private static final String APP_NAME = "Dynadoro";
	private static final String userHome = System.getProperty("user.home");
	private static Path path = Paths.get(userHome, APP_NAME, "sessions.txt");

	@Override
	public String read()
	{
		if (Files.notExists(path))
			return "";
		else
			try
			{
				return Files.readString(path);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		return "";
	}

	@Override
	public void write(String text)
	{
		try
		{

			if (Files.notExists(path))
			{
				Files.createDirectories(path.getParent());
				Files.createFile(path);
			} else
			{
				Files.writeString(path, text);
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		UsageFile file = new UsageFile();
		file.read();
		System.out.println(Files.exists(path));
	}
}
