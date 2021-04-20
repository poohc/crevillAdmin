package kr.co.crevill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.branches.BranchesService;
import kr.co.crevill.branches.NoticeDto;
import kr.co.crevill.common.CommonDto;
import kr.co.crevill.common.CommonService;
import kr.co.crevill.common.CommonVo;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.member.MemberDto;
import kr.co.crevill.member.MemberService;
import kr.co.crevill.reservation.ReservationService;
import kr.co.crevill.schedule.ScheduleDto;
import kr.co.crevill.staff.InstructorDto;
import kr.co.crevill.staff.StaffService;

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
}