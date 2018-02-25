package poorman.utils.sql;
import java.lang.reflect.Field;
import java.util.Stack;

public interface FieldModification {
	static <T> void updateField(Field beanField, Stack<Field> existingFields, T existingObject, Object newBean,
			int i) throws IllegalArgumentException, IllegalAccessException {

		
	}
}
