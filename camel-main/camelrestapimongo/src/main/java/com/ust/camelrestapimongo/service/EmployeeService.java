package com.ust.camelrestapimongo.service;

import org.apache.camel.Exchange;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;



@Component
public class EmployeeService {
	
	public void findById(Exchange exchange) {
		
		String id = exchange.getIn().getHeader("id",String.class);
		ObjectId object = new ObjectId(id);
		exchange.getIn().setBody(object);
		
	}
	

}
