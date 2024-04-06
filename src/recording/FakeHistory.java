package recording;

import java.util.Collections;
import java.util.List;
/**
 * Doesn't interact with the file system, instead, stores Sessions in a list.
 */
public class FakeHistory implements History2
{

	@Override
	public List<Period> getSessions()
	{ 
		return Collections.emptyList();
	 }

}
