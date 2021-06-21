package kr.co.crevill.schedule;

import java.util.ArrayList;
import java.util.Arrays;
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

import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.store.StoreDto;
import kr.co.crevill.store.StoreService;
import kr.co.crevill.storeProgram.StoreProgramDto;
import kr.co.crevill.storeProgram.StoreProgramService;
import kr.co.crevill.voucher.VoucherDto;
import kr.co.crevill.voucher.VoucherService;
import kr.co.crevill.voucher.VoucherVo;

@Controller
@RequestMapping("schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private VoucherService voucherService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StoreProgramService storeProgramService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, ScheduleDto scheduleDto) {
		ModelAndView mav = new ModelAndView("schedule/list");
		if(scheduleDto.getStoreId() == null) {
			scheduleDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		}
		scheduleDto.setScheduleStart("99991231");
		scheduleDto.setScheduleType("ING");
		mav.addObject("list", scheduleService.selectScheduleList(scheduleDto));
		mav.addObject("info", scheduleService.selectScheduleStatInfo(scheduleDto));
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("storeId", scheduleDto.getStoreId());
		return mav;
	}
	
	@GetMapping("search.view")
	public ModelAndView search(HttpServletRequest request, ScheduleDto scheduleDto) {
		ModelAndView mav = new ModelAndView("schedule/search");
		scheduleDto.setScheduleType("ALL");
		scheduleDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		mav.addObject("allList", scheduleService.selectScheduleList(scheduleDto));
//		scheduleDto.setScheduleType("ING");
//		mav.addObject("ingList", scheduleService.selectScheduleList(scheduleDto));
//		scheduleDto.setScheduleType("END");
//		mav.addObject("endList", scheduleService.selectScheduleList(scheduleDto));
		
		String scheduleDate = "";
		if(scheduleDto.getScheduleStart() != null) {
			scheduleDate = scheduleDto.getScheduleStart().substring(0,4) + "-" + scheduleDto.getScheduleStart().substring(4,6) + "-" + scheduleDto.getScheduleStart().substring(6,8);
			mav.addObject("scheduleDate", scheduleDate);
		}
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("storeId", scheduleDto.getStoreId());
		return mav;
	}
	
	@GetMapping("regist.view")
	public ModelAndView regist(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("schedule/regist");
		List<String> timeList = new ArrayList<String>();
		for(int i=0; i<24; i++) {
			if(i >= 10) {
				timeList.add(i + "");
			} else {
				timeList.add("0" + i);
			}
		}
		mav.addObject("timeList", timeList);
		StoreDto storeDto = new StoreDto();
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		
		StoreProgramDto storeProgramDto = new StoreProgramDto();
		storeProgramDto.setStatus(CrevillConstants.PROGRAM_STATUS_ACTIVE);
		mav.addObject("programList", storeProgramService.selectPromotionList(storeProgramDto));
		return mav;
	}
	
	@PostMapping("regist.proc")
	@ResponseBody
	public JSONObject registProc(HttpServletRequest request, @ModelAttribute ScheduleDto scheduleDto) {
		JSONObject result = new JSONObject();
		result = scheduleService.insertSchedule(scheduleDto, request);
		return result;
	}
	
	@PostMapping("getScheduleList.proc")
	@ResponseBody
	public JSONObject getScheduleList(HttpServletRequest request, ScheduleDto scheduleDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		scheduleDto.setScheduleType("ALL");
		
		VoucherDto voucherDto = new VoucherDto();
		voucherDto.setVoucherNo(scheduleDto.getVoucherNo());
		VoucherVo voucherVo = voucherService.selectVoucherInfo(voucherDto);
		String grade = "normal";
		
		String[] attributeArray = voucherVo.getAttribute().split(",");
		List<String> attributeList = Arrays.asList(attributeArray);
		
		//스페셜 캠프 확인 프로세스 수정
		if(attributeList.contains("SPECIAL CAMP")) {
			grade = "VIP";
		}
		
		scheduleDto.setGrade(grade);
		List<ScheduleVo> scheduleList = scheduleService.selectScheduleList(scheduleDto);
		if(scheduleList != null && scheduleList.size() > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
			result.put("scheduleList", scheduleList);
			result.put("attributeList", attributeList);
		}
		return result;
	}
	
	@PostMapping("getReservationScheduleCount.proc")
	@ResponseBody
	public JSONObject getReservationScheduleCount(HttpServletRequest request, ScheduleDto scheduleDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		try {
			int count = scheduleService.selectReservationScheduleCount(scheduleDto);
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
			if(count > 0 ) {
				result.put("reservationScheduleYn", "Y");
			} else {
				result.put("reservationScheduleYn", "N");
			}
		} catch (Exception e) {
			logger.error("예약된 스케쥴 확인 여부 처리 중 오류 발생 : " + e);
		}
		return result;
	}
	
	@PostMapping("updateSchedule.proc")
	@ResponseBody
	public JSONObject updateSchedule(HttpServletRequest request, ScheduleDto scheduleDto) {
		JSONObject result = scheduleService.updateSchedule(scheduleDto);
		return result;
	}
	
	@PostMapping("deleteSchedule.proc")
	@ResponseBody
	public JSONObject deleteSchedule(HttpServletRequest request, ScheduleDto scheduleDto) {
		JSONObject result = scheduleService.deleteSchedule(scheduleDto);
		return result;
	}
}