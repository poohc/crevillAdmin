function voucherDelete(voucherNo){
	if(confirm('해당 바우처를 삭제 하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            voucherNo : voucherNo
		    },
			url : '/voucher/delete.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('바우처가 삭제되었습니다.');
					location.href = '/voucher/list.view';
				} else {
					alert('바우처 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert('바우처 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
				return false;
		    }
		});	
	}
}

function voucherUpdate(voucherNo){
	var url = '/voucher/update.view?voucherNo=' + voucherNo;
	location.href = url;
}