package poorman.utils.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class ReflectionUtils {

	public static <T> List<Method> getSetMethods(Class<T> classToCreate) {

		return Arrays.asList(classToCreate.getDeclaredMethods()).stream()
				.filter(method -> method.getName().contains("set")).collect(Collectors.toList());
	}

	public static <T> List<Field> getAllFields(T object) throws IllegalArgumentException, IllegalAccessException {
		return getAllFields(object.getClass());
	}

	public static <T> Stack<Field> getAllFieldsAsStack(T object)
			throws IllegalArgumentException, IllegalAccessException {

		Stack<Field> stack = new Stack<Field>();
		stack.addAll(getAllFields(object.getClass()));
		return stack;
	}

	public static <T> List<Field> getAllFields(Class<T> classType) {
		List<Field> allFields = new ArrayList<Field>();

		for (Class<?> c = classType; c != null; c = c.getSuperclass()) {
			allFields.addAll(Arrays.asList(c.getDeclaredFields()).stream().collect(Collectors.toList()));
		}

		return allFields;
	}

}
