package kr.co.crevill.schedule;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonService;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
	
	@Autowired
	private CommonService commonService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("schedule/list");
		return mav;
	}
	
	@GetMapping("regist.view")
	public ModelAndView regist(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("schedule/regist");
		return mav;
	}
	
}