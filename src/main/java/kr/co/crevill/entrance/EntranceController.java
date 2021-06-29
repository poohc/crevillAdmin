package kr.co.crevill.entrance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.store.StoreDto;
import kr.co.crevill.store.StoreService;

@Controller
@RequestMapping("entrance")
public class EntranceController {
	
	@Autowired
	private EntranceService entranceService;
	
	@Autowired
	private StoreService storeService;
	
	@GetMapping("classMember.view")
	public ModelAndView member(HttpServletRequest request, EntranceDto entranceDto) {
		ModelAndView mav = new ModelAndView("entrance/classMember");
		mav.addObject("currentTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH")));
		entranceDto.setScheduleStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		
		StoreDto storeDto = new StoreDto();
		if(entranceDto.getStoreId() == null) {
			entranceDto.setStoreId("");
			storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		} else {
			mav.addObject("storeId", entranceDto.getStoreId());
		}
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		entranceDto.setSearchType("TODAY");
		entranceDto.setIsTutoringYn("N");
		mav.addObject("titleList", entranceService.selectEntranceTitleList(entranceDto));
		mav.addObject("list", entranceService.selectEntranceList(entranceDto));
		return mav;
	}
	
	@GetMapping("nonMember.view")
	public ModelAndView nonMember(HttpServletRequest request, EntranceDto entranceDto) {
		ModelAndView mav = new ModelAndView("entrance/nonMember");
		mav.addObject("list", entranceService.selectNonMemberScheduleList());
		mav.addObject("voucherList", entranceService.selectNonMemberVoucherList(entranceDto));
		return mav;
	}
	
	@GetMapping("nonMemberList.view")
	public ModelAndView nonMemberList(HttpServletRequest request, EntranceDto entranceDto) {
		ModelAndView mav = new ModelAndView("entrance/nonMemberList");
		mav.addObject("list", entranceService.selectNonMemberEntranceList(entranceDto));
		return mav;
	}
	
	@PostMapping("checkVoucher.proc")
	@ResponseBody
	public JSONObject checkVoucher(HttpServletRequest request, EntranceDto entranceDto) {
		JSONObject result = new JSONObject();
		result = entranceService.checkVoucher(entranceDto); 
		return result;
	}
	
	@PostMapping("entrance.proc")
	@ResponseBody
	public JSONObject entranceProc(HttpServletRequest request, EntranceDto entranceDto) {
		JSONObject result = new JSONObject();
		result = entranceService.entrance(entranceDto, request); 
		return result;
	}
	
	@PostMapping("nonMemberEntrance.proc")
	@ResponseBody
	public JSONObject nonMemberEntranceProc(HttpServletRequest request, EntranceDto entranceDto) {
		JSONObject result = new JSONObject();
		result = entranceService.nonMemberEntrance(entranceDto, request); 
		return result;
	}
}