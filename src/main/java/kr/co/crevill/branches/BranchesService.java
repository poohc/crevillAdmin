package kr.co.crevill.branches;

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
public class BranchesService {

	@Autowired
	private BranchesMapper branchesMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<NoticeVo> selectNoticeList(NoticeDto noticeDto){
		return branchesMapper.selectNoticeList(noticeDto);
	}
	
	public List<NoticeVo> selectHeadquarterNoticeList(NoticeDto noticeDto){
		return branchesMapper.selectHeadquarterNoticeList(noticeDto);
	}
	
	public NoticeVo selectNoticeInfo(NoticeDto noticeDto) {
		return branchesMapper.selectNoticeInfo(noticeDto);
	}
	
	/**
	 * 공지 INSERT 처리 
	 * @methodName : insertNotice
	 * @author : Juyoung Park
	 * @date : 2021.02.20
	 * @param noticeDto
	 * @return
	 */
	public JSONObject insertNotice(NoticeDto noticeDto, HttpServletRequest request) {
		NoticeVo noticeVo = branchesMapper.selectNoticeId();
		noticeDto.setNoticeId(noticeVo.getNoticeId());
		noticeDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		//공지사항 INSERT 성공 시 전달매체 테이블 INSERT 
		if(branchesMapper.insertNotice(noticeDto) > 0) {
			//선택된 전달매체 모두 INSERT 성공해야 SUCC
			if(noticeDto.getNoticeSendType() != null) {
				int cnt = noticeDto.getNoticeSendType().split(",").length;
				int insCnt = 0;
				for(String noticeType : noticeDto.getNoticeSendType().split(",")) {
					noticeDto.setNoticeSendType(noticeType);
					if(branchesMapper.insertNoticeSendType(noticeDto) > 0) {
						insCnt++;
					}	
				}
				if(cnt == insCnt) {
					result.put("resultCd", CrevillConstants.RESULT_SUCC);	
				}
			}
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	public JSONObject deleteNotice(NoticeDto noticeDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		if(branchesMapper.deleteNotice(noticeDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	public JSONObject updateNotice(NoticeDto noticeDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		noticeDto.setUpdId(SessionUtil.getSessionStaffVo(request).getStaffId());
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		if(branchesMapper.updateNotice(noticeDto) > 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
}