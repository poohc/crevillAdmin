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
import kr.co.crevill.common.CrevillConstants;

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
		noticeDto.setNoticeType(CrevillConstants.NOTICE_BRANCHES);
		mav.addObject("list", branchesService.selectNoticeList(noticeDto));
		return mav;
	}
	
	@GetMapping("noticeWrite.view")
	public ModelAndView noticeWrite(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("branches/noticeWrite");
		return mav;
	}
	
	@GetMapping("headquarterList.view")
	public ModelAndView headquarterList(HttpServletRequest request, NoticeDto noticeDto) {
		ModelAndView mav = new ModelAndView("branches/headquarterList");
		noticeDto.setNoticeType(CrevillConstants.NOTICE_HEADQUARTER);
		mav.addObject("list", branchesService.selectHeadquarterNoticeList(noticeDto));
		return mav;
	}
	
	@GetMapping("headquarterWrite.view")
	public ModelAndView headquarterWrite(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("branches/headquarterWrite");
		return mav;
	}
	
	@GetMapping("headquarterUpdate.view")
	public ModelAndView headquarterUpdate(HttpServletRequest request, NoticeDto noticeDto) {
		ModelAndView mav = new ModelAndView("branches/headquarterUpdate");
		mav.addObject("info", branchesService.selectNoticeInfo(noticeDto));
		return mav;
	}
	
	@PostMapping("noticeWrite.proc")
	@ResponseBody
	public JSONObject noticeWriteProcess(HttpServletRequest request, @ModelAttribute NoticeDto noticeDto) {
		JSONObject result = new JSONObject();
		result = branchesService.insertNotice(noticeDto, request);
		return result;
	}
	
	@PostMapping("noticeDelete.proc")
	@ResponseBody
	public JSONObject noticeDeleteProcess(HttpServletRequest request, @ModelAttribute NoticeDto noticeDto) {
		JSONObject result = new JSONObject();
		result = branchesService.deleteNotice(noticeDto, request);
		return result;
	}
	
	@PostMapping("noticeUpdate.proc")
	@ResponseBody
	public JSONObject noticeUpdateProcess(HttpServletRequest request, @ModelAttribute NoticeDto noticeDto) {
		JSONObject result = new JSONObject();
		result = branchesService.updateNotice(noticeDto, request);
		return result;
	}
}