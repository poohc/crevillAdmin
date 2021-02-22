package kr.co.crevill.branches;

import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CrevillConstants;

@Service
public class BranchesService {

	@Autowired
	private BranchesMapper branchesMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<NoticeVo> selectNoticeList(NoticeDto noticeDto){
		return branchesMapper.selectNoticeList(noticeDto);
	}
	
	/**
	 * 공지 INSERT 처리 
	 * @methodName : insertNotice
	 * @author : Juyoung Park
	 * @date : 2021.02.20
	 * @param noticeDto
	 * @return
	 */
	public JSONObject insertNotice(NoticeDto noticeDto) {
		//TODO 로그인 처리 이후 삭제할것
		NoticeVo noticeVo = branchesMapper.selectNoticeId();
		noticeDto.setNoticeId(noticeVo.getNoticeId());
		noticeDto.setRegId("devjyp");
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		//공지사항 INSERT 성공 시 전달매체 테이블 INSERT 
		if(branchesMapper.insertNotice(noticeDto) > 0) {
			//선택된 전달매체 모두 INSERT 성공해야 SUCC
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
		return result;
	}
	
}