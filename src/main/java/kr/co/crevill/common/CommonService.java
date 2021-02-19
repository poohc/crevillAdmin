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
	
	public List<CommonCodeVo> selectCommonCode(CommonCodeDto commonCodeDto){
		return commonMapper.selectCommonCode(commonCodeDto);
	}
	
	public List<OfficeInfoVo> selectOfficeList(){
		return commonMapper.selectOfficeList();
	}
	
}