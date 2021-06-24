var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      endDate : '만료일',
			  memo : '취소사유',
		    }
	  	}
  }
});

var vm = new Vue({
    el: '#page-body',
    data: {
    	name : '',
	  	endDate : '',
		voucherNo : '',
		ticketName : '',
		timeLeftHour : '',
		salePrice : '',
		promotionName : '',
		cancelVoucherNo : '',
		useTime : '',
		calcTimeLeft : '',
    },
	methods: {
    validateForm(scope) {
      this.$validator.validateAll(scope).then((result) => {
        if (result) {
	        
			acceessableCount  = acceessableCount - 1; //count부터 뺀다
			
			if (acceessableCount < 0 ) {
		    	alert("이미 작업이 수행중입니다.");
		    } else {
			
				if(scope == 'modify'){
					var formdata = new FormData();
					formdata.append("voucherNo", $('#voucherNo').val());
					formdata.append("useTime", $('#useTime').val());
					
					axios.post('/voucher/voucherTimeUpdate.proc', formdata,{
						  headers: {
							'Content-Type': 'multipart/form-data'
						  }
						}).then((response) => {
						if (response.data.resultCd == '00') {
					      	alert('정상처리 되었습니다.');
							location.href = '/voucher/saleList.view';
					    }
					});		
				}
			
				if(scope == 'extension'){
					var formdata = new FormData();
					formdata.append("voucherNo", $('#voucherNo').val());
					formdata.append("endDate", $('#endDate').val());
					
					axios.post('/voucher/voucherValidUpdate.proc', formdata,{
						  headers: {
							'Content-Type': 'multipart/form-data'
						  }
						}).then((response) => {
						if (response.data.resultCd == '00') {
					      	alert('정상처리 되었습니다.');
							location.href = '/voucher/saleList.view';
					    }
					});		
				}
			
				if(scope == 'cancel'){
					var formdata = new FormData();
					formdata.append("voucherNo", $('#cancelVoucherNo').val());
					formdata.append("memo", $('#memo').val());
					
					axios.post('/voucher/voucherCancel.proc', formdata,{
						  headers: {
							'Content-Type': 'multipart/form-data'
						  }
						}).then((response) => {
						if (response.data.resultCd == '00') {
					      	alert('정상처리 되었습니다.');
							location.href = '/voucher/saleList.view';
					    }
					});	
				}
			}
			
			acceessableCount = acceessableCount + 1;
 			
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}); 


$('#storeId').change(function(){
	location.href = '/voucher/saleList.view?storeId=' + $('#storeId').val();
});

function setVoucherTimeModify(voucherNo, name, ticketName, timeLeftHour, useTime){
	vm.voucherNo = voucherNo;
	vm.name = name;
	vm.ticketName    = ticketName;
	vm.timeLeftHour  = timeLeftHour;
	vm.useTime = useTime;
}

function setVoucherExtension(voucherNo, name, endDate){
	vm.voucherNo = voucherNo;
	vm.name = name;
	vm.endDate = endDate;
}

function setVoucherCancel(voucherNo, name, endDate, ticketName, timeLeftHour, salePrice, promotionName){
	vm.cancelVoucherNo = voucherNo;
	vm.name          = name;
	vm.endDate       = endDate;
	vm.ticketName    = ticketName;
	vm.timeLeftHour  = timeLeftHour;
	vm.salePrice     = salePrice;
	vm.promotionName = promotionName;
}