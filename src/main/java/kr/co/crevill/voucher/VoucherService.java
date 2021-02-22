package kr.co.crevill.voucher;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CommonMapper;
import kr.co.crevill.common.CommonUtil;
import kr.co.crevill.common.CrevillConstants;
import kr.co.crevill.common.FileDto;
import kr.co.crevill.common.FileVo;

@Service
public class VoucherService {
	
	@Autowired
	private VoucherMapper voucherMapper;

	@Autowired
	private CommonMapper commonMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int selectVoucherCount(VoucherDto voucherDto) {
		return voucherMapper.selectVoucherCount(voucherDto);
	}
	public List<VoucherVo> selectVoucherList(VoucherDto voucherDto){
		return voucherMapper.selectVoucherList(voucherDto);
	}
	public List<VoucherVo> selectVoucherAttributeList(VoucherDto voucherDto){
		return voucherMapper.selectVoucherAttributeList(voucherDto);
	}
	
	/**
	 * 직원 저장 처리 
	 * @methodName : insertVoucher
	 * @author : Juyoung Park
	 * @date : 2021.02.22
	 * @param voucherDto
	 * @return
	 */
	public JSONObject insertVoucher(VoucherDto voucherDto) {
		JSONObject result = new JSONObject();
		//TODO 로그인 세션처리시 삭제할것
		voucherDto.setRegId("devjyp");
		
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		if(voucherDto.getImage() != null && !voucherDto.getImage().isEmpty()) {
			FileVo fileVo = commonMapper.selectImagesIdx();
			voucherDto.setImageIdx(fileVo.getImageIdx());
			FileDto fileDto = CommonUtil.setBlobByMultiPartFile(voucherDto.getImage());
			fileDto.setImageIdx(fileVo.getImageIdx());
			fileDto.setDescription("바우처사진");
			commonMapper.insertImages(fileDto);
		}
		//VOUCHER_NO 설정
		voucherDto.setVoucherNo(voucherMapper.selectVoucherNo());
		
		if(voucherMapper.insertVoucher(voucherDto) > 0) {
			//선택된 전달매체 모두 INSERT 성공해야 SUCC
			int cnt = voucherDto.getAttribute().split(",").length;
			int insCnt = 0;
			for(String attribute : voucherDto.getAttribute().split(",")) {
				voucherDto.setAttribute(attribute);
				if(voucherMapper.insertVoucherAttribute(voucherDto) > 0) {
					insCnt++;
				}	
			}
			if(cnt == insCnt) {
				result.put("resultCd", CrevillConstants.RESULT_SUCC);	
			}
		}
		return result;
	}	
}