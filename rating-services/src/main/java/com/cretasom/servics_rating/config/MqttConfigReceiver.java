package com.cretasom.servics_rating.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.cretasom.servics_rating.service.RatingServiceImpl;

@Configuration
public class MqttConfigReceiver {

	@Autowired
	private RatingServiceImpl impl;

	@Value("${mqtt.uri}")
	private String mqttUri;
	@Value("${mqtt.username}")
	private String mqttUserName;
	@Value("${mqtt.password}")
	private String mqttPassword;
	Logger logger = LoggerFactory.getLogger(getClass());

	@Bean
	MqttPahoClientFactory mqttClientFactory() {
		DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
		MqttConnectOptions options = new MqttConnectOptions();

		options.setServerURIs(new String[] { mqttUri });
		options.setUserName(mqttUserName);
		options.setPassword(mqttPassword.toCharArray());
		options.setCleanSession(true);

		factory.setConnectionOptions(options);

		return factory;
	}

	@Bean
	MessageChannel mqttInputChannel() {
		return new DirectChannel();
	}

	@Bean
	MessageProducer inbound() {
		MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
				"ratingMqttClientReceiver", mqttClientFactory(), "userIdTopic", "hotelIdTopic");

		adapter.setCompletionTimeout(5000);
		adapter.setConverter(new DefaultPahoMessageConverter());
		adapter.setQos(2);
		adapter.setOutputChannel(mqttInputChannel());
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = "mqttInputChannel")
	MessageHandler handler() {
		return new MessageHandler() {

			@Override
			public void handleMessage(Message<?> message) {
				try {
					String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
					String payload = message.getPayload().toString();
					if ("userIdTopic".equalsIgnoreCase(topic)) {
						logger.info("adding [{}] to user id list", payload);
						impl.addUserId(payload);
					}
					if ("hotelIdTopic".equalsIgnoreCase(topic)) {
						logger.info("adding [{}] to hotel id list", payload);
						impl.addHotelId(payload);
					}
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
				}

			}

		};
	}
}
