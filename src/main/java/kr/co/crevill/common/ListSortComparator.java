package kr.co.crevill.common;

import java.util.Comparator;

import kr.co.crevill.voucher.VoucherVo;

public class ListSortComparator implements Comparator {
	@Override
	public int compare(Object o1, Object o2) {
		int testint1 = ((VoucherVo) o1).getSortOrder();
		int testint2 = ((VoucherVo) o2).getSortOrder();

		if (testint1 > testint2) {
			return 1;
		} else if (testint1 < testint2) {
			return -1;
		} else {
			return 0;
		}
	}
}