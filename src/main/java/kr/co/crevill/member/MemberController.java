package kr.co.crevill.member;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonCodeDto;
import kr.co.crevill.common.CommonService;
import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.voucher.VoucherDto;
import kr.co.crevill.voucher.VoucherService;
import kr.co.crevill.voucher.VoucherVo;

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

	@Autowired
	private VoucherService voucherService; 
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, MemberDto memberDto) {
		ModelAndView mav = new ModelAndView("member/list");
		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
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
	
	@GetMapping("update.view")
	public ModelAndView update(HttpServletRequest request, MemberDto memberDto) {
		ModelAndView mav = new ModelAndView("member/update");
		CommonCodeDto commonCodeDto = new CommonCodeDto();
		commonCodeDto.setCodeType("LEARNING_GRADE");
		mav.addObject("learningGradeList", commonService.selectCommonCode(commonCodeDto));
		List<MemberVo> infoList = memberService.getMemberInfo(memberDto);
		mav.addObject("info", infoList);
		List<String> learningGradeList = new ArrayList<String>();
		for(String learningGrade : infoList.get(0).getLearningGrade().split(",")) {
			learningGradeList.add(learningGrade);
		}
		mav.addObject("checkedlearningGradeList", learningGradeList);
		return mav;
	}
	
	@PostMapping("checkMemberCellPhone.proc")
	@ResponseBody
	public JSONObject checkMemberCellPhone(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		result = memberService.checkMemberCellPhone(memberDto);
		return result;
	}
	
	@PostMapping("join.proc")
	@ResponseBody
	public JSONObject joinProc(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.insertMemberInfo(memberDto, request);
		return result;
	}
	
	@PostMapping("update.proc")
	@ResponseBody
	public JSONObject updateProc(HttpServletRequest request, @RequestBody MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.updateMemberInfo(memberDto, request);
		return result;
	}
	
	@PostMapping("delete.proc")
	@ResponseBody
	public JSONObject deleteProc(HttpServletRequest request, @ModelAttribute MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result = memberService.deleteMemberInfo(memberDto);
		return result;
	}
	
	@GetMapping("voucherUseList.view")
	public ModelAndView history(HttpServletRequest request, VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("member/voucherUseList");
		List<VoucherVo> voucherList = voucherService.getMemberVoucherAllList(voucherDto);
		mav.addObject("voucherList", voucherList);
		
		if(voucherList != null && voucherList.size() > 0) {
			if(voucherDto.getVoucherNo() == null) {
				voucherDto.setVoucherNo(voucherList.get(0).getVoucherNo());
			}	
			mav.addObject("memberName", voucherList.get(0).getName());
			mav.addObject("cellPhone", voucherDto.getCellPhone());
			mav.addObject("voucherNo", voucherDto.getVoucherNo());
			mav.addObject("list", voucherService.getMemberVoucherUseList(voucherDto));
		}
		return mav;
	}
		
	@PostMapping("getMemberInfo.proc")
	@ResponseBody
	public List<MemberVo> getMemberInfo(HttpServletRequest request, MemberDto memberDto) {
		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		return memberService.getMemberInfo(memberDto);
	}
	
	
	@PostMapping("getChildList.proc")
	@ResponseBody
	public JSONObject getChildList(HttpServletRequest request, MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		List<MemberVo> childList = memberService.selectChildList(memberDto);
		if(childList != null && childList.size() > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
			result.put("list", childList);
		}
		return result;
	}
}