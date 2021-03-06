package kr.co.crevill;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CrevillController {

	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("dashboard");
		return mav;
	}	
}