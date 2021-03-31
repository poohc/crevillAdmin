function entrance(reservationId){
	var formdata = new FormData()
	formdata.append("reservationId", reservationId);
	
	axios.post('/entrance/checkVoucher.proc', formdata,{
		  headers: {
			'Content-Type': 'multipart/form-data'
		  }
		}).then((response) => {
		if (response.data.resultCd != '00') {
	      	alert('해당 고객 바우처로는 이 수업을 들을 수 없습니다(바우처 시간 부족)');
	    } else {
			$('#entranceText').text('입장');
			axios.post('/entrance/entrance.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/entrance/member.view';
				    }
				});
		}
		
	});
}