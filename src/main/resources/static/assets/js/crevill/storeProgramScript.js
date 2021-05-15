var acceessableCount = 1; //동시접근제한수


Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      chainClass : '연관클래스',
			  programName : '프로그램명',
   			  programContents : '프로그램내용'
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	chainClass : '',
	    programName : '',
	    programContents : ''
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
				
				formdata.append("chainClass", $('#chainClass').val());
				formdata.append("programName", $('#programName').val());
				formdata.append("programContents", $('#programContents').val());
				
				if($("#thumbnail")[0].files[0] != undefined){
					formdata.append("thumbnail", $("#thumbnail")[0].files[0]);	
				}
				
				if($("#picture")[0].files[0] != undefined){
					formdata.append("picture", $("#picture")[0].files[0]);	
				}
				
				if($("#teachingPlanImg")[0].files[0] != undefined){
					formdata.append("teachingPlanImg", $("#teachingPlanImg")[0].files[0]);	
				}
				
				axios.post('/storeProgram/regist.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/storeProgram/list.view';
				    } else {
						alert('처리중 오류가 발생했습니다. 다시 시도하여 주세요.');
						location.href = '/storeProgram/regist.view';
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
	location.href = '/storeProgram/regist.view';
}