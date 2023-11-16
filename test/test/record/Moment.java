package test.record;

public class Moment
{
	// number is randomly picked, because currentTime() returns a random number
	private int currentTime = 36;

	public Moment()
	{}

	public Moment(int currentTime)
	{ this.currentTime = currentTime; }

	public int after(int seconds)
	{ return currentTime += seconds; }

	public int current()
	{ return currentTime; }
}
