package com.lenovo.itac.http.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * SpringMVC在将Bean转换为JSON时，如果Bean包含有对象类型的变量，那么默认情况下该对象必须经过赋值，否则会报错。
 * 解决方法：自定义一个ObjectMapper，将FAIL_ON_EMPTY_BEANS设置为false
 * @author lizh18
 *
 */
public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -807951932130346328L;

	public CustomObjectMapper() {
		this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}
}
