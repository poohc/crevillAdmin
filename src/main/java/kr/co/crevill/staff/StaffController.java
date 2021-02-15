package kr.co.crevill.staff;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonCodeDto;
import kr.co.crevill.common.CommonService;

@Controller
@RequestMapping("staff")
public class StaffController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private StaffService staffService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("list.view")
	public ModelAndView list(HttpServletRequest request, StaffDto staffDto) {
		ModelAndView mav = new ModelAndView("staff/list");
		mav.addObject("staffCount", staffService.selectStaffCount(staffDto));
		mav.addObject("staffList", staffService.selectStaffList(staffDto));
		return mav;
	}
	
	@RequestMapping("join.view")
	public ModelAndView join(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("staff/join");
		CommonCodeDto commonCodeDto = new CommonCodeDto();
		commonCodeDto.setCodeType("WORKER_TYPE");
		mav.addObject("workerType", commonService.selectCommonCode(commonCodeDto));
		commonCodeDto.setCodeType("STAFF_GRADE");
		mav.addObject("staffGrade", commonService.selectCommonCode(commonCodeDto));
		mav.addObject("officeList", commonService.selectOfficeList());
		return mav;
	}
	
	@RequestMapping("join.proc")
	@ResponseBody
	public JSONObject joinProc(HttpServletRequest request, @ModelAttribute StaffDto staffDto) {
		JSONObject result = new JSONObject();
		result = staffService.insertStaffInfo(staffDto);
		return result;
	}
	
}