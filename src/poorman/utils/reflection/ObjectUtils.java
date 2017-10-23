package poorman.utils.reflection;

public class ObjectUtils {

	public ObjectUtils() {

	}

	public <T> Object mergeClasses(T object, Class<T> classToCreate) {
		return ClassesUtils.merge(object, classToCreate);
	}

	public <T> Object createClass(Class<T> classToCreate) {
		return ClassesUtils.create(classToCreate);
	}

}
