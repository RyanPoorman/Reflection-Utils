package poorman.object.utils.tests;

import org.junit.Test;

import poorman.object.utils.tests.resources.Employee;
import poorman.object.utils.tests.resources.Student;
import poorman.utils.reflection.ReflectionUtils;

public class ReflectionUtilsTest {

	@Test
	public void getSetMethodsTest() {
		ReflectionUtils.getSetMethods(Employee.class);
	}

	@Test
	public void getObjectValuesTest() throws IllegalArgumentException, IllegalAccessException {
		ReflectionUtils.getAllFields(new Student("Ryan", "Poorman", 123123, "Junior"));
		// ReflectionUtils.getAllFields(Employee.class);
	}
}
