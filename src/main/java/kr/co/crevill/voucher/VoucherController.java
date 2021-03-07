package kr.co.crevill.voucher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("voucher")
public class VoucherController {
	
	@Autowired
	private VoucherService voucherService;
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("voucher/list");
		mav.addObject("list", voucherService.selectVoucherList(voucherDto));
		mav.addObject("attributeList", voucherService.selectVoucherAttributeList(voucherDto));
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
	
	@PostMapping("create.proc")
	@ResponseBody
	public JSONObject createProc(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.insertVoucher(voucherDto, request);
		return result;
	}
	
	@PostMapping("sale.proc")
	@ResponseBody
	public JSONObject sale(HttpServletRequest request, @ModelAttribute VoucherSaleDto voucherSaleDto) {
		JSONObject result = new JSONObject();
		result = voucherService.voucherSaleProc(voucherSaleDto, request);
		return result;
	}
	
	@PostMapping("getVoucherList.proc")
	@ResponseBody
	public List<VoucherVo> getVoucherList(VoucherDto voucherDto){
		return voucherService.getVoucherList(voucherDto);
	}
	
}