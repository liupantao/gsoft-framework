package com.gsoft.framework.util;

/**
 * 参数验证
 * 
 * @author liupantao
 * 
 */
public class ValidataFactory {

	private StringBuffer paramValidateResult;

	public static String VALIDATE_SIZE_TYPE_EQ = "1";
	public static String VALIDATE_SIZE_TYPE_LESS = "2";
	public static String VALIDATE_SIZE_TYPE_GREATER = "3";

	private ValidataFactory() {
		paramValidateResult = new StringBuffer();
	}

	public static ValidataFactory create() {
		return new ValidataFactory();
	}
	

	public ValidataFactory notEmptyAndLength(String name, String value, Boolean notNull, String validateSizeType, int size) {
		if (notNull) {
			// 参数不能为空
			if (isEmpty(value)) {
				paramValidateResult.append(buildMessage(name, value, "不能为空"));
			} else {
				// 参数长度判断
				if (VALIDATE_SIZE_TYPE_EQ.equals(validateSizeType)) {
					paramValidateResult.append(value.length() == size ? "" : buildMessage(name, value, "长度必须为 " + size));
				} else if (VALIDATE_SIZE_TYPE_LESS.equals(validateSizeType)) {
					paramValidateResult.append(value.length() < size ? "" : buildMessage(name, value, "长度必须小于 " + size));
				} else if (VALIDATE_SIZE_TYPE_GREATER.equals(validateSizeType)) {
					paramValidateResult.append(value.length() > size ? "" : buildMessage(name, value, "长度必须大于 " + size));
				}
			}
		}
		return this;
	}

	public ValidataFactory notNull(String name, Object value){
		if(value==null){
			paramValidateResult.append(buildMessage(name, value, "不能为null"));
		}
		return this;
	}
	/**
	 * 非空
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public ValidataFactory notEmpty(String name, String value) {
		return notEmptyAndLength(name, value, true, null, 0);
	}

	/**
	 * 非空且长度等于 @param size
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public ValidataFactory lengthEq(String name, String value, int size) {
		return notEmptyAndLength(name, value, true, VALIDATE_SIZE_TYPE_EQ, size);
	}

	/**
	 * 非空且长度大于 @param size
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public ValidataFactory lengthGreater(String name, String value, int size) {
		return notEmptyAndLength(name, value, true, VALIDATE_SIZE_TYPE_GREATER, size);
	}

	/**
	 * 非空且长度小于 @param size
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	public ValidataFactory lengthLess(String name, String value, int size) {
		return notEmptyAndLength(name, value, true, VALIDATE_SIZE_TYPE_LESS, size);
	}

	public static boolean isEmpty(String value) {
		return value == null || value.equals("");
	}

	public boolean validateSuccess() {
		return paramValidateResult.length() == 0;
	}

	public String getMessage() {
		if (!validateSuccess()) {
			return paramValidateResult.toString();
		}
		return "";
	}

	private String buildMessage(String name, Object value, String message) {
		return "【 " + name + "】" + message + "\n";
	}

	public static void main(String[] args) {
		ValidataFactory factory = ValidataFactory.create().notEmpty("reqNo", "111").lengthEq("accDate", "20170720", 9).lengthGreater("test", "", 8)
				.notEmpty("a", "").notEmpty("v", "");

		if (!factory.validateSuccess()) {
			System.out.println(factory.getMessage());
		}
	}

}
