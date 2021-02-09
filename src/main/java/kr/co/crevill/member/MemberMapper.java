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
	public int insertMemberParent(MemberDto memberDto);
	public int insertMemberChildren(MemberDto memberDto);
}
