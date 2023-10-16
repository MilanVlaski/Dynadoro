package display;

public abstract class Display {

	protected int time;
	protected String state;

	public void setTime(int time) {
		this.time = time;
	}

	public void setState(String state) {
		this.state = state;
	}

	public abstract void update();
}
