Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      title : '제목',
			  noticeType : '알림구분',
   			  noticeSendType : '전달매체',		  
			  contents : '내용',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	title : '',
	  	noticeType : '',
		noticeSendType : '',	  	
		contents : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
 			var formdata = new FormData()
			formdata.append("title", $('#title').val());
			formdata.append("noticeType", $('input[name="noticeType"]:checked').val());
			formdata.append("noticeSendType", $('select[name="noticeSendType"]').val());
			formdata.append("contents", $('#contents').val());
			
			axios.post('/branches/noticeWrite.proc', formdata,{
				  headers: {
					'Content-Type': 'multipart/form-data'
				  }
				}).then((response) => {
				if (response.data.resultCd == '00') {
			      	alert('정상처리 되었습니다.');
					location.href = '/branches/noticeWrite.view';
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
	location.href = '/branches/noticeWrite.view';
}