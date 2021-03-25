var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      operationType : '운영구분',
			  numberOfPeople : '이용인원',
   			  tutoringNumber : '튜터링인원',		  
			  scheduleDate : '시작날짜선택',
			  subTopic : '소주제'
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	operationType : '',
	  	numberOfPeople : '',
		tutoringNumber : '',	  	
		scheduleStart : '',
		subTopic : ''
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
			acceessableCount  = acceessableCount - 1; //count부터 뺀다
			
			if (acceessableCount < 0 ) {
		    	alert("이미 작업이 수행중입니다.");
		    } else {
				var formdata = new FormData()
				var operType = '';
				$("input[name=operationType]:checked").each(function() {
					operType += $(this).val() + ',';
				});
				operType = operType.substr(0, operType.length - 1);
				
				formdata.append("operationType", operType);
				formdata.append("numberOfPeople", $('#numberOfPeople').val());
				formdata.append("playId", $('select[name="playId"]').val());
				formdata.append("tutoringNumber", $('#tutoringNumber').val());
				formdata.append("scheduleStart", $('#scheduleStart').val().replace(/[^0-9]/g,""));
				formdata.append("subTopic", $('#subTopic').val());
				formdata.append("storeId", $('#storeId').val());
				
				axios.post('/schedule/regist.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/schedule/regist.view';
				    }
					
				});
			}

 			acceessableCount = acceessableCount + 1;	
			
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}); 

function cancel(){
	location.href = '/schedule/regist.view';
}


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