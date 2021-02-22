package kr.co.crevill.branches;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BranchesMapper {
	public NoticeVo selectNoticeId();
	public List<NoticeVo> selectNoticeList(NoticeDto noticeDto);
	public int insertNotice(NoticeDto noticeDto);
	public int insertNoticeSendType(NoticeDto noticeDto);
}
