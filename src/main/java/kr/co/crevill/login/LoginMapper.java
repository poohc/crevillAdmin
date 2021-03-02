package kr.co.crevill.login;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginMapper {
	public int selectLoginStaff(LoginDto loginDto);
}