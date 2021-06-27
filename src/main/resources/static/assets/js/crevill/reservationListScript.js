$('#searchBtn').click(function() {
	location.href = '/reservation/list.view?storeId=' + $('#storeId').val() + '&scheduleStart=' + $('input[name="scheduleStart"]').val();   
});

$('input[name="scheduleStart"]').daterangepicker({
	singleDatePicker : true,
	locale: {
      format: 'YYYYMMDD',
      separator: '',
      applyLabel: "적용",
      cancelLabel: "닫기"
    } 	
});

function cancel(reservationId, voucherUseId){
	
	if(confirm('예약을 취소처리 하시겠습니까?')){
		var formdata = new FormData();
		formdata.append("reservationId", reservationId);
		formdata.append("voucherUseId", voucherUseId);
		
		axios.post('/reservation/cancel.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
			}).then((response) => {
			if (response.data.resultCd == '00') {
		      	alert('정상처리 되었습니다.');
				location.href = '/reservation/list.view';
		    }
			
		}).catch(function (error) {
		    if (error.response) {
		      alert('처리 중 오류가 발생했습니다. 관리자에게 문의하여 주세요.');
		    }
		    else if (error.request) {
		      alert('처리 중 오류가 발생했습니다. 관리자에게 문의하여 주세요.');
		    }
		    else {
		      alert('처리 중 오류가 발생했습니다. 관리자에게 문의하여 주세요.');
		    }
	    });	
	}	
}

$('#storeId').change(function(){
	location.href = '/reservation/list.view?storeId=' + $('#storeId').val() + '&scheduleStart=' + $('input[name="scheduleStart"]').val();
});