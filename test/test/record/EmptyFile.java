package test.record;

import record.UsageFile;

public class EmptyFile implements UsageFile {

	private String contents = "";

	public String read() {
		return contents;
	}

	public void write(String contents) {
		this.contents += contents;
	}

	
}
