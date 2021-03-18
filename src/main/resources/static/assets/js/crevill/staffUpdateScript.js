var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      name : '성함',
			  nameEng : '성함(영문)',
   			  telNo : '연락처',		  
			  address : '주소',
			  startDate : '입사일',
			  storeId : '근무지점',
			  staffGrade : '권한구분',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	name : '',
	  	nameEng : '',
		telNo : '',	  	
		address : '',
	  	startDate : '',
	  	storeId : '',
	  	staffGrade : '',
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
				formdata.append("staffId", $('#staffId').val());
				formdata.append("workerType", $('#workerType').val());
				formdata.append("name", $('#name').val());
				formdata.append("nameEng", $('#nameEng').val());
				formdata.append("telNo", $('#telNo').val());
				formdata.append("address", $('#address').val());
				formdata.append("startDate", $('#startDate').val());
				formdata.append("storeId", $('#storeId').val());
				formdata.append("staffGrade", $('input[name="staffGrade"]:checked').val());
				
				if($("#idPicture")[0].files[0] != undefined){
					formdata.append("idPicture", $("#idPicture")[0].files[0]);	
				}
				
				if($("#healthCertificate")[0].files[0] != undefined){
					formdata.append("healthCertificate", $("#healthCertificate")[0].files[0]);	
				}
				
				if($("#resume")[0].files[0] != undefined){
					formdata.append("resume", $("#resume")[0].files[0]);	
				}
				
				axios.post('/staff/update.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/staff/list.view';
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

function list(){
	location.href = '/staff/list.view';
}