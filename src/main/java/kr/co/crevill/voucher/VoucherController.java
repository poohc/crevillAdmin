package kr.co.crevill.voucher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("voucher")
public class VoucherController {
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucher/list");
		return mav;
	}
	
	@GetMapping("sale.view")
	public ModelAndView sale(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucher/sale");
		return mav;
	}
	
	@GetMapping("create.view")
	public ModelAndView create(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucher/create");
		return mav;
	}
}