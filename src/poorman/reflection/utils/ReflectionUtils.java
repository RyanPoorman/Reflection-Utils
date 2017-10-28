package poorman.reflection.utils;

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

	public static <T> List<Method> getSetMethods(T object) {
		return getSetMethods(object.getClass());
	}

	public static <T> List<Field> getAllFields(T object) {
		return fetchAllFields(object.getClass());
	}

	public static <T> List<Field> getAllFields(Class<T> classType) {
		return fetchAllFields(classType);
	}

	public static <T> Stack<Field> getAllFieldsAsStack(T object)
			throws IllegalArgumentException, IllegalAccessException {

		Stack<Field> stack = new Stack<Field>();
		stack.addAll(getAllFields(object.getClass()));
		return stack;
	}

	private static <T> List<Field> fetchAllFields(Class<T> classType) {
		List<Field> allFields = new ArrayList<Field>();

		try {
			for (Class<?> c = classType; c != null; c = c.getSuperclass()) {
				allFields.addAll(Arrays.asList(c.getDeclaredFields()).stream().collect(Collectors.toList()));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		return allFields;
	}

}
