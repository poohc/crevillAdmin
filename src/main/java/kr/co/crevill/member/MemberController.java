package kr.co.crevill.member;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonCodeDto;
import kr.co.crevill.common.CommonService;

/**
 * 
 * @packageName : kr.co.crevill.member
 * @fileName : MemberController.java
 * @author : Juyoung Park
 * @date : 2021.02.10
 * @description :
 * ===========================================================
 * DATE AUTHOR NOTE * -----------------------------------------------------------
 * 2021.02.10 Juyoung Park 최초 생성
 */
@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CommonService commonService;

	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, MemberDto memberDto) {
		ModelAndView mav = new ModelAndView("member/list");
		mav.addObject("memberList", memberService.selectMemberList(memberDto));
		return mav;
	}
	
	@GetMapping("join.view")
	public ModelAndView join(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/join");
		CommonCodeDto commonCodeDto = new CommonCodeDto();
		commonCodeDto.setCodeType("LEARNING_GRADE");
		mav.addObject("learningGradeList", commonService.selectCommonCode(commonCodeDto));
		return mav;
	}
	
	@PostMapping("checkMemberCellPhone.proc")
	@ResponseBody
	public JSONObject checkMemberCellPhone(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.checkMemberCellPhone(memberDto);
		return result;
	}
	
	@PostMapping("join.proc")
	@ResponseBody
	public JSONObject joinProc(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.insertMemberInfo(memberDto);
		return result;
	}
	
	@GetMapping("history.view")
	public ModelAndView history(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/history");
		return mav;
	}
	
	@PostMapping("getMemberInfo.proc")
	@ResponseBody
	public List<MemberVo> getMemberInfo(HttpServletRequest request, MemberDto memberDto) {
		return memberService.getMemberInfo(memberDto);
	}
	
}