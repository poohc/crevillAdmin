package kr.co.crevill.settlement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.store.StoreDto;
import kr.co.crevill.store.StoreService;
import kr.co.crevill.store.StoreVo;

@Controller
@RequestMapping("settlement")
public class SettlementController {
	
	@Autowired
	private SettlementService settlementService;
	
	@Autowired
	private StoreService storeService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, SettlementDto settlementDto) {
		ModelAndView mav = new ModelAndView("settlement/list");
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		mav.addObject("list", settlementService.selectDailySettlementList(settlementDto));
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("storeId", settlementDto.getStoreId());
		return mav;
	}
	
	@GetMapping("daily.view")
	public ModelAndView daily(HttpServletRequest request, SettlementDto settlementDto) {
		ModelAndView mav = new ModelAndView("settlement/daily");
		String storeName = "";
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		
		if(settlementDto.getStoreId() == null) {
			settlementDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		}
		
		if(settlementDto.getSearchDate() == null) {
			settlementDto.setSearchDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		
		for(StoreVo sVo : storeService.selectStoreList(storeDto)) {
			if(sVo.getStoreId().equals(settlementDto.getStoreId())) {
				storeName = sVo.getStoreNameShort();
			}
		}
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("storeId", settlementDto.getStoreId());
		mav.addObject("storeName", storeName);
		mav.addObject("info1", settlementService.selectVoucherSaleTotalPrice(settlementDto));
		mav.addObject("info2", settlementService.selectVisitMemberCount(settlementDto));
		mav.addObject("info3", settlementService.selectNoshowMemberCount(settlementDto));
		mav.addObject("info4", settlementService.selectTodayCancelCount(settlementDto));
		mav.addObject("info5", settlementService.selectMemberWithdrawCount(settlementDto));
		mav.addObject("info6", settlementService.selectVoucherRepurchaseCount(settlementDto));
		mav.addObject("info7", settlementService.selectEnterInfo(settlementDto));
		
		List<SettlementVo> list1 = settlementService.selectRepurchaseList(settlementDto);
		int list1Size = 0;
		if(list1 != null && list1.size() > 0) {
			list1Size = list1.size();
		}
		
		mav.addObject("list1", list1);
		mav.addObject("list1Size", list1Size);
		
		List<SettlementVo> list2 = settlementService.selectVoucherSalePriceList(settlementDto);
		
		int sumTotalPrice = 0;
		
		for(SettlementVo vo : list2) {
			sumTotalPrice += Integer.parseInt(vo.getTotalPrice()); 
		}
		
		mav.addObject("list2", list2);
		mav.addObject("sumTotalPrice", sumTotalPrice);
		return mav;
	}
	
	@PostMapping("regist.proc")
	@ResponseBody
	public JSONObject registProc(HttpServletRequest request, @ModelAttribute SettlementDto settlementDto) {
		JSONObject result = new JSONObject();
		result = settlementService.insertDailySettlement(settlementDto, request);
		return result;
	}
	
	@PostMapping("getWeekVisitMemberInfo.proc")
	@ResponseBody
	public List<SettlementVo> getWeekVisitMemberInfo(HttpServletRequest request, SettlementDto settlementDto){
		return settlementService.selectWeekVisitMemberInfo(settlementDto);
	}
	
	@PostMapping("selectWeekVisitInfo.proc")
	@ResponseBody
	public List<SettlementVo> selectWeekVisitInfo(HttpServletRequest request, SettlementDto settlementDto){
		List<SettlementVo> list = settlementService.selectWeekVisitInfo(settlementDto);
		return list;
	}
	
	@PostMapping("selectWeekRevenue.proc")
	@ResponseBody
	public List<SettlementVo> selectWeekRevenue(HttpServletRequest request, SettlementDto settlementDto){
		return settlementService.selectWeekRevenue(settlementDto);
	}
}