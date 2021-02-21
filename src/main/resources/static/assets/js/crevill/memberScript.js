const checkMemberCellPhone = (value) => {
  	return axios.post('/member/checkMemberCellPhone.proc', {parentCellPhone: value}).then((response) => {
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
			  email : '이메일',
			  address : '주소',
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
	  	email : '',
	  	address : '',
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
	        
			axios.post('/member/join.proc', {
								            parentName : $('#parentName').val(),
								            email : $('#email').val(),
								            address : $('#address').val(),
								            cellPhone : $('#cellPhone').val(),
								            childName : $('#childName').val(),
								            birthday : $('#birthday').val(),
								            sex : $('input[name="sex"]:checked').val(),
								            learningGrade : $('input[name="learningGrade"]:checked').val()
		        }).then((response) => {
				if (response.data.resultCd == '00') {
			      	alert('정상처리 되었습니다.');
					location.href = '/member/join.view';
			    }
				
				return {
			      data: {
			        message: '이미 가입되어 있는 번호입니다.'
			      }
			    };
			});	
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}) 