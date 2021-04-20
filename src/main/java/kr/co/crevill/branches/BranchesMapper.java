package kr.co.crevill.branches;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BranchesMapper {
	public NoticeVo selectNoticeId();
	public List<NoticeVo> selectNoticeList(NoticeDto noticeDto);
	public List<NoticeVo> selectHeadquarterNoticeList(NoticeDto noticeDto);
	public NoticeVo selectNoticeInfo(NoticeDto noticeDto);
	public int insertNotice(NoticeDto noticeDto);
	public int insertNoticeSendType(NoticeDto noticeDto);
	public int deleteNotice(NoticeDto noticeDto);
	public int updateNotice(NoticeDto noticeDto);
}
