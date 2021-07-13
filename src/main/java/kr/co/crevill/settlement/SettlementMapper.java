package kr.co.crevill.settlement;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SettlementMapper {
	public List<SettlementVo> selectDailySettlementList(SettlementDto settlementDto);
	public SettlementVo selectVoucherSaleTotalPrice(SettlementDto settlementDto);
	public SettlementVo selectVisitMemberCount(SettlementDto settlementDto);
	public SettlementVo selectNoshowMemberCount(SettlementDto settlementDto);
	public SettlementVo selectTodayCancelCount(SettlementDto settlementDto);
	public SettlementVo selectMemberWithdrawCount(SettlementDto settlementDto);
	public SettlementVo selectVoucherRepurchaseCount(SettlementDto settlementDto);
	public List<SettlementVo> selectWeekVisitMemberInfo(SettlementDto settlementDto);
	public List<SettlementVo> selectWeekVisitInfo(SettlementDto settlementDto);
	public List<SettlementVo> selectWeekRevenue(SettlementDto settlementDto);
	public SettlementVo selectEnterInfo(SettlementDto settlementDto);
	public List<SettlementVo> selectRepurchaseList(SettlementDto settlementDto);
	public List<SettlementVo> selectVoucherSalePriceList(SettlementDto settlementDto);
	public int insertDailySettlement(SettlementDto settlementDto);
}