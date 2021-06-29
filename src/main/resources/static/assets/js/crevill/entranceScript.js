var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      parentCellPhone : '보호자연락처',
			  childName : '아동명',
   			  birthday : '아동생년월일',		  
			  entranceTime : '이용시간',
			  price : '이용금액'
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	
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
				formdata.append("cellPhone", $('#parentCellPhone').val());
				formdata.append("childName", $('#childName').val());
				formdata.append("childBirthday", $('#birthday').val().replace(/[^0-9]/g,""));
				formdata.append("entranceTime", $('#entranceTime').val());
				formdata.append("price", $('#price').val());
				formdata.append("storeId", $('#storeId').val());
				formdata.append("scheduleType", '클래스');
				
				axios.post('/entrance/nonMemberEntrance.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/entrance/classMember.view';
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

function entrance(reservationId){
	var formdata = new FormData()
		formdata.append("reservationId", reservationId);
		formdata.append("storeId", $('#storeId').val());
	
	$('#entranceText').text('입장');
	axios.post('/entrance/entrance.proc', formdata,{
				  headers: {
					'Content-Type': 'multipart/form-data'
				  }
				}).then((response) => {
				if (response.data.resultCd == '00') {
			      	alert('정상처리 되었습니다.');
					location.href = '/entrance/classMember.view';
			    }
			});
}

$('#storeId').change(function(){
	location.href = "/entrance/classMember.view?storeId=" + $('#storeId').val();
});