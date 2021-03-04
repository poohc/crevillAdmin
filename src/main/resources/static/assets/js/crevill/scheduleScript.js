Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      operationType : '운영구분',
			  numberOfPeople : '이용인원',
   			  tutoringNumber : '튜터링인원',		  
			  scheduleDate : '날짜선택',
			  scheduleTime : '시작시간',
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
		scheduleDate : '',
		scheduleTime : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
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
			formdata.append("scheduleDate", $('#scheduleDate').val());
			formdata.append("scheduleTime", $('select[name="scheduleTime"]').val());
			
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

$('input[name="operationType"]').change(function(){
	$("select[name='playId'] option").remove();
	
	if($('input[name="operationType"]:checked').length > 0){
		$.ajax({
			type : "POST",
			data: {
		            operationType : $('input[name="operationType"]:checked').val()
		    },
			url : '/play/playList.view',
			success : function(data){
				for(var i=0; i < data.length; i++){
					$("#playId").append('<option value="' + data[i].playId + '">' + data[i].name + '</option');
				}
			}
		});	
	} 
	
});