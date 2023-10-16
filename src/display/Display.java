package display;

public abstract class Display {

	public enum DisplayState {
		IDLE,
		WORKING,
		TAKING_BREAK;
	}
	
	protected int time;
	protected DisplayState state;

	public void setTime(int time) {
		this.time = time;
	}

	public void setState(DisplayState state) {
		this.state = state;
	}

	public abstract void update();
}
