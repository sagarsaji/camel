package com.ust.camelrestapimongo.service;

import org.apache.camel.Exchange;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;

@Component
public class EmployeeService {
	
	public void findById(Exchange exchange) {
		
		String id = exchange.getIn().getHeader("id",String.class);
		ObjectId object = new ObjectId(id);
		exchange.getIn().setBody(object);
		System.out.println(object);
	}
	
	public void findByName(Exchange exchange) {
		
		String name = exchange.getIn().getHeader("name", String.class);
        BasicDBObject query = new BasicDBObject("name", name);
        exchange.getIn().setBody(query);
        System.out.println(query);
		
	}

}
