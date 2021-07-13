package kr.co.crevill.voucher;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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

import kr.co.crevill.common.CommonDto;
import kr.co.crevill.common.CommonService;
import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.ListSortComparator;
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
	
	@Autowired
	private CommonService commonService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("voucher/list");
		mav.addObject("list", voucherService.selectVoucherProductList(voucherDto));
		mav.addObject("attributeList", voucherService.selectVoucherProductAttributeList(voucherDto));
		return mav;
	}
	
	@GetMapping("sale.view")
	public ModelAndView sale(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("voucher/sale");
		PromotionDto promotionDto = new PromotionDto();
		mav.addObject("promotionList", promotionService.selectPromotionList(promotionDto));
		return mav;
	}
	
	@GetMapping("saleList.view")
	public ModelAndView saleList(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("voucher/saleList");
		StoreDto storeDto = new StoreDto();
		CommonDto commonDto = new CommonDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		if(voucherDto.getStoreId() == null) {
			voucherDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
			commonDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		} else {
			commonDto.setStoreId(voucherDto.getStoreId());
		}
		mav.addObject("voucherCancelCount", voucherService.selectVoucherSaleCancelCount(voucherDto));
		mav.addObject("voucherTotalStat", commonService.selectVoucherStatistics(commonDto));
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("list", voucherService.selectVoucherSaleList(voucherDto));
		mav.addObject("storeId", voucherDto.getStoreId());
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
		VoucherVo info = voucherService.selectVoucherProductInfo(voucherDto);
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
	
	@SuppressWarnings("unchecked")
	@GetMapping("stat.view")
	public ModelAndView stat(HttpServletRequest request, VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("voucher/stat");
		
		if(voucherDto.getSearchStartDate() != null) {
			
			List<VoucherVo> statList = voucherService.selectVoucherStat(voucherDto);
			int totalTime = 0;
			int usePeopleCount = 0;
			int refundCancelCount = 0;
			int totalUseTime = 0;
			int totalTimeLeft = 0;
			
			for(VoucherVo voucherVo : statList) {
				totalTime += Integer.parseInt(voucherVo.getTotalTime().replaceAll(",", "")); 
				usePeopleCount += Integer.parseInt(voucherVo.getUsePeopleCount().replaceAll(",", ""));
				refundCancelCount += Integer.parseInt(voucherVo.getRefundCancelCount().replaceAll(",", ""));
				totalUseTime += Integer.parseInt(voucherVo.getTotalUseTime().replaceAll(",", ""));
				totalTimeLeft += Integer.parseInt(voucherVo.getTotalTimeLeft().replaceAll(",", ""));
			}
			
			VoucherVo statVo = new VoucherVo();
			DecimalFormat formatter = new DecimalFormat("###,###");
			statVo.setStoreName("전체");
			statVo.setDiffDate(statList.get(0).getDiffDate());
			statVo.setTotalTime(formatter.format(totalTime) + "");
			statVo.setUsePeopleCount(formatter.format(usePeopleCount) + "");
			statVo.setRefundCancelCount(formatter.format(refundCancelCount) + "");
			statVo.setTotalUseTime(formatter.format(totalUseTime) + "");
			statVo.setTotalTimeLeft(formatter.format(totalTimeLeft) + "");
			statVo.setUsePer(Math.round(totalTime / totalUseTime) + "");
			statVo.setSortOrder(0);
			
			List<VoucherVo> calcStatList = new ArrayList<VoucherVo>(); 
			calcStatList.add(statVo);
			int sortOrder = 1;
			for(VoucherVo voucherVo : statList) {
				voucherVo.setSortOrder(sortOrder);
				calcStatList.add(voucherVo);
				sortOrder++;
			}
			Collections.sort(calcStatList, new ListSortComparator());
			
			List<VoucherVo> statMemberList = voucherService.selectVoucherStatMember(voucherDto);
			int voucherMemberCount = 0;
			int nonMemberCount = 0;
			int shortVoucherMemberCount = 0;
			int totalMemberCount = 0;
			
			for(VoucherVo voucherVo : statMemberList) {
				voucherMemberCount += Integer.parseInt(voucherVo.getVoucherMemberCount().replaceAll(",", "")); 
				nonMemberCount += Integer.parseInt(voucherVo.getNonMemberCount().replaceAll(",", ""));
				shortVoucherMemberCount += Integer.parseInt(voucherVo.getShortVoucherMemberCount().replaceAll(",", ""));
				totalMemberCount += Integer.parseInt(voucherVo.getTotalMemberCount().replaceAll(",", ""));
			}
			
			VoucherVo statMember = new VoucherVo();
			statMember.setStoreName("전체");
			statMember.setDiffDate(statMemberList.get(0).getDiffDate());
			statMember.setVoucherMemberCount(formatter.format(voucherMemberCount) + "");
			statMember.setNonMemberCount(formatter.format(nonMemberCount) + "");
			statMember.setShortVoucherMemberCount(formatter.format(shortVoucherMemberCount) + "");
			statMember.setTotalMemberCount(formatter.format(totalMemberCount) + "");
			statMember.setSortOrder(0);
			
			List<VoucherVo> calcStatMemberList = new ArrayList<VoucherVo>(); 
			calcStatMemberList.add(statMember);
			sortOrder = 1;
			for(VoucherVo voucherVo : statMemberList) {
				voucherVo.setSortOrder(sortOrder);
				calcStatMemberList.add(voucherVo);
				sortOrder++;
			}
			Collections.sort(calcStatMemberList, new ListSortComparator());
			
			List<VoucherVo> statResMemberList = voucherService.selectVoucherStatResMember(voucherDto);
			
			int reservationAvaiableCount = 0;
			int reservationCount = 0;
			int noshowCount = 0;
			int entranceCount = 0;
			
			for(VoucherVo voucherVo : statResMemberList) {
				reservationAvaiableCount += Integer.parseInt(voucherVo.getReservationAvaiableCount().replaceAll(",", "")); 
				reservationCount += Integer.parseInt(voucherVo.getReservationCount().replaceAll(",", ""));
				noshowCount += Integer.parseInt(voucherVo.getNoshowCount().replaceAll(",", ""));
				entranceCount += Integer.parseInt(voucherVo.getEntranceCount().replaceAll(",", ""));
			}
			
			VoucherVo statResMember = new VoucherVo();
			statResMember.setStoreName("전체");
			statResMember.setDiffDate(statResMemberList.get(0).getDiffDate());
			statResMember.setReservationAvaiableCount(formatter.format(reservationAvaiableCount) + "");
			statResMember.setReservationCount(formatter.format(reservationCount) + "");
			statResMember.setNoshowCount(formatter.format(noshowCount) + "");
			statResMember.setEntranceCount(formatter.format(entranceCount) + "");
			statResMember.setSortOrder(0);
			
			List<VoucherVo> calcStatResMemberList = new ArrayList<VoucherVo>(); 
			calcStatResMemberList.add(statResMember);
			sortOrder = 1;
			for(VoucherVo voucherVo : statResMemberList) {
				voucherVo.setSortOrder(sortOrder);
				calcStatResMemberList.add(voucherVo);
				sortOrder++;
			}
			Collections.sort(calcStatResMemberList, new ListSortComparator());
			
			mav.addObject("statList", calcStatList);
			mav.addObject("memberStatList", calcStatMemberList);
			mav.addObject("memberResStatList", calcStatResMemberList);
		} else {
			mav.addObject("statList", null);
			mav.addObject("memberStatList", null);
			mav.addObject("memberResStatList", null);
		}
		
		return mav;
	}
	
	@GetMapping("refundList.view")
	public ModelAndView refundList(HttpServletRequest request, VoucherDto voucherDto) {
		ModelAndView mav = new ModelAndView("voucher/refundList");
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		
		if(voucherDto.getStoreId() == null) {
			voucherDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		}
		
		mav.addObject("list", voucherService.selectVoucherRefundList(voucherDto));
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("storeId", voucherDto.getStoreId());
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
	
	@PostMapping("voucherTimeUpdate.proc")
	@ResponseBody
	public JSONObject voucherTimeUpdate(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.voucherTimeUpdate(voucherDto, request);
		return result;
	}
	
	@PostMapping("voucherValidUpdate.proc")
	@ResponseBody
	public JSONObject voucherValidUpdateProc(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.voucherValidUpdate(voucherDto, request);
		return result;
	}
	
	@PostMapping("voucherCancel.proc")
	@ResponseBody
	public JSONObject voucherCancelProc(HttpServletRequest request, @ModelAttribute VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		result = voucherService.voucherCancel(voucherDto, request);
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
//		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		result = memberService.checkMemberCellPhone(memberDto);
		//기회원 바우처 리스트 가져오기
		if(CrevillConstants.RESULT_FAIL.equals(result.get("resultCd"))) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);	//기회원 이므로 성공 코드 리턴
			voucherSaleDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
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
	
	@PostMapping("getVoucherStatList.proc")
	@ResponseBody
	public List<VoucherVo> getVoucherStatList(HttpServletRequest request, VoucherDto voucherDto){
		return voucherService.selectVoucherStat(voucherDto);
	}
}