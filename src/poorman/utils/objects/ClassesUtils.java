package poorman.utils.objects;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;
import java.util.Stack;

import poorman.reflection.utils.ReflectionUtils;

public class ClassesUtils {

	public static <T> Object merge(T object, Class<?> classToCreate) {

		Object newObject = null;

		newObject = classToCreate.newInstance();

		List<Field> newObjectFields = ReflectionUtils.getAllFields(newObject);

		Stack<Field> existingFields = ReflectionUtils.getAllFieldsAsStack(object);

		updateNewObject(newObjectFields, existingFields, newObject, object);

		return newObject;
	}

	private static <T> void updateNewObject(List<Field> newObjectFields, Stack<Field> existingFields, Object newObject,
			T existingObject) throws InstantiationException, IllegalAccessException {

		for (Field newField : newObjectFields) {
			for (int i = 0; i < existingFields.size(); i++) {
				if (isMatchingField(newField, existingFields, i)) {
					updateField(newField, existingFields, existingObject, newObject, i);
					continue;
				}
			}
		}
	}

	private static boolean isMatchingField(Field newField, Stack<Field> existingFields, int i) {
		if (newField.getName().equalsIgnoreCase(existingFields.get(i).getName())
				&& newField.getType() == existingFields.get(i).getType()) {
			return true;
		}
		return false;
	}

	private static <T> void updateField(Field newField, Stack<Field> existingFields, T existingObject, Object newObject,
			int i) throws IllegalArgumentException, IllegalAccessException {

		newField.setAccessible(true);
		existingFields.get(i).setAccessible(true);
		newField.set(newObject, existingFields.get(i).get(existingObject));
		existingFields.removeElementAt(i);
	}

	public static Object createObjectFromResultSet(ResultSet rs, Class<?> classType) {

		return null;
	}

}
