package org.tat.gginl.api.common;

import java.lang.reflect.Field;
import java.util.Date;

import org.eclipse.persistence.descriptors.DescriptorEvent;
import org.eclipse.persistence.descriptors.DescriptorEventAdapter;
import org.springframework.stereotype.Component;

@Component
public class IDInterceptor extends DescriptorEventAdapter {
	private static ICustomIDGenerator customIDGenerator;
	private static IUserProcessService userProcessService;

//	@Autowired(required = true)
//	@Qualifier("CustomIDGenerator")
	public void setcustomIDGenerator(ICustomIDGenerator generator) {
		customIDGenerator = generator;
	}

	private String getPrefix(Class cla) {
		return customIDGenerator.getPrefix(cla, true);
	}

//	@Autowired(required = true)
//	@Qualifier("UserProcessService")
	public void setUserProcessService(IUserProcessService userProcessService) {
		IDInterceptor.userProcessService = userProcessService;
	}

	@Override
	public void preInsert(DescriptorEvent event) {
		try {
			Object obj = event.getObject();
			Field field = getClassFiled(obj.getClass(), "id");
			field.setAccessible(true);
			String id = (String) field.get(obj);
			id = FormatID.formatId(id, getPrefix(obj.getClass()), 10);
			field.set(obj, id);
			Field coCreateAndUpateField = getClassFiled(obj.getClass(), "commonCreateAndUpateMarks");
			coCreateAndUpateField.setAccessible(true);
			CommonCreateAndUpateMarks coCreateAndUpateMarks = new CommonCreateAndUpateMarks();
			coCreateAndUpateMarks.setCreatedUserId(userProcessService.getLoginUser().getId());
			coCreateAndUpateMarks.setCreatedDate(new Date());
			coCreateAndUpateField.set(obj, coCreateAndUpateMarks);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public Field getClassFiled(Class<?> c, String fieldName) {
		Field field = null;
		try {
			field = c.getDeclaredField(fieldName);

		} catch (NoSuchFieldException e) {
			Class<?> sc = c.getSuperclass();
			field = getClassFiled(sc, fieldName);
		}
		return field;
	}

	@Override
	public void preUpdateWithChanges(DescriptorEvent event) {
		try {
			Object obj = event.getObject();
			Field coCreateAndUpateField = obj.getClass().getDeclaredField("commonCreateAndUpateMarks");
			coCreateAndUpateField.setAccessible(true);
			CommonCreateAndUpateMarks coCreateAndUpateMarks = (CommonCreateAndUpateMarks) coCreateAndUpateField.get(obj);
			if (coCreateAndUpateMarks != null) {
				coCreateAndUpateMarks.setUpdatedUserId(userProcessService.getLoginUser().getId());
				coCreateAndUpateMarks.setUpdatedDate(new Date());
				coCreateAndUpateField.set(obj, coCreateAndUpateMarks);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
