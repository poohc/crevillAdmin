Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      grade : '등급',
			  ticketName : '티켓명',
   			  price : '정가',		  
			  salePrice : '판매금액',
			  useTime : '이용시간',
			  endDate : '유효기간',
			  saleCode : '할인코드',
			  attribute : '티켓속성',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	grade : '',
	  	ticketName : '',
		price : '',	  	
		salePrice : '',
	  	useTime : '',
	  	endDate : '',
	  	saleCode : '',
		attribute : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
 			var formdata = new FormData();
			formdata.append("grade", $('#grade').val());
			formdata.append("ticketName", $('#ticketName').val());
			formdata.append("price", $('#price').val());
			formdata.append("salePrice", $('#salePrice').val());
			formdata.append("useTime", $('#useTime').val());
			formdata.append("endDate", $('#endDate').val());
			formdata.append("saleCode", $('#saleCode').val());
			var attribute = '';
			$("input[name=attribute]:checked").each(function() {
				attribute += $(this).val() + ',';
			});
			attribute = attribute.substr(0, attribute.length - 1);
			formdata.append("attribute", attribute);
			
			if($("#image")[0].files[0] != undefined){
				formdata.append("image", $("#image")[0].files[0]);	
			}
			
			axios.post('/voucher/create.proc', formdata,{
				  headers: {
					'Content-Type': 'multipart/form-data'
				  }
				}).then((response) => {
				if (response.data.resultCd == '00') {
			      	alert('정상처리 되었습니다.');
					location.href = '/voucher/create.view';
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
	location.href = '/voucher/create.view';
}