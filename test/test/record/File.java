package test.record;

import record.UsageFile;

public class File implements UsageFile {

	private String contents = "";

	public File(String contents) {
		this.contents = contents;
	}

	@Override
	public String read() {
		return contents;
	}

	@Override
	public void write(String contents) {
		this.contents += contents;
	}

}
