package com.gsoft.framework.pubsub.redis.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.gsoft.framework.pubsub.redis.AbstractSubMsgReceiver;

/**
 * redis 消息订阅配置
 * 
 * @author liupantao
 * @date 2018年7月13日
 * 
 */
@Configuration
public class PubsubConfig implements ApplicationContextAware {

	@Autowired
	private PubsubMsgReceiver pubsubMsgReceiver;

	private ApplicationContext applicationContext;

	// 初始化监听器
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		List<ChannelTopic> topics = new ArrayList<>();
		List<AbstractSubMsgReceiver<?>> subMsgReceivers = getSubMsgReceivers();
		for (AbstractSubMsgReceiver<?> subMsgReceiver : subMsgReceivers) {
			topics.add(new ChannelTopic(subMsgReceiver.getChannel()));
		}
		if (topics.size() > 0) {
			container.addMessageListener(listenerAdapter(), topics);
		}
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter() {
		return new MessageListenerAdapter(pubsubMsgReceiver, "pubsubMsgReceiver");
	}

	/**
	 * 获取 JobTask集合
	 * 
	 * @param context
	 */
	@SuppressWarnings("rawtypes")
	private List<AbstractSubMsgReceiver<?>> getSubMsgReceivers() {
		List<AbstractSubMsgReceiver<?>> subMsgReceivers = new ArrayList<>();
		Map<String, AbstractSubMsgReceiver> jobTaskMap = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext, AbstractSubMsgReceiver.class, true, false);
		for (Map.Entry<String, AbstractSubMsgReceiver> entry : jobTaskMap.entrySet()) {
			subMsgReceivers.add(entry.getValue());
		}
		return subMsgReceivers;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
