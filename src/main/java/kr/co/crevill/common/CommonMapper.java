package kr.co.crevill.common;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommonMapper {
	public List<CommonCodeVo> selectCommonCode(CommonCodeDto commonCodeDto);
}
