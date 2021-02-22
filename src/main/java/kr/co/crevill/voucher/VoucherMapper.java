package kr.co.crevill.voucher;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VoucherMapper {
	public int selectVoucherCount(VoucherDto voucherDto);
	public String selectVoucherNo();
	public List<VoucherVo> selectVoucherList(VoucherDto voucherDto);
	public List<VoucherVo> selectVoucherAttributeList(VoucherDto voucherDto);
	public int insertVoucher(VoucherDto voucherDto);
	public int insertVoucherAttribute(VoucherDto voucherDto);
}