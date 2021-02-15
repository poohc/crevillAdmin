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
			  officeId : '근무지점',
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
	  	officeId : '',
	  	staffGrade : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
 			var formdata = new FormData()
			formdata.append("workerType", $('#workerType').val());
			formdata.append("name", $('#name').val());
			formdata.append("nameEng", $('#nameEng').val());
			formdata.append("telNo", $('#telNo').val());
			formdata.append("address", $('#address').val());
			formdata.append("startDate", $('#startDate').val());
			formdata.append("officeId", $('#officeId').val());
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
			
			axios.post('/staff/join.proc', formdata,{
				  headers: {
					'Content-Type': 'multipart/form-data'
				  }
				}).then((response) => {
				if (response.data.resultCd == '00') {
			      	alert('정상처리 되었습니다.');
					$('#staffForm').attr('action','/staff/join.view');
					$('#staffForm').submit();
			    }
				
			});	
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}); 