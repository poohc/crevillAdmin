package kr.co.crevill.schedule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonService;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ScheduleService scheduleService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("schedule/list");
		return mav;
	}
	
	@GetMapping("regist.view")
	public ModelAndView regist(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("schedule/regist");
		List<String> timeList = new ArrayList<String>();
		for(int i=0; i<24; i++) {
			if(i >= 10) {
				timeList.add(i + "");
			} else {
				timeList.add("0" + i);
			}
		}
		mav.addObject("timeList", timeList);
		return mav;
	}
	
	@PostMapping("regist.proc")
	@ResponseBody
	public JSONObject registProc(HttpServletRequest request, @ModelAttribute ScheduleDto scheduleDto) {
		JSONObject result = new JSONObject();
		result = scheduleService.insertSchedule(scheduleDto);
		return result;
	}
}