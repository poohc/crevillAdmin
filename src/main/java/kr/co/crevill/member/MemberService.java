package kr.co.crevill.member;

import java.util.ArrayList;
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
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public int selectMemberCount(MemberDto memberDto) {
		return memberMapper.selectMemberCount(memberDto);
	}
	
	public List<MemberVo> selectUpdateChildList(MemberDto memberDto){
		return memberMapper.selectUpdateChildList(memberDto);
	}
	
	public List<MemberVo> selectChildList(MemberDto memberDto){
		return memberMapper.selectChildList(memberDto);
	}

	public MemberVo selectMemberCountInfo(MemberDto memberDto) {
		return memberMapper.selectMemberCountInfo(memberDto);
	}
	
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
		if(memberMapper.checkExistCellPhone(memberDto) == 0) {
			result.put("resultCd", CrevillConstants.RESULT_SUCC);
		}
		return result;
	}
	
	public JSONObject insertMemberInfo(MemberDto memberDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		memberDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
		memberDto.setStoreId(SessionUtil.getSessionStaffVo(request).getStoreId());
		//부모 정보 INSERT 성공 시 자녀정보 INSERT
		if(memberMapper.insertMemberParent(memberDto) > 0) {
			//자녀정보 INSERT 성공 시 resultCd = SUCC
			if(memberMapper.insertMemberChildren(memberDto) > 0) {
				//자녀 영어수준 INSERT 추가
				int learningGradeCount = memberDto.getLearningGrade().split(",").length;
				int tmpCnt = 0;
				
				if(learningGradeCount > 0) {
					for(String learningGrade : memberDto.getLearningGrade().split(",")) {
						memberDto.setLearningGrade(learningGrade);
						if(memberMapper.insertMemberChildrenGrade(memberDto) > 0) {
							tmpCnt++;
						}
					}
					if(learningGradeCount == tmpCnt) {
						result.put("resultCd", CrevillConstants.RESULT_SUCC);
					}
				} else {
					if(memberMapper.insertMemberChildrenGrade(memberDto) > 0) {
						result.put("resultCd", CrevillConstants.RESULT_SUCC);
					}
				}
			}
		}
		return result;
	}
	
	public JSONObject updateMemberInfo(MemberDto memberDto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		memberDto.setUpdId(SessionUtil.getSessionStaffVo(request).getStaffId());
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		//부모 정보 INSERT 성공 시 자녀정보 INSERT
		if(memberMapper.updateMemberParent(memberDto) > 0) {
			//자녀 정보는 DELETE 후 INSERT 로 처리
			if(memberMapper.deleteMemberChildren(memberDto) > 0) {
				
				if(memberMapper.deleteMemberChildrenGrade(memberDto) > 0) {
					for(int i=0; i < memberDto.getChildName().split(",").length; i++) {
						MemberDto nMemberDto = new MemberDto();
						nMemberDto.setParentName(memberDto.getParentName());
						nMemberDto.setCellPhone(memberDto.getCellPhone());
						nMemberDto.setChildName(memberDto.getChildName().split(",")[i]);
						nMemberDto.setEngName(memberDto.getEngName().split(",")[i]);
						nMemberDto.setBirthday(memberDto.getBirthday().split(",")[i]);
						if(memberDto.getLearningGrade().split(";").length > i) {
							nMemberDto.setLearningGrade(memberDto.getLearningGrade().split(";")[i]);	
						}
						nMemberDto.setSex(memberDto.getSex().split(",")[i]);
						nMemberDto.setRegId(SessionUtil.getSessionStaffVo(request).getStaffId());
						logger.info("nMemberDto : " + nMemberDto.toString());
						
						if(memberMapper.insertMemberChildren(nMemberDto) > 0) {
							if(nMemberDto.getLearningGrade() != null) {
								int learningGradeCount = nMemberDto.getLearningGrade().split(",").length;
								int tmpCnt = 0;
								
								if(learningGradeCount > 0) {
									for(String learningGrade : nMemberDto.getLearningGrade().split(",")) {
										nMemberDto.setLearningGrade(learningGrade);
										if(memberMapper.insertMemberChildrenGrade(nMemberDto) > 0) {
											tmpCnt++;
										}
									}
									if(learningGradeCount == tmpCnt) {
										result.put("resultCd", CrevillConstants.RESULT_SUCC);
									}
								} else {
									if(memberMapper.insertMemberChildrenGrade(nMemberDto) > 0) {
										result.put("resultCd", CrevillConstants.RESULT_SUCC);
									}
								}
							} else {
								result.put("resultCd", CrevillConstants.RESULT_SUCC);
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	public JSONObject deleteMemberInfo(MemberDto memberDto) {
		JSONObject result = new JSONObject();
		result.put("resultCd", CrevillConstants.RESULT_FAIL);
		//2021.06.27 회원탈퇴 프로세스 변경, AS-IS : 모두삭제, TO-BE : 부모회원 상태변경, 나머지 삭제
		if(memberMapper.deleteMemberChildren(memberDto) > 0) {
			if(memberMapper.deleteMemberChildrenGrade(memberDto) >= 0) {
				memberDto.setStatus(CrevillConstants.MEMBER_STATUS_INACTIVE);
				if(memberMapper.updateMemberParent(memberDto) > 0) {
					result.put("resultCd", CrevillConstants.RESULT_SUCC);
				}
			}
		}
		return result;
	}
	
	public MemberVo getMemberInfo(MemberDto memberDto){
		return memberMapper.selectMemberInfo(memberDto);
	}
	
	public List<MemberVo> selectMemberVoucherInfo(MemberDto memberDto){
		return memberMapper.selectMemberVoucherInfo(memberDto);
	}
}