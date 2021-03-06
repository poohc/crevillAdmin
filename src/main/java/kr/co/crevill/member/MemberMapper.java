package kr.co.crevill.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper {
	public int checkExistCellPhone(MemberDto memberDto);
	public int selectMemberCount(MemberDto memberDto);
	public List<MemberVo> selectMemberList(MemberDto memberDto);
	public MemberVo selectMemberInfo(MemberDto memberDto);
	public MemberVo getMemberInfo(MemberDto memberDto);
	public List<MemberVo> selectUpdateChildList(MemberDto memberDto);
	public List<MemberVo> selectChildList(MemberDto memberDto);
	public int insertMemberParent(MemberDto memberDto);
	public int insertMemberChildren(MemberDto memberDto);
	public int insertMemberChildrenGrade(MemberDto memberDto);
	public int updateMemberParent(MemberDto memberDto);
	public int deleteMemberParent(MemberDto memberDto);
	public int deleteMemberChildren(MemberDto memberDto);
	public int deleteMemberChildrenGrade(MemberDto memberDto);
	public List<MemberVo> selectMemberVoucherInfo(MemberDto memberDto);
	public MemberVo selectMemberCountInfo(MemberDto memberDto);
}
