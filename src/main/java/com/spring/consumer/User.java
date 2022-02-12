package com.spring.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.spring.Constants;
import com.spring.entity.Property;

@Component
public class User {

	@RabbitListener(queues = Constants.QUEUE )
	public void consumeMessageFromQueue(Property orderStatus) {
		System.out.println(">>>>>>>>>>>>>>>>Message Received from queue: " +orderStatus );
	}
}
