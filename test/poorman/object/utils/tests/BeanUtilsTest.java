package poorman.object.utils.tests;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import poorman.object.utils.tests.resources.Employee;
import poorman.object.utils.tests.resources.Student;
import poorman.utils.bean.BeanUtils;

public class BeanUtilsTest {

	static Student student = null;

	@BeforeClass
	public static void setup() {
		student = new Student("Ryan", "Poorman", 1234, "Senior");
	}

	@Test
	public void mergeTest() {
		Employee emp = BeanUtils.merge(student, Employee.class);
		Assert.assertEquals("Ryan", emp.getFirstName());
		Assert.assertEquals("Poorman", emp.getLastName());
	}

	@Test
	public void deepCopyTest() {
		Student studentB = (Student) BeanUtils.deepCopy(student);
		Assert.assertNotEquals(studentB, student);
		Assert.assertEquals("Ryan", studentB.getFirstName());
		Assert.assertEquals("Poorman", studentB.getLastName());
	}

}
