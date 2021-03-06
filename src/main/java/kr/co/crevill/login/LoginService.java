package kr.co.crevill.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.staff.StaffDto;
import kr.co.crevill.staff.StaffMapper;
import kr.co.crevill.staff.StaffVo;

@Service
public class LoginService {
	
	@Autowired
	private StaffMapper staffMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public JSONObject loginProcess(StaffDto staffDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		StaffVo staffVo = staffMapper.selectStaffInfo(staffDto);
		if(staffVo != null && !staffVo.getStaffId().isEmpty()) {
			session.setAttribute("staffVo", staffVo);
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		 
		return result;
	}
}