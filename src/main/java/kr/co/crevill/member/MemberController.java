package kr.co.crevill.member;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonCodeDto;
import kr.co.crevill.common.CommonService;
import kr.co.crevill.common.MenuDto;

@Controller
@RequestMapping("member")
public class MemberController {

	@Autowired
	private MenuDto menuDto;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping("list.view")
	public ModelAndView list(HttpServletRequest request, MemberDto memberDto) {
		ModelAndView mav = new ModelAndView("member/list");
		menuDto.setMenuName("고객리스트");
		menuDto.setUpperMenu("고객관리");
		menuDto.setCurrentMenu("고객리스트");
		mav.addObject("menu", menuDto);
		mav.addObject("memberList", memberService.selectMemberList(memberDto));
		return mav;
	}
	
	@RequestMapping("join.view")
	public ModelAndView join(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/join");
		menuDto.setMenuName("신규고객등록");
		menuDto.setUpperMenu("고객관리");
		menuDto.setCurrentMenu("신규고객등록");
		mav.addObject("menu", menuDto);
		CommonCodeDto commonCodeDto = new CommonCodeDto();
		commonCodeDto.setCodeType("LEARNING_GRADE");
		mav.addObject("learningGradeList", commonService.selectCommonCode(commonCodeDto));
		return mav;
	}
	
	@RequestMapping("checkMemberCellPhone.proc")
	@ResponseBody
	public JSONObject checkMemberCellPhone(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.checkMemberCellPhone(memberDto);
		return result;
	}
	
	@RequestMapping("join.proc")
	@ResponseBody
	public JSONObject joinProc(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.insertMemberInfo(memberDto);
		return result;
	}
	
	@RequestMapping("history.view")
	public ModelAndView history(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("member/history");
		return mav;
	}
	
}