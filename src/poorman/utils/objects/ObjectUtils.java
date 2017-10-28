package poorman.utils.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import poorman.reflection.utils.ReflectionUtils;

public class ObjectUtils {

	public ObjectUtils() {

	}

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
	public <T> Object mergeClasses(T object, Class<T> classToCreate) {
		return ClassesUtils.merge(object, classToCreate);
	}

	/**
	 * Returns all of the fields both in that specific class and all inherited
	 * classes
	 *
	 * @param object
	 *            an already instantiated object
	 * @param classType
	 *            the object type
	 * @return A List of Fields pertaining to that class
	 * 
	 * @author Ryan Poorman
	 */
	public <T> List<Field> getFields(Class<T> classType) {
		return ReflectionUtils.getAllFields(classType);
	}

	/**
	 * Returns all of the fields both in that specific class and all inherited
	 * classes
	 *
	 * @param object
	 *            an already instantiated object
	 * @return A List of Fields pertaining to that class
	 * 
	 * @author Ryan Poorman
	 */
	public <T> List<Field> getFields(T object) {
		return ReflectionUtils.getAllFields(object);

	}

	/**
	 * Returns all of the fields that may be used for basic setting
	 * functionality both in that specific class and all inherited classes
	 *
	 * @param classType
	 *            the object type
	 * @return A List of Methods pertaining to that class with setting
	 *         functionality
	 * 
	 * @author Ryan Poorman
	 */
	public <T> List<Method> getSetters(Class<T> classType) {
		return ReflectionUtils.getSetMethods(classType);
	}

}
