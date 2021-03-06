package kr.co.crevill.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import kr.co.crevill.staff.StaffVo;

public class SessionUtil {
	public static StaffVo getSessionStaffVo(HttpServletRequest request) {
		HttpSession session = request.getSession();
		StaffVo staffVo = (StaffVo) session.getAttribute("staffVo");
		return staffVo;
	}
}