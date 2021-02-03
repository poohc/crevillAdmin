package kr.co.crevill.member;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("member")
public class MemberController {

	@RequestMapping("list.view")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/list");
		return mav;
	}
	
	@RequestMapping("join.view")
	public ModelAndView join(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/join");
		return mav;
	}
	
	@RequestMapping("history.view")
	public ModelAndView history(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/history");
		return mav;
	}
	
}