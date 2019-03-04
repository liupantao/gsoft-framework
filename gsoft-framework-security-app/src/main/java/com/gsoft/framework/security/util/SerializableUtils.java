package com.gsoft.framework.security.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.session.Session;

/**
 * 序列和反序列Session对象，只有将session对象序列化成字符串，才可以存储到Mysql上，不能直接存
 * @author liupantao
 * @date 2018年4月18日
 *  
 */
public class SerializableUtils {

	/** 
	 * 将Session序列化成String类型
	 * @param session
	 * @return 
	 */
	public static String serializ(Session session) {
		try {
			// ByteArrayOutputStream 用于存储序列化的Session对象
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			// 将Object对象输出成byte数据
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(session);

			Base64 base64 = new Base64();

			// 将字节码，编码成String类型数据
			return base64.encodeToString(bos.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException("序列化失败");
		}
	}

	/** 
	 * 将一个Session的字符串序列化成字符串,反序列化
	 * @param sessionStr
	 * @return 
	 */
	public static Session deserializ(String sessionStr) {
		try {
			Base64 base64 = new Base64();
			// 读取字节码表
			ByteArrayInputStream bis = new ByteArrayInputStream(base64.decode(sessionStr));
			// 将字节码反序列化成 对象
			ObjectInputStream in = new ObjectInputStream(bis);
			Session session = (Session) in.readObject();
			return session;
		} catch (Exception e) {
			throw new RuntimeException("反序列化失败");
		}
	}
}