var acceessableCount = 1; //동시접근제한수

const checkMemberCellPhone = (value) => {
  	return axios.post('/member/checkMemberCellPhone.proc', {parentCellPhone: value, cellPhone : $('#originCellPhone').val()}).then((response) => {
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

VeeValidate.Validator.extend('chkCellPhone', {
    validate : checkMemberCellPhone,
    getMessage: (field, params, data) => {
    	return data.message;
  	}
});

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      parentName : '고객성함',
			  parentBirthday : '고객생년월일',
		      parentSex : '고객성별',
			  email : '이메일',
			  address : '주소',
			  roadAddress : '도로명주소',
			  detailAddress : '상세주소',
			  cellPhone : '전화번호',
			  childName : '아동이름',
			  birthday : '생년월일',
			  sex : '성별',
			  learningGrade : '영어학습수준'
		    }
	  	}
  }
})

new Vue({
    el: '#page-body',
    data: {
    	parentName : '',
		parentBirthday : '',
		parentSex : '',
	  	email : '',
	  	address : '',
		roadAddress : '',
		detailAddress : '',
	  	cellPhone : '',
	  	childName : '',
	  	birthday : '',
	  	sex : '',
	  	learningGrade : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
			acceessableCount  = acceessableCount - 1; //count부터 뺀다
			
			if (acceessableCount < 0 ) {
		    	alert("이미 작업이 수행중입니다.");
		    } else {
				var learningGrade = '';
				$("input[name=learningGrade]:checked").each(function() {
					learningGrade += $(this).val() + ',';
				});
				learningGrade = learningGrade.substr(0, learningGrade.length - 1);
			
				axios.post('/member/update.proc', {
									            parentName : $('#parentName').val(),
												parentBirthday : $('#parentBirthday').val(),	
												parentSex : $('input[name="parentSex"]:checked').val(),
									            email : $('#email').val(),
									            address : $('#roadAddress').val() + ' | ' + $('#detailAddress').val(),
									            cellPhone : $('#cellPhone').val(),
									            childName : $('#childName').val(),
									            birthday : $('#birthday').val(),
									            sex : $('input[name="sex"]:checked').val(),
									            learningGrade : learningGrade,
												qrCode : $('#qrCode').val()
			        }).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/member/list.view';
				    } else {
						alert('업데이트 중 오류가 발생했습니다. 다시 시도하여 주세요.');
						return false;
					}
					
					return {
				      data: {
				        message: '이미 가입되어 있는 번호입니다.'
				      }
				    };
				});
			}
			
			acceessableCount = acceessableCount + 1;
				
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
})


$('input[name="birthday"]').daterangepicker({
	singleDatePicker : true,
	locale: {
      format: 'YYYYMMDD',
      separator: '',
      applyLabel: "적용",
      cancelLabel: "닫기"
    } 	
}); 