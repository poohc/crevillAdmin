package kr.co.crevill.entrance;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.voucher.VoucherDto;

@Controller
@RequestMapping("entrance")
public class EntranceController {
	
	@GetMapping("member.view")
	public ModelAndView member(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("entrance/member");
		return mav;
	}
	
	@GetMapping("nonMember.view")
	public ModelAndView nonMember(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("entrance/nonMember");
		return mav;
	}
}