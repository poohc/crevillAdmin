package kr.co.crevill.reservation;

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
import kr.co.crevill.schedule.ScheduleDto;
import kr.co.crevill.store.StoreDto;
import kr.co.crevill.store.StoreService;

@Controller
@RequestMapping("reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private StoreService storeService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("list.view")
	public ModelAndView list(HttpServletRequest request, ScheduleDto scheduleDto) {
		ModelAndView mav = new ModelAndView("reservation/list");
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		if(scheduleDto.getScheduleStart() == null || scheduleDto.getScheduleStart().isEmpty()) {
			scheduleDto.setScheduleStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		if(scheduleDto.getStoreId() == null) {
			scheduleDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		}
		mav.addObject("scheduleStart", scheduleDto.getScheduleStart());
		mav.addObject("list", reservationService.selectReservationList(scheduleDto));
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("storeId", scheduleDto.getStoreId());
		ReservationDto reservationDto = new ReservationDto();
		reservationDto.setStoreId(scheduleDto.getStoreId());
		mav.addObject("info", reservationService.selectReservationStatInfo(reservationDto));
		return mav;
	}
	
	@GetMapping("freeList.view")
	public ModelAndView freeList(HttpServletRequest request, ScheduleDto scheduleDto) {
		ModelAndView mav = new ModelAndView("reservation/freeList");
		scheduleDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		scheduleDto.setIsFree("Y");
		if(scheduleDto.getScheduleStart() == null || scheduleDto.getScheduleStart().isEmpty()) {
			scheduleDto.setScheduleStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		mav.addObject("scheduleStart", scheduleDto.getScheduleStart());
		mav.addObject("list", reservationService.selectReservationList(scheduleDto));
		return mav;
	}
	
	@GetMapping("search.view")
	public ModelAndView search(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reservation/search");
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("sessionStoreId", storeDto.getStoreId());
		return mav;
	}
	
	@GetMapping("regist.view")
	public ModelAndView regist(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("reservation/regist");
		StoreDto storeDto = new StoreDto();
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		return mav;
	}
	
	@PostMapping("regist.proc")
	@ResponseBody
	public JSONObject registProc(HttpServletRequest request, @ModelAttribute ReservationDto reservationDto) {
		JSONObject result = new JSONObject();
		result = reservationService.insertReservation(reservationDto, request);
		return result;
	}
	
	@PostMapping("getReservationList.proc")
	@ResponseBody
	public List<ReservationVo> getReservationList(HttpServletRequest request, @ModelAttribute ScheduleDto scheduleDto) {
		if(scheduleDto.getScheduleStart().isEmpty()) {
			scheduleDto.setScheduleType("ALL");
			scheduleDto.setScheduleStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		}
		logger.info("scheduleDto : " + scheduleDto.toString());
		return reservationService.selectReservationSearchList(scheduleDto);
	}
	
	@PostMapping("getTrialReservationList.proc")
	@ResponseBody
	public List<ReservationVo> getTrialReservationList(HttpServletRequest request, @ModelAttribute ScheduleDto scheduleDto) {
		scheduleDto.setScheduleStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		scheduleDto.setIsFree("Y");
		return reservationService.selectReservationList(scheduleDto);
	}
	
	@PostMapping("cancel.proc")
	@ResponseBody
	public JSONObject cancelProc(HttpServletRequest request, @ModelAttribute ReservationDto reservationDto) {
		JSONObject result = new JSONObject();
		result = reservationService.cancelReservation(reservationDto, request);
		return result;
	}
}