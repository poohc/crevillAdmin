package kr.co.crevill.entrance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.reservation.ReservationMapper;
import kr.co.crevill.voucher.VoucherDto;
import kr.co.crevill.voucher.VoucherMapper;
import kr.co.crevill.voucher.VoucherVo;

@Service
public class EntranceService {

	@Autowired
	private EntranceMapper entranceMapper;
	
	@Autowired
	private ReservationMapper reservationMapper;
	
	@Autowired
	private VoucherMapper voucherMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<EntranceVo> selectEntranceTitleList(EntranceDto entranceDto){
		return entranceMapper.selectEntranceTitleList(entranceDto);
	}
	
	public List<EntranceVo> selectEntranceList(EntranceDto entranceDto){
		return entranceMapper.selectEntranceList(entranceDto);
	}
	
	public EntranceVo selectNonMemberScheduleList(){
		return entranceMapper.selectNonMemberScheduleList();
	}
	
	public List<EntranceVo> selectNonMemberVoucherList(EntranceDto entranceDto){
		return entranceMapper.selectNonMemberVoucherList(entranceDto);
	}

	public List<EntranceVo> selectNonMemberEntranceList(EntranceDto entranceDto){
		return entranceMapper.selectNonMemberEntranceList(entranceDto);
	}
	
	public JSONObject checkVoucher(EntranceDto entranceDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		EntranceVo entranceVo = entranceMapper.checkVoucher(entranceDto); 
		if("Y".equals(entranceVo.getVoucherAvailableYn())) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	public JSONObject entrance(EntranceDto entranceDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		EntranceVo entranceVo = entranceMapper.selectEntranceInfo(entranceDto);
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		entranceDto.setVoucherNo(entranceVo.getVoucherNo());
		entranceDto.setUseTime(entranceVo.getPlayTime());
		entranceDto.setStatus(CrevillConstants.VOUCHER_STATUS_USED);
		entranceDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
 	    entranceDto.setChildName(entranceVo.getChildName());
 	    entranceDto.setChildBirthday(entranceVo.getChildBirthday());
 	    entranceDto.setMemberQrCode(entranceVo.getMemberQrCode());
 	    entranceDto.setChildSex(entranceVo.getChildSex());
 	    
 	    //1회권일 경우 바우처 상태 사용완료로 업데이트 2021.06.14
 	    VoucherDto voucherDto = new VoucherDto();
 	    voucherDto.setVoucherNo(entranceVo.getVoucherNo());
 	    VoucherVo voucherVo = voucherMapper.selectVoucherInfo(voucherDto);
 	    if("1회권".equals(voucherVo.getTicketName())) {
 	    	voucherDto.setStatus(CrevillConstants.VOUCHER_STATUS_USED);
 	    	voucherMapper.updateVoucher(voucherDto);
 	    }
 	    
		if(entranceMapper.insertScheduleEntranceMember(entranceDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	/**
	 * 비회원 입장처리
	 * @methodName : nonMemberEntrance
	 * @author : Juyoung Park
	 * @date : 2021.04.01
	 * @param entranceDto
	 * @param request
	 * @return
	 */
	public JSONObject nonMemberEntrance(EntranceDto entranceDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		entranceDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		//입장처리
		if(entranceMapper.insertScheduleEntranceNonMember(entranceDto) > 0) {
   			result.put("resultCd", CrevillConstants.RESULT_SUCC);
   		}
		return result;
	}
	
}