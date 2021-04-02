function deleteVoucherUseInfo(reservationId, cellPhone, voucherNo, voucherUseId){
	if(confirm('바우처 사용을 취소하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            reservationId : reservationId,
					voucherUseId : voucherUseId
		    },
			url : '/reservation/cancel.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('바우처 사용이 취소 되었습니다.');
					location.href = '/member/voucherUseList.view?reservationId='+reservationId+'&cellPhone='+cellPhone+'&voucherNo='+voucherNo;
				} else {
					alert('바우처 사용 취소 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert("바우처 사용 취소 중 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
		});	
	}
}

$('#voucherNo').change(function(){
	location.href = '/member/voucherUseList.view?voucherNo=' + $('#voucherNo').val() + '&cellPhone='+$('#cellPhone').val();
});