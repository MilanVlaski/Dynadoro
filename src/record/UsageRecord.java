package record;

public class UsageRecord {
	
	private StateInfo stateInfo;

	@Override
	public String toString() {
		return stateInfo != null ? stateInfo.toString() + "\n" : "";
	}

	public void capture(StateInfo stateInfo) {
		this.stateInfo = stateInfo;
	}
}
	