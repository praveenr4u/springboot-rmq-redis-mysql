package com.spring.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.Constants;
import com.spring.entity.Property;

@Service
public class QueueService {
 
	@Autowired
	private RabbitTemplate rabbitTemplate;
	 
		
		public String sendQueue(Property property) {
			
			rabbitTemplate.convertAndSend(Constants.EXCHANGE,Constants.ROUTING_KEY, property);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>> Queue values<<<<<<<<<<<<"+property);
			return "success!!"; 
		}
}
