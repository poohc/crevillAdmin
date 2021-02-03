package kr.co.crevill.staff;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("staff")
public class StaffController {
	
	@RequestMapping("list.view")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("staff/list");
		return mav;
	}
	
	@RequestMapping("register.view")
	public ModelAndView register(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("staff/register");
		return mav;
	}
	
}