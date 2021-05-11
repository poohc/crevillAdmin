var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      promotionName : '프로모션명',
			  promotionContents : '프로모션내용',
   			  endDate : '만료일',		
			  promotionType : '프로모션구분',	  
			  promotionValue : '프로모션값',
			  storeId : '매장'
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	promotionName : '',
	    promotionContents : '',
	    endDate : '',		
	    promotionType : '',	  
	    promotionValue : '',
		storeId : ''
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
				var storeId = '';
				$("input[name=storeId]:checked").each(function() {
					storeId += $(this).val() + ',';
				});
				storeId = storeId.substr(0, storeId.length - 1);
				
				formdata.append("storeId", storeId);
				formdata.append("promotionName", $('#promotionName').val());
				formdata.append("promotionContents", $('#promotionContents').val());
				formdata.append("endDate", $('#endDate').val().replace(/[^0-9]/g,""));
				formdata.append("promotionType", $('input[name="promotionType"]:checked').val());
				formdata.append("promotionValue", $('#promotionValue').val());
				
				if($("#promotionBanner")[0].files[0] != undefined){
					formdata.append("promotionBanner", $("#promotionBanner")[0].files[0]);	
				}
				
				if($("#promotionDetailImage")[0].files[0] != undefined){
					formdata.append("promotionDetailImage", $("#promotionDetailImage")[0].files[0]);	
				}
				
				axios.post('/promotion/regist.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/promotion/list.view';
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

$('input[name="endDay"]').daterangepicker({
	singleDatePicker : true,
	locale: {
      format: 'YYYYMMDD',
      separator: '',
      applyLabel: "적용",
      cancelLabel: "닫기"
    } 	
});

function cancel(){
	location.href = '/promotion/regist.view';
}