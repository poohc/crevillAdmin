package kr.co.crevill.reservation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("reservation")
public class ReservationController {

	@RequestMapping("list.view")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reservation/list");
		return mav;
	}
	
	@RequestMapping("todayList.view")
	public ModelAndView todayList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reservation/todayList");
		return mav;
	}
	
}