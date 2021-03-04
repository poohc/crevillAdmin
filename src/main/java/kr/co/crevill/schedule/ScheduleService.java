package kr.co.crevill.schedule;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CommonMapper;
import kr.co.crevill.common.CrevillConstants;

@Service
public class ScheduleService {

	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private ScheduleMapper scheduleMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int selectScheduleCount(ScheduleDto scheduleDto) {
		return scheduleMapper.selectScheduleCount(scheduleDto);
	}
	
	public List<ScheduleVo> selectScheduleList(ScheduleDto scheduleDto){
		return scheduleMapper.selectScheduleList(scheduleDto);
	}
	
	public JSONObject insertSchedule(ScheduleDto scheduleDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		if(scheduleDto.getOperationType().split(",").length > 1) {
			scheduleDto.setOperationType("BOTH");
		}
		
		//TODO 세션처리 되면 삭제할 것
		scheduleDto.setRegId("devjyp");
		//직원정보 INSERT 
		if(scheduleMapper.insertSchedule(scheduleDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	
	
}