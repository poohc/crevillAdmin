package kr.co.crevill.manage;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("manage")
public class ManageController {

	@RequestMapping("entrance.view")
	public ModelAndView entrance(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("manage/entrance");
		return mav;
	}
	
}