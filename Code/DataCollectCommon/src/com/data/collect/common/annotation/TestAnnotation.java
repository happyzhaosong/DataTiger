package com.data.collect.common.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.data.collect.common.dto.UserDTO;

public class TestAnnotation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Annotation anno = UserDTO.class.getAnnotation(DBTable.class);
		DBTable tableAnno = (DBTable)anno;
		if(tableAnno!=null)
		{
			String tableName = tableAnno.name();
		}
		
		Annotation annoArr[] = UserDTO.class.getAnnotations();
		int size = annoArr.length;
		for(int i=0;i<size;i++)
		{
			anno = annoArr[i];
		}
		
		Field fieldArr[] = UserDTO.class.getDeclaredFields();
		size = fieldArr.length;
		for(int i=0;i<size;i++)
		{
			Field field = fieldArr[i];
			anno = field.getAnnotation(DBColumn.class);
			DBColumn columnAnno = (DBColumn)anno;
			if(columnAnno!=null)
			{
				String columnName = columnAnno.name();
			}
		}		
	}

}
