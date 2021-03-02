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
	
}