package kr.co.crevill.staff;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CommonMapper;
import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.CommonUtil;
import kr.co.crevill.common.FileDto;
import kr.co.crevill.common.FileVo;

@Service
public class StaffService {
	
	@Autowired
	private StaffMapper staffMapper;

	@Autowired
	private CommonMapper commonMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int selectStaffCount(StaffDto staffDto){
		return staffMapper.selectStaffCount(staffDto);
	}
	
	public List<StaffVo> selectStaffList(StaffDto staffDto){
		return staffMapper.selectStaffList(staffDto);
	}
	
	/**
	 * 직원 저장 처리 
	 * @methodName : insertStaffInfo
	 * @author : Juyoung Park
	 * @date : 2021.02.16
	 * @param staffDto
	 * @return
	 */
	public JSONObject insertStaffInfo(StaffDto staffDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		if(staffDto.getIdPicture() != null && !staffDto.getIdPicture().isEmpty()) {
			FileVo fileVo = commonMapper.selectImagesIdx();
			staffDto.setIdPictureIdx(fileVo.getImageIdx());
			FileDto fileDto = CommonUtil.setBlobByMultiPartFile(staffDto.getIdPicture());
			fileDto.setImageIdx(fileVo.getImageIdx());
			fileDto.setDescription("직원사진");
			commonMapper.insertImages(fileDto);
		}
		
		if(staffDto.getHealthCertificate() != null && !staffDto.getHealthCertificate().isEmpty()) {
			FileVo fileVo = commonMapper.selectFileIdx();
			staffDto.setHealthCertificateIdx(fileVo.getFileIdx());
			FileDto fileDto = CommonUtil.setBlobByMultiPartFile(staffDto.getIdPicture());
			fileDto.setFileIdx(fileVo.getFileIdx());
			fileDto.setDescription("보건증");
			commonMapper.insertFiles(fileDto);
		}
		
		if(staffDto.getResume() != null && !staffDto.getResume().isEmpty()) {
			FileVo fileVo = commonMapper.selectFileIdx();
			staffDto.setResumeIdx(fileVo.getFileIdx());
			FileDto fileDto = CommonUtil.setBlobByMultiPartFile(staffDto.getIdPicture());
			fileDto.setFileIdx(fileVo.getFileIdx());
			fileDto.setDescription("이력서");
			commonMapper.insertFiles(fileDto);
		}
		
		//직원정보 INSERT 
		if(staffMapper.insertStaffInfo(staffDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}	
	
	
	
}