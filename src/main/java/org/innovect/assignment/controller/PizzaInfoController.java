package org.innovect.assignment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/")
@Api(value="This controller provides all pizza ordering information.")
public class PizzaInfoController {

	@Autowired
    Environment environment;
	
	@GetMapping(value="/")
	public Map<String,Object> getWelcomeMsg(){
	    Map<String,Object> map = new HashMap<>();
	    map.put("welcomeMsg","WelcomeController executed");
	    map.put("Environment selected",environment.getActiveProfiles());
	    return map;
	}
	
}
