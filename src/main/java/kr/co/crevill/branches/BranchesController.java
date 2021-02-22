package kr.co.crevill.branches;

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
@RequestMapping("branches")
public class BranchesController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private BranchesService branchesService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("noticeList.view")
	public ModelAndView noticeList(HttpServletRequest request, NoticeDto noticeDto) {
		ModelAndView mav = new ModelAndView("branches/noticeList");
		mav.addObject("list", branchesService.selectNoticeList(noticeDto));
		return mav;
	}
	
	@GetMapping("noticeWrite.view")
	public ModelAndView noticeWrite(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("branches/noticeWrite");
		return mav;
	}
	
	@PostMapping("noticeWrite.proc")
	@ResponseBody
	public JSONObject noticeWriteProcess(HttpServletRequest request, @ModelAttribute NoticeDto noticeDto) {
		JSONObject result = new JSONObject();
		result = branchesService.insertNotice(noticeDto);
		return result;
	}
	
}