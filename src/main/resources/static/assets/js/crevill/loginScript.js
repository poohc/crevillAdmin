Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      staffId : '사원번호',
			  passwd : '비밀번호',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	staffId : '',
	  	passwd : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
 			var formdata = new FormData();
			formdata.append("staffId", $('#staffId').val());
			formdata.append("storeId", $('#passwd').val());
			
			axios.post('/login/login.proc', formdata,{
				  headers: {
					'Content-Type': 'multipart/form-data'
				  }
				}).then((response) => {
				if (response.data.resultCd == '00') {
			      	location.href = '/';
			    } else {
					alert('ID 혹은 비밀번호를 확인해주세요.');
					return false;
				}
				
			});	
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}); 

function cancel(){
	location.href = '/staff/regist.view';
}