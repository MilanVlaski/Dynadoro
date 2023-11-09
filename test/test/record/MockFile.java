package test.record;

import record.UsageFile;

public class MockFile implements UsageFile
{

	private String contents = "";

	public MockFile(String contents)
	{ this.contents = contents; }

	public MockFile()
	{}

	@Override
	public String read()
	{ return contents; }

	@Override
	public void write(String contents)
	{ this.contents += contents; }

}
