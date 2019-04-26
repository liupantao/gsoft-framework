package com.gsoft.framework.pubsub.redis.conf;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import com.gsoft.framework.pubsub.redis.AbstractSubMsgReceiver;
import com.gsoft.framework.pubsub.redis.log.PubsubLog;

/**
 * 发布订阅消息监听
 * 
 * @author liupantao
 * @date 2018年7月13日
 * 
 */
@Component
public class PubsubMsgReceiver implements MessageListener, ApplicationContextAware {

	private Log logger = LogFactory.getLog(getClass());

	private ApplicationContext applicationContext;

	private Map<String, AbstractSubMsgReceiver<?>> subMsgReceiverMap;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String channelName = new String(pattern);
		AbstractSubMsgReceiver<?> subMsgReceiver = subMsgReceiverMap.get(channelName);
		if (subMsgReceiver != null) {
			subMsgReceiver.onMessage(message);
		} else {
			PubsubLog.info("未知广播类型:" + channelName + "-" + message.toString());
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		initSubMsgReceivers();
	}

	/**
	 * 获取 JobTask集合
	 * 
	 * @param context
	 */
	@SuppressWarnings("rawtypes")
	private void initSubMsgReceivers() {
		if (subMsgReceiverMap == null) {
			subMsgReceiverMap = new HashMap<>();
			Map<String, AbstractSubMsgReceiver> jobTaskMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext,
					AbstractSubMsgReceiver.class, true, false);
			for (Map.Entry<String, AbstractSubMsgReceiver> entry : jobTaskMap.entrySet()) {
				subMsgReceiverMap.put(entry.getValue().getChannel(), entry.getValue());
			}
			logger.info("初始化消息订阅服务:" + subMsgReceiverMap.keySet());
		}

	}
}
