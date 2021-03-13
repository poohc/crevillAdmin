package kr.co.crevill;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.crevill.common.CommonService;
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
	private CommonService commonService;
	
	@GetMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("dashboard");
		InstructorDto instructorDto = new InstructorDto();
		ScheduleDto scheduleDto = new ScheduleDto();
		MemberDto memberDto = new MemberDto();
		mav.addObject("currentYear", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")));
		mav.addObject("currentMonth", LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM")));
		mav.addObject("currentDay", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd")));
		mav.addObject("voucherStatInfo", commonService.selectVoucherStatInfo());
		mav.addObject("statInfo", commonService.selectStatInfo());
		mav.addObject("memberCount", memberService.selectMemberCount(memberDto));
		mav.addObject("nstaffList", staffService.selectInstructorList(instructorDto));
		mav.addObject("reservationList", reservationService.selectReservationList(scheduleDto));
		return mav;
	}
}