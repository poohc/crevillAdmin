package kr.co.crevill.member;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.crevill.common.CrevillConstants;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<MemberVo> selectMemberList(MemberDto memberDto) {
		List<MemberVo> memberList = new ArrayList<>();
		int memberCount = memberMapper.selectMemberCount(memberDto);
		if (memberCount > 0) {
			memberList = memberMapper.selectMemberList(memberDto);
		}
		return memberList;
	}
	
	public JSONObject checkMemberCellPhone(MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		//TODO 등록자 ID 수정해 줄것
		memberDto.setRegId("devjyp");
		if(memberMapper.checkExistCellPhone(memberDto) == 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	public JSONObject insertMemberInfo(MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		//부모 정보 INSERT 성공 시 자녀정보 INSERT
		if(memberMapper.insertMemberParent(memberDto) > 0) {
			//자녀정보 INSERT 성공 시 resultCd = SUCC
			if(memberMapper.insertMemberChildren(memberDto) > 0) {
				result.put("resultCd", CrevillConstants.RESULT_SUCC);
			}
		}
		return result;
	}
	
	public MemberVo getMemberInfo(MemberDto memberDto){
		return memberMapper.selectMemberInfo(memberDto);
	}
	
}