package kr.co.crevill;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class CrevillController {

	@RequestMapping("memberJoin.view")
	public ModelAndView memberJoin(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("memberJoin");
		return mav;
	}
	
	@RequestMapping("voucherSearch.view")
	public ModelAndView voucherSearch(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucherSearch");
		return mav;
	}
	
	@RequestMapping("voucherSale.view")
	public ModelAndView voucherSale(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucherSale");
		return mav;
	}
	
	@RequestMapping("reservationSearch.view")
	public ModelAndView reservationSearch(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reservationSearch");
		return mav;
	}
	
	@RequestMapping("staffRegister.view")
	public ModelAndView staffRegister(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("staffRegister");
		return mav;
	}
}
