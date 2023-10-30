package display;

public abstract class Display {

	public enum DisplayState {
		IDLE,
		WORKING,
		TAKING_BREAK,
		WORK_PAUSE,
		BREAK_PAUSE;
	}
	
	protected int time;
	protected DisplayState state;

	public void setTime(int time) {
		this.time = time;
	}

	public void setState(DisplayState state) {
		this.state = state;
	}

	public abstract void show(int displayedTime);
	public abstract void show(int displayedTime, DisplayState idle);
}
