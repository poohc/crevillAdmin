package kr.co.crevill.voucher;

import java.util.ArrayList;
import java.util.HashSet;
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

import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.member.MemberDto;
import kr.co.crevill.member.MemberService;
import kr.co.crevill.promotion.PromotionDto;
import kr.co.crevill.promotion.PromotionService;
import kr.co.crevill.store.StoreDto;
import kr.co.crevill.store.StoreService;
import kr.co.crevill.store.StoreVo;

@Controller
@RequestMapping("voucher")
public class VoucherController {
	
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private PromotionService promotionService;
	
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
		PromotionDto promotionDto = new PromotionDto();
		mav.addObject("promotionList", promotionService.selectPromotionList(promotionDto));
		return mav;
	}
	
	@GetMapping("create.view")
	public ModelAndView create(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucher/create");
		StoreDto storeDto = new StoreDto();
		List<StoreVo> storeList = storeService.selectStoreList(storeDto);
		List<StoreVo> newStoreList = new ArrayList<StoreVo>();
		for(StoreVo storeVo : storeList) {
			if(!"CST00001".equals(storeVo.getStoreId())) {
				newStoreList.add(storeVo);	
			}
		}
		mav.addObject("storeList", newStoreList);
		return mav;
	}
	
	@GetMapping("update.view")
	public ModelAndView update(HttpServletRequest request, VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("voucher/update");
		VoucherVo info = voucherService.selectVoucherInfo(voucherDto);
		List<String> attributeList = new ArrayList<String>();
		if(info.getAttribute() != null && info.getAttribute().length() > 0) {
			for(String attribute : info.getAttribute().split(",")) {
				attributeList.add(attribute);
			}
			mav.addObject("attributeList", attributeList);
		}
		StoreDto storeDto = new StoreDto();
		List<StoreVo> storeList = storeService.selectStoreList(storeDto);
		List<StoreVo> newStoreList = new ArrayList<StoreVo>();
		for(StoreVo storeVo : storeList) {
			if(!"CST00001".equals(storeVo.getStoreId())) {
				newStoreList.add(storeVo);	
			}
		}
		mav.addObject("storeList", newStoreList);
		mav.addObject("info", info);
		return mav;
	}
	
	@PostMapping("create.proc")
	@ResponseBody
	public JSONObject createProc(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.insertVoucher(voucherDto, request);
		return result;
	}
	
	@PostMapping("update.proc")
	@ResponseBody
	public JSONObject updateProc(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.updateVoucher(voucherDto, request);
		return result;
	}
	
	@PostMapping("delete.proc")
	@ResponseBody
	public JSONObject deleteProc(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.deleteVoucher(voucherDto, request);
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
	
	@PostMapping("getMemberVoucherList.proc")
	@ResponseBody
	public JSONObject getMemberVoucherList(HttpServletRequest request, VoucherSaleDto voucherSaleDto){
		JSONObject result = new JSONObject();
		MemberDto memberDto = new MemberDto();
		memberDto.setParentCellPhone(voucherSaleDto.getBuyCellPhone());
		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		result = memberService.checkMemberCellPhone(memberDto);
		//기회원 바우처 리스트 가져오기
		if(CrevillConstants.RESULT_FAIL.equals(result.get("resultCd"))) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);	//기회원 이므로 성공 코드 리턴
			List<VoucherVo> voucherList = voucherService.getMemberVoucherList(voucherSaleDto);
			List<String> tempChildList = new ArrayList<String>();
			for(VoucherVo vo : voucherList) {
				tempChildList.add(vo.getName());
			}
			HashSet<String> distinctChildSet = new HashSet<String>(tempChildList);
			List<String> childList = new ArrayList<String>(distinctChildSet);
			result.put("voucherList", voucherList);
			result.put("childList", childList);
		} else {
			result.put("resultCd", CrevillConstants.RESULT_FAIL);	//기회원 이므로 성공 코드 리턴
		}
		return result;
	}
}