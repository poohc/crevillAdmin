package kr.co.crevill;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("main")
public class CrevillController {

	@GetMapping("main.view")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	
}