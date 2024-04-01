package recording;

import java.util.List;
import java.util.Optional;

public enum State
{

	IDLE("Idle"),
	WORKING("Working"),
	RESTING("Resting"),
	PAUSE("Pause");

	String name;

	State(String name)
	{ this.name = name; }

	public static Optional<State> of(String name)
	{
		return List.of(values()).stream()
		        .filter((s) -> name.toLowerCase().equals(s.name.toLowerCase()))
		        .findAny();
	}
}