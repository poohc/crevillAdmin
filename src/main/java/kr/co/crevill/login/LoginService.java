package kr.co.crevill.login;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CrevillConstants;

@Service
public class LoginService {
	
	@Autowired
	private LoginMapper loginMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public JSONObject loginProcess(LoginDto loginDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		if(loginMapper.selectLoginStaff(loginDto) > 0) {
			request.setAttribute("loginId", loginDto.getStaffId());
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		} 
		return result;
	}
}