package kr.co.crevill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonView;

import kr.co.crevill.branches.BranchesService;
import kr.co.crevill.branches.NoticeDto;
import kr.co.crevill.common.CommonDto;
import kr.co.crevill.common.CommonService;
import kr.co.crevill.common.CommonVo;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.member.MemberDto;
import kr.co.crevill.member.MemberService;
import kr.co.crevill.reservation.ReservationService;
import kr.co.crevill.reservation.ReservationVo;
import kr.co.crevill.schedule.ScheduleDto;
import kr.co.crevill.staff.InstructorDto;
import kr.co.crevill.staff.StaffService;
import kr.co.crevill.store.StoreDto;
import kr.co.crevill.store.StoreService;

@Controller
public class CrevillController {

	@Autowired
	private StaffService staffService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BranchesService branchesService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private StoreService storeService;
	
	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("dashboard");
		InstructorDto instructorDto = new InstructorDto();
		instructorDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		MemberDto memberDto = new MemberDto();
		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		CommonDto commonDto = new CommonDto();
		commonDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		NoticeDto noticeDto = new NoticeDto();
		mav.addObject("currentYear", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")));
		mav.addObject("currentMonth", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM")));
		mav.addObject("currentDay", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd")));
		StoreDto storeDto = new StoreDto();
		storeDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		mav.addObject("storeList", storeService.selectStoreList(storeDto));
		mav.addObject("voucherStatInfo", commonService.selectVoucherStatInfo());
		mav.addObject("noticeList", branchesService.selectHeadquarterNoticeList(noticeDto));
		mav.addObject("statInfo", commonService.selectStatInfo(commonDto));
		mav.addObject("memberCount", memberService.selectMemberCount(memberDto));
		mav.addObject("nstaffList", staffService.selectInstructorList(instructorDto));
		mav.addObject("reservationList", reservationService.selectReservationList(scheduleDto));
		mav.addObject("todayReservationList", commonService.selectTodayReservationInfo(commonDto));
		return mav;
	}
	
	@PostMapping("/main/getTodayReservationInfo.proc")
	@ResponseBody
	public List<CommonVo> selectTodayReservationInfo(HttpServletRequest request, @ModelAttribute CommonDto commonDto) {
		return commonService.selectTodayReservationInfo(commonDto);
	}
	
	@GetMapping("/send/getTodayReservationList.proc")
	@ResponseBody
	public List<Map<String, Object>> getTodayReservationList(HttpServletRequest request, @RequestParam String storeId){
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setScheduleStart(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		scheduleDto.setStoreId(storeId); 
		List<ReservationVo> reservationList = reservationService.selectReservationList(scheduleDto);
		List<Map<String, Object>> outList = new ArrayList<>();
		for(ReservationVo rVo : reservationList){
			Map<String, Object> tempMap = new HashMap<>();
			tempMap.put("storeName", rVo.getStoreName());
			tempMap.put("reservationDate", rVo.getReservationDate());
			tempMap.put("reservationTime", rVo.getReservationTime());
			tempMap.put("className", rVo.getPlayName() + "("+ rVo.getReservationCnt() +" / "+ rVo.getNumberOfPeople() +")");
			tempMap.put("tutoringName", rVo.getPlayName() + "("+ rVo.getTutoringCnt() +" / "+ rVo.getTutoringNumber() +")");
			outList.add(tempMap);
		}
		return outList;
	}
	
}