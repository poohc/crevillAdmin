var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      operationType : '운영구분',
			  name : '플레이 이름',
   			  playTime : '이용시간',		  
			  description : '플레이 설명',
			  thumbnail : '썸네일',
			  picture : '놀이사진',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	operationType : '',
	  	name : '',
		playTime : '',	  	
		description : '',
		thumbnail : '',
		picture : '',
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
				formdata.append("name", $('#name').val());
				formdata.append("playTime", $('select[name="playTime"]').val());
				formdata.append("description", $('#description').val());
				
				if($("#thumbnail")[0].files[0] != undefined){
					formdata.append("thumbnail", $("#thumbnail")[0].files[0]);	
				}
				
				if($("#picture")[0].files[0] != undefined){
					formdata.append("picture", $("#picture")[0].files[0]);	
				}
				
				axios.post('/play/master.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/play/master.view';
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
	location.href = '/play/master.view';
}