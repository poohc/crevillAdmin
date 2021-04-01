var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      childName : '이름',
			  childBirthday : '생년월일',
   			  childSex : '성별',		  
			  cellPhone : '휴대폰번호',
			  scheduleId : '스케쥴',
			  playName : '클래스',
			  voucherNo : '바우처',
	          price : '바우처가격'
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    		childName : '',
			childBirthday : '',
   			childSex : '',		  
			cellPhone : '',
			scheduleId : '',
			playName : '',
			voucherNo : '',
			price : ''
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
				
				formdata.append("childName", $('#childName').val());
				formdata.append("childBirthday", $('#childBirthday').val().replace(/[^0-9]/g,""));
				formdata.append("childSex", $('#childSex').val());
				formdata.append("cellPhone", $('#cellPhone').val());
				formdata.append("scheduleId", $('#scheduleId').val());
				formdata.append("voucherNo", $('#voucherNo').val());
				formdata.append("playTime", $('#playTime').val());
				
				axios.post('/entrance/nonMemberEntrance.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/entrance/nonMember.view';
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

$('#enterBtn').click(function(){
	$('#nonMemberForm').submit();
});

$('#voucherNo').change(function(){
	$('#price').val($(this).val()).prop("selected", true);
});