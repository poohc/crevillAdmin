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

@Service
public class EntranceService {

	@Autowired
	private EntranceMapper entranceMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<EntranceVo> selectEntranceList(EntranceDto entranceDto){
		return entranceMapper.selectEntranceList(entranceDto);
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
		entranceDto.setScheduleId(entranceVo.getScheduleId());
		entranceDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
 	    entranceDto.setChildrenName(entranceVo.getChildrenName());
		entranceDto.setMemberQrCode(entranceVo.getMemberQrCode());
		
		if(entranceMapper.insertScheduleEntranceMember(entranceDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
}