package kr.co.crevill.settlement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.SessionUtil;

@Service
public class SettlementService {
	
	@Autowired
	private SettlementMapper settlementMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<SettlementVo> selectDailySettlementList(SettlementDto settlementDto){
		return settlementMapper.selectDailySettlementList(settlementDto);
	}
	
	public SettlementVo selectVoucherSaleTotalPrice(SettlementDto settlementDto){
		return settlementMapper.selectVoucherSaleTotalPrice(settlementDto);
	}
	public SettlementVo selectVisitMemberCount(SettlementDto settlementDto){
		return settlementMapper.selectVisitMemberCount(settlementDto);
	}
	public SettlementVo selectNoshowMemberCount(SettlementDto settlementDto){
		return settlementMapper.selectNoshowMemberCount(settlementDto);
	}
	public SettlementVo selectTodayCancelCount(SettlementDto settlementDto){
		return settlementMapper.selectTodayCancelCount(settlementDto);
	}
	public SettlementVo selectMemberWithdrawCount(SettlementDto settlementDto){
		return settlementMapper.selectMemberWithdrawCount(settlementDto);
	}
	public SettlementVo selectVoucherRepurchaseCount(SettlementDto settlementDto){
		return settlementMapper.selectVoucherRepurchaseCount(settlementDto);
	}
	public List<SettlementVo> selectWeekVisitMemberInfo(SettlementDto settlementDto){
		return settlementMapper.selectWeekVisitMemberInfo(settlementDto);
	}
	public List<SettlementVo> selectWeekVisitInfo(SettlementDto settlementDto){
		return settlementMapper.selectWeekVisitInfo(settlementDto);
	}
	public List<SettlementVo> selectWeekRevenue(SettlementDto settlementDto){
		return settlementMapper.selectWeekRevenue(settlementDto);
	}
	public SettlementVo selectEnterInfo(SettlementDto settlementDto){
		return settlementMapper.selectEnterInfo(settlementDto);
	}
	public List<SettlementVo> selectRepurchaseList(SettlementDto settlementDto){
		return settlementMapper.selectRepurchaseList(settlementDto);
	}
	public List<SettlementVo> selectVoucherSalePriceList(SettlementDto settlementDto){
		return settlementMapper.selectVoucherSalePriceList(settlementDto);
	}
	
	public JSONObject insertDailySettlement(SettlementDto settlementDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		HttpSession session = request.getSession();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		settlementDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		if(settlementMapper.insertDailySettlement(settlementDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
}