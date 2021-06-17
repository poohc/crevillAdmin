package kr.co.crevill.voucher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import kr.co.crevill.common.SessionUtil;
import kr.co.crevill.member.MemberDto;
import kr.co.crevill.member.MemberMapper;
import kr.co.crevill.member.MemberVo;

@Service
public class VoucherService {
	
	@Autowired
	private VoucherMapper voucherMapper;

	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int selectVoucherCount(VoucherDto voucherDto) {
		return voucherMapper.selectVoucherCount(voucherDto);
	}
	
	public List<VoucherVo> selectVoucherProductList(VoucherDto voucherDto){
		return voucherMapper.selectVoucherProductList(voucherDto);
	}
	
	public List<VoucherVo> selectVoucherList(VoucherDto voucherDto){
		return voucherMapper.selectVoucherList(voucherDto);
	}
	
	public VoucherVo selectVoucherProductInfo(VoucherDto voucherDto) {
		return voucherMapper.selectVoucherProductInfo(voucherDto);
	}
	
	public VoucherVo selectVoucherInfo(VoucherDto voucherDto){
		return voucherMapper.selectVoucherInfo(voucherDto);
	}
	
	public List<VoucherVo> getVoucherList(VoucherDto voucherDto){
		return voucherMapper.getVoucherList(voucherDto);
	}
	
	public List<VoucherVo> selectVoucherProductAttributeList(VoucherDto voucherDto){
		return voucherMapper.selectVoucherProductAttributeList(voucherDto);
	}
	
	public VoucherVo selectVoucherTimeInfo(VoucherDto voucherDto) {
		return voucherMapper.selectVoucherTimeInfo(voucherDto);
	}
	
	public List<VoucherVo> getMemberVoucherList(VoucherSaleDto voucherSaleDto){
		return voucherMapper.getMemberVoucherList(voucherSaleDto);
	}
	
	public List<VoucherVo> getMemberVoucherAllList(VoucherDto voucherDto){
		return voucherMapper.getMemberVoucherAllList(voucherDto);
	}
	
	public List<VoucherVo> getMemberVoucherUseList(VoucherDto voucherDto){
		return voucherMapper.getMemberVoucherUseList(voucherDto);
	}
	
	public List<VoucherVo> selectVoucherSaleList(VoucherDto voucherDto){
		return voucherMapper.selectVoucherSaleList(voucherDto);
	}
	
	public int selectVoucherSaleCancelCount(VoucherDto voucherDto) {
		return voucherMapper.selectVoucherSaleCancelCount(voucherDto);
	}
	
	/**
	 * 바우처 저장 처리 
	 * @methodName : insertVoucher
	 * @author : Juyoung Park
	 * @date : 2021.02.22
	 * @param voucherDto
	 * @return
	 */
	public JSONObject insertVoucher(VoucherDto voucherDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		voucherDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		if(voucherDto.getImage() != null && !voucherDto.getImage().isEmpty()) {
			FileVo fileVo = commonMapper.selectImagesIdx();
			voucherDto.setImageIdx(fileVo.getImageIdx());
			FileDto fileDto = CommonUtil.setBlobByMultiPartFile(voucherDto.getImage());
			fileDto.setImageIdx(fileVo.getImageIdx());
			fileDto.setDescription("바우처사진");
			commonMapper.insertImages(fileDto);
		}
		//VOUCHER_PRODUCT_NO 설정
//		voucherDto.setVoucherNo(voucherMapper.selectVoucherNo());
		voucherDto.setProductNo(voucherMapper.selectVoucherProductNo());
		voucherDto.setStatus(CrevillConstants.VOUCHER_PRODUCT_STATUS_ACTIVE);
		//무제한일 경우 UNLIMIT INSERT
		if("0".equals(voucherDto.getUseTime())){ 
			voucherDto.setUseTime(CrevillConstants.VOUCHER_UNLIMITED_TIME);
		}
		
		if("0".equals(voucherDto.getEndDate())){ 
			voucherDto.setEndDate(CrevillConstants.VOUCHER_UNLIMITED_DATE);
		}
		if(voucherMapper.insertVoucherProduct(voucherDto) > 0) {
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
	
	/**
	 * 바우처 수정처리
	 * @methodName : updateVoucher
	 * @author : Juyoung Park
	 * @date : 2021.03.24
	 * @param voucherDto
	 * @param request
	 * @return
	 */
	public JSONObject updateVoucher(VoucherDto voucherDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		voucherDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		voucherDto.setUpdId(SessionUtil.getSessionStaffVo(request).getStaffId());
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		
		if(voucherDto.getImage() != null && !voucherDto.getImage().isEmpty()) {
			FileVo fileVo = commonMapper.selectImagesIdx();
			voucherDto.setImageIdx(fileVo.getImageIdx());
			FileDto fileDto = CommonUtil.setBlobByMultiPartFile(voucherDto.getImage());
			fileDto.setImageIdx(fileVo.getImageIdx());
			fileDto.setDescription("바우처사진");
			commonMapper.insertImages(fileDto);
		}
		
		//무제한일 경우 UNLIMIT INSERT
		if("0".equals(voucherDto.getUseTime())){ 
			voucherDto.setUseTime(CrevillConstants.VOUCHER_UNLIMITED_TIME);
		}
		
		if("0".equals(voucherDto.getEndDate())){ 
			voucherDto.setEndDate(CrevillConstants.VOUCHER_UNLIMITED_DATE);
		}
		voucherDto.setStatus(CrevillConstants.VOUCHER_PRODUCT_STATUS_ACTIVE);
		//VOUCHER_ATTRIBUTE 테이블 데이터 먼저 삭제
		if(voucherMapper.deleteVoucherProductAttribute(voucherDto) >= 0) {
			if(voucherMapper.updateVoucherProduct(voucherDto) > 0) {
				//선택된 전달매체 모두 INSERT 성공해야 SUCC
				int cnt = voucherDto.getAttribute().split(",").length;
				int insCnt = 0;
				for(String attribute : voucherDto.getAttribute().split(",")) {
					voucherDto.setAttribute(attribute);
					if(voucherMapper.insertVoucherProductAttribute(voucherDto) > 0) {
						insCnt++;
					}	
				}
				if(cnt == insCnt) {
					result.put("resultCd", CrevillConstants.RESULT_SUCC);	
				}
			}
		}
		return result;
	}
	
	public JSONObject voucherValidUpdate(VoucherDto voucherDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		voucherDto.setUpdId(SessionUtil.getSessionStaffVo(request).getStaffId());
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		if(voucherMapper.updateVoucher(voucherDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	public JSONObject voucherCancel(VoucherDto voucherDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		voucherDto.setUpdId(SessionUtil.getSessionStaffVo(request).getStaffId());
		voucherDto.setStatus(CrevillConstants.VOUCHER_STATUS_REFUND);
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		if(voucherMapper.updateVoucher(voucherDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	/**
	 * 바우처 삭제처리
	 * @methodName : deleteVoucher
	 * @author : Juyoung Park
	 * @date : 2021.03.24
	 * @param voucherDto
	 * @return
	 */
	public JSONObject deleteVoucher(VoucherDto voucherDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		if(voucherMapper.deleteVoucherAttribute(voucherDto) > 0) {
			if(voucherMapper.deleteVoucherProduct(voucherDto) > 0) {
				result.put("resultCd", CrevillConstants.RESULT_SUCC);
			}
		}
		return result;
	}
	
	/**
	 * VOUCHER_SALE 테이블 INSERT 처리
	 * @methodName : voucherSaleProc
	 * @author : Juyoung Park
	 * @date : 2021.03.08
	 * @param voucherSaleDto
	 * @param request
	 * @return
	 */
	public JSONObject voucherSaleProc(VoucherSaleDto voucherSaleDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		voucherSaleDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		result.put("resultCd", CrevillConstants.RESULT_FAIL);		
		
		MemberDto memberDto = new MemberDto();
		memberDto.setCellPhone(voucherSaleDto.getBuyCellPhone());
		MemberVo memberVo = memberMapper.getMemberInfo(memberDto);
		
		//모바일 가입 회원의 경우 판매 매장 STORE_ID 로 업데이트
		if(memberVo != null && "MOBILE".equals(memberVo.getStoreId())) {
			logger.info("voucherSaleDto : " + voucherSaleDto.toString());
			memberDto.setStoreId(voucherSaleDto.getStoreId());
			memberDto.setQrCode(memberVo.getQrCode());
			memberMapper.updateMemberParent(memberDto);
		}
		
		/**
		 * 해당 종류 바우처로 VOUCHER 테이블 INSERT 추가 2021.06.08
		 * 1. VOUCHER_NO 생성
		 * 2. 선택된 바우처 상품 정보가져오기
		 * 3. 1,2번 정보로 VOUCHER 테이블 데이터 INSERT 
		 */
		
		String voucherNo = voucherMapper.selectVoucherNo();
		VoucherDto voucherDto = new VoucherDto();
		voucherDto.setProductNo(voucherSaleDto.getProductNo());
		VoucherVo voucherInfo = voucherMapper.selectVoucherProductInfo(voucherDto);
		voucherDto.setVoucherNo(voucherNo);
		voucherDto.setGrade(voucherInfo.getGrade());
		voucherDto.setTicketName(voucherInfo.getTicketName());
		voucherDto.setPrice(voucherInfo.getPrice());
		voucherDto.setSalePrice(voucherInfo.getSalePrice());
		voucherDto.setUseTime(voucherInfo.getUseTime());
		voucherDto.setEndDate(voucherInfo.getExpireMonth());
		voucherDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		voucherDto.setImageIdx(voucherInfo.getImageIdx());
		voucherDto.setStatus(CrevillConstants.VOUCHER_STATUS_SALE);
		voucherDto.setStoreId(voucherSaleDto.getStoreId());
		if(voucherMapper.insertVoucher(voucherDto) > 0) {
			
			//VOUCHER_ATTRIBUTE 테이블 INSERT 추가(VOUCHER_PRODUCT 테이블 수정에 따른 영향을 받지 않기 위해)
			int cnt = voucherInfo.getAttribute().split(",").length;
			int insCnt = 0;
			for(String attribute : voucherInfo.getAttribute().split(",")) {
				voucherDto.setAttribute(attribute);
				if(voucherMapper.insertVoucherAttribute(voucherDto) > 0) {
					insCnt++;
				}	
			}
			if(cnt == insCnt) {
				voucherSaleDto.setVoucherNo(voucherNo);
				if(!"undefined".equals(voucherSaleDto.getVoucherNo())) {
					if(voucherMapper.insertVoucherSale(voucherSaleDto) > 0) {
						result.put("resultCd", CrevillConstants.RESULT_SUCC);
					}
				}
			}  
		}
		return result;
	}	
}