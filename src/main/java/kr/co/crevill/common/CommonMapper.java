package kr.co.crevill.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommonMapper {
	public List<CommonCodeVo> selectCommonCode(CommonCodeDto commonCodeDto);
	public List<CommonCodeVo> selectNationalCode();
	public FileVo selectImageInfo(FileDto fileDto);
	public FileVo selectImagesIdx();
	public FileVo selectFileIdx();
	public int insertImages(FileDto fileDto);
	public int insertFiles(FileDto fileDto);
	public CommonVo selectStatInfo(CommonDto commonDto);
	public List<CommonVo> selectVoucherStatInfo();
	public List<CommonVo> selectTodayReservationInfo(CommonDto commonDto);
	public CommonVo selectVoucherStatistics(CommonDto commonDto);
	public CommonVo selectNoShowCount(CommonDto commonDto);
}