package com.ust.camelrestapimongo.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.ust.camelrestapimongo.entity.Employee;
import com.ust.camelrestapimongo.service.EmployeeService;

@Component
public class CamelRoute extends RouteBuilder {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.session.mongodb.collection-name}")
    private String collection;

	@Override
	public void configure() throws Exception {

		rest()
			.post("/add").type(Employee.class).to("direct:addemployees")
			.get("/get").to("direct:getemployees")
			.get("/get/{id}").to("direct:getemployeebyid")
			.get("/get/details/{name}").to("direct:getbyname")
			.get("/get/details/name/{name}").to("direct:getname");
		
		

		from("direct:addemployees")
        .unmarshal().json(JsonLibrary.Jackson, Employee.class)
        .to("mongodb:employeedb?database=" + database + "&collection=" + collection + "&operation=insert")
        .marshal().json();
		
		
		from("direct:getemployees")
		.to("mongodb:employeedb?database=" + database + "&collection=" + collection + "&operation=findAll")
		.marshal().json();

		
		from("direct:getemployeebyid")
		.bean("employeeService","findById")
	    .to("mongodb:employeedb?database=" + database + "&collection=" + collection + "&operation=findById")
	    .marshal().json();

		
		from("direct:getbyname")
		.setHeader("CamelMongoDbCriteria", simple("{ name: '${header.name}' }"))
	    .to("mongodb:employeedb?database=" + database + "&collection=" + collection + "&operation=findOneByQuery")
	    .marshal().json();

		
		from("direct:getname")
	    .setHeader("CamelMongoDbCriteria", simple("{ name: '${header.name}' }"))
	    .setHeader("CamelMongoDbLimit", constant(2))
	    .to("mongodb:employeedb?database=" + database + "&collection=" + collection + "&operation=findAll")
	    .marshal().json();



	}

}
