package com.api.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.api.sample.vo.UserVO;

@Controller
public class ViewsController {
	@GetMapping("/")
	public String goHome() {
		return "/index";
	}
	@GetMapping("/views/test")
	@ResponseBody // RestController여도 상관없다 리턴값 자체가 다르다
	public ModelAndView test() {
	    ModelAndView modelAndView = new ModelAndView(); 
	    modelAndView.setViewName("/views/test");
	    UserVO user = new UserVO();
	    user.setName("demd7362");
	    user.setPassword("r1r2");
	    user.setAge(10);
	    
	    modelAndView.addObject("test", user);
	    return modelAndView;
	}
	
	@GetMapping("/views/**")
	public void goPage() {
		
	}
}
