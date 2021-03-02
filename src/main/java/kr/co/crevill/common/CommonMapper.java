package kr.co.crevill.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.crevill.store.StoreVo;

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
}