var acceessableCount = 1; //동시접근제한수

const checkInstructorTelNo = (value) => {
  	return axios.post('/staff/checkInstructorTelNo.proc', {telNo: value}).then((response) => {
		if (response.data.resultCd == '00') {
	      return true;
	    }
		
		return {
	      data: {
	        message: '이미 가입되어 있는 번호입니다.'
	      }
	    };
	});
};

VeeValidate.Validator.extend('chkTelNo', {
    validate : checkInstructorTelNo,
    getMessage: (field, params, data) => {
    	return data.message;
  	}
});

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      nationality : '국적',
			  name : '성함',
			  fullName : '풀네임',
   			  telNo : '연락처',		  
			  roadAddress : '도로명주소',
			  detailAddress : '상세주소',
			  startDate : '입사일',
			  storeId : '근무지점',
		    }
	  	}
  }
})

new Vue({
    el: '#page-body',
    data: {
    	nationality : '',
	  	name : '',
		fullName : '',	
		telNo : '',	  	
		roadAddress : '',
		detailAddress : '',
	  	startDate : '',
	  	storeId : '',	  
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
			acceessableCount  = acceessableCount - 1; //count부터 뺀다	        
			
			if (acceessableCount < 0 ) {
		    	alert("이미 작업이 수행중입니다.");
		    } else {
				var formdata = new FormData();
				formdata.append("nationality", $('#nationality').val());
				formdata.append("name", $('#name').val());
				formdata.append("fullName", $('#fullName').val());
				formdata.append("telNo", $('#telNo').val());
				formdata.append("address", $('#roadAddress').val() + ' | ' + $('#detailAddress').val());
				formdata.append("startDate", $('#startDate').val());
				formdata.append("storeId", $('#storeId').val());
				
				if($("#picture")[0].files[0] != undefined){
					formdata.append("picture", $("#picture")[0].files[0]);	
				}
				
				if($("#criminalRecords")[0].files[0] != undefined){
					formdata.append("criminalRecords", $("#criminalRecords")[0].files[0]);	
				}
				
				if($("#resume")[0].files[0] != undefined){
					formdata.append("resume", $("#resume")[0].files[0]);	
				}
				
				axios.post('/staff/nsRegist.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/staff/nsRegist.view';
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

$('input[name="startDate"]').daterangepicker({
	singleDatePicker : true,
	locale: {
      format: 'YYYYMMDD',
      separator: '',
      applyLabel: "적용",
      cancelLabel: "닫기"
    } 	
});

function cancel(){
	location.href = '/staff/nsRegist.view';
}