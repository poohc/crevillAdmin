package kr.co.crevill.reservation;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReservationMapper {
	public int selectReservationCount(ReservationDto reservationDto);
	public List<ReservationVo> selectReservationList(ReservationDto reservationDto);
	public int insertReservation(ReservationDto reservationDto);
}