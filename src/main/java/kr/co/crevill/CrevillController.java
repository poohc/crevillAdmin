package kr.co.crevill;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CrevillController {

	@GetMapping("index.view")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("index");
		return mav;
	}
	
}