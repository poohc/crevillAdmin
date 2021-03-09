package kr.co.crevill.reservation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CommonMapper;
import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.play.PlayMapper;

@Service
public class ReservationService {

	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private ReservationMapper reservationMapper;
	
	@Autowired
	private PlayMapper playMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int selectReservationCount(ReservationDto reservationDto) {
		return reservationMapper.selectReservationCount(reservationDto);
	}
	
	public List<ReservationVo> selectReservationList(ReservationDto reservationDto){
		return reservationMapper.selectReservationList(reservationDto);
	}
	
	public JSONObject insertReservation(ReservationDto reservationDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		reservationDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		reservationDto.setStatus(CrevillConstants.RESERVATION_STATUS_READY);
		//직원정보 INSERT 
		if(reservationMapper.insertReservation(reservationDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
}