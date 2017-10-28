import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List collection = new ArrayList();
		collection.add(1);
		collection.add(3);

		Object x = collection;

		List test = (List) x;

		for (Iterator iterator = test.iterator(); iterator.hasNext();) {
			System.out.println(iterator.next());

		}

	}

}
