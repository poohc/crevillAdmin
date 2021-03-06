package kr.co.crevill.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

	@Autowired
	private CommonMapper commonMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public FileVo selectImageInfo(FileDto fileDto) {
		return commonMapper.selectImageInfo(fileDto);
	}
	
	public List<CommonCodeVo> selectCommonCode(CommonCodeDto commonCodeDto){
		return commonMapper.selectCommonCode(commonCodeDto);
	}
	
	public List<CommonCodeVo> selectNationalCode(){
		return commonMapper.selectNationalCode();
	}
	
	public CommonVo selectStatInfo(CommonDto commonDto) {
		return commonMapper.selectStatInfo(commonDto);
	}
	
	public List<CommonVo> selectVoucherStatInfo() {
		return commonMapper.selectVoucherStatInfo();
	}
	
	public List<CommonVo> selectTodayReservationInfo(CommonDto commonDto){
		return commonMapper.selectTodayReservationInfo(commonDto);
	}
	
	public CommonVo selectVoucherStatistics(CommonDto commonDto) {
		return commonMapper.selectVoucherStatistics(commonDto);
	}
	
	public CommonVo selectNoShowCount(CommonDto commonDto) {
		return commonMapper.selectNoShowCount(commonDto);
	}
}