package poorman.object.utils.tests;

import org.junit.Test;

import poorman.object.utils.tests.resources.Employee;
import poorman.object.utils.tests.resources.Student;
import poorman.utils.reflection.ClassesUtils;

public class MergeClassesTest {

	@Test
	public void mergeTest() {
		Student s = new Student("Ryan", "Poorman", 123123, "Junior");
		s.setBodyHeight(72);
		ClassesUtils.merge(s, Employee.class);

	}
}
