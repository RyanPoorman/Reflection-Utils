package poorman.utils.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import poorman.utils.reflection.ReflectionUtils;

public class BeanUtils {

	/**
	 * Returns a new object that has been instantiated based upon the specified
	 * type. This object is then populated from the fields that were of matching
	 * names in the originally instantiated object.
	 *
	 * @param object
	 *            an already instantiated object
	 * @param classToCreate
	 *            the object type to be created
	 * @return A new populated object of specified type
	 * 
	 * @author Ryan Poorman
	 */
	public static <T> T merge(Object object, Class<T> beanToCreate) {

		T bean = null;

		try {
			bean = beanToCreate.newInstance();

			List<Field> beanFields = ReflectionUtils.getAllFields(bean);

			Stack<Field> objectFields = ReflectionUtils.getAllFieldsAsStack(object);

			populateNewObject(beanFields, objectFields, bean, object);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return bean;
	}

	private static <T> void populateNewObject(List<Field> beanFields, Stack<Field> objectFields, Object newBean,
			T existingObject) throws InstantiationException, IllegalAccessException {

		for (Field field : beanFields) {
			for (int i = 0; i < objectFields.size(); i++) {
				if (isMatchingField(field, objectFields, i)) {
					updateField(field, objectFields, existingObject, newBean, i);
					continue;
				}
			}
		}
	}

	private static boolean isMatchingField(Field beanField, Stack<Field> objectField, int i) {
		if (namesMatch(beanField, objectField, i) && typesMatch(beanField, objectField, i)
				&& !isSerialNumber(beanField)) {
			return true;
		}
		return false;
	}

	private static boolean isSerialNumber(Field beanField) {
		return beanField.getName().equalsIgnoreCase("serialVersionUID");
	}

	private static boolean typesMatch(Field beanField, Stack<Field> objectField, int i) {
		return beanField.getType() == objectField.get(i).getType();
	}

	private static boolean namesMatch(Field beanField, Stack<Field> objectField, int i) {
		return beanField.getName().equalsIgnoreCase(objectField.get(i).getName());
	}

	private static <T> void updateField(Field beanField, Stack<Field> existingFields, T existingObject, Object newBean,
			int i) throws IllegalArgumentException, IllegalAccessException {

		beanField.setAccessible(true);
		existingFields.get(i).setAccessible(true);
		beanField.set(newBean, existingFields.get(i).get(existingObject));
		existingFields.removeElementAt(i);
	}

	
	/**
	 * Returns a copy of the object, or null if the object cannot be serialized.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deepCopy(Object obj) {

		Object clonedObj = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bin = null;
		ObjectInputStream ois = null;
		ByteArrayOutputStream bos = null;

		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bin = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bin);
			clonedObj = ois.readObject();
		} catch (Exception e) {

		} finally {
			try {
				oos.close();
				ois.close();
				bos.close();
				bin.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return clonedObj;
	}

	/**
	 * Returns all of the fields and methods that are included in the specified
	 * object
	 *
	 * @param Object
	 *            of any type
	 * @return A formatted String containing information about object
	 * 
	 * @author Ryan Poorman
	 */
	public static <T> String toString(T obj) {
		StringBuffer sb = new StringBuffer(obj.getClass().getSimpleName().toUpperCase());
		final String COLON = ":";
		addFields(obj, COLON, sb);
		addMethods(obj, COLON, sb);
		return sb.toString();
	}

	private static <T> void addFields(T obj, String COLON, StringBuffer sb) {
		List<Field> fields = ReflectionUtils.getAllFields(obj);

		for (Field field : fields) {
			field.setAccessible(true);
			try {
				sb.append("\n" + field.getName() + COLON + field.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	private static <T> void addMethods(T obj, String COLON, StringBuffer sb) {
		List<Method> methods = ReflectionUtils.getAllMethods(obj);

		for (Method method : methods) {
			sb.append("\n" + method.getReturnType() + " " + method.getName() + "()");
		}
	}

	


}
