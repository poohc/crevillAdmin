var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      fbRevenue : 'F&B매출',
			  voucherRefundPrice : '바우처환불액',
   			  shortTicketCase1Count : '1회권1시간 수량',		  
			  shortTicketCase1Price : '1회권1시간 단가',
			  shortTicketCase1Result : '1회권1시간 계산결과',
			  shortTicketCase2Count : '1회권2시간 수량',		  
			  shortTicketCase2Price : '1회권2시간 단가',
			  shortTicketCase2Result : '1회권2시간 계산결과',
			  memo : '일지작성',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
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
				
				formdata.append("fbRevenue", $('#fbRevenue').val());
				formdata.append("voucherRefundPrice", $('#voucherRefundPrice').val());
				formdata.append("shortTicketCase1Count", $('#shortTicketCase1Count').val());
				formdata.append("shortTicketCase1Price", $('#shortTicketCase1Price').val());
				formdata.append("shortTicketCase1Result", $('#shortTicketCase1Result').val());
				formdata.append("shortTicketCase2Count", $('#shortTicketCase2Count').val());
				formdata.append("shortTicketCase2Price", $('#shortTicketCase2Price').val());
				formdata.append("shortTicketCase2Result", $('#shortTicketCase2Result').val());
				formdata.append("memo", $('#memo').val());
				formdata.append("storeId", $('#storeId').val());
				formdata.append("finalPrice", $('#finalPrice').text());
				
				axios.post('/settlement/regist.proc', formdata,{
					  headers: {
						'Content-Type': 'multipart/form-data'
					  }
					}).then((response) => {
					if (response.data.resultCd == '00') {
				      	alert('정상처리 되었습니다.');
						location.href = '/settlement/list.view';
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

var visitMember1 = []; 
var visitMember2 = [];
var visitMemberDate = [];  

var voucherCount = [];
var shortVoucherCount = [];
var nonMemberCount = [];
var voucherDate = [];

var salePrice = [];
var salePriceDate = [];

$(function(){
	
	var formdata = new FormData();
		formdata.append("storeId", $('#storeId').val());
		
		axios.post('/settlement/getWeekVisitMemberInfo.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  for(var i=0; i < response.data.length; i++){
			  	  visitMember1[i] = response.data[i].totalCount;
				  visitMember2[i] = response.data[i].visitMemberCount;
				  visitMemberDate[i] = response.data[i].searchDate;
			  }	
			  
	 		  chart1.render();
	
		}).catch(function (error) {
			console.log(error);
		});
		
		axios.post('/settlement/selectWeekVisitInfo.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  for(var i=0; i < response.data.length; i++){
				  voucherCount[i] = response.data[i].voucherCount;
		          shortVoucherCount[i] = response.data[i].shortVoucherCount;
                  nonMemberCount[i] = response.data[i].nonMemberCount;
				  voucherDate[i] = response.data[i].searchDate;
			  }	
			  chart2.render();
		}).catch(function (error) {
			console.log(error);
		});
		
		axios.post('/settlement/selectWeekRevenue.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  for(var i=0; i < response.data.length; i++){
			  	  salePrice[i] = response.data[i].salePrice;
				  salePriceDate[i] = response.data[i].searchDate;
			  }	
			  chart3.render();
		}).catch(function (error) {
			console.log(error);
		});
		
});

$("#storeId").on("change", function() {
	location.href = '/settlement/daily.view?storeId=' + $('#storeId').val();
});

$("#fbRevenue").on("keyup", function() {
	setFinalPrice();
});

$("#voucherRefundPrice").on("keyup", function() {
	setFinalPrice();
});

$("#shortTicketCase1Count").on("keyup", function() {
	$('#shortTicketCase1Result').val(parseInt($('#shortTicketCase1Count').val() || 0 ) * parseInt($('#shortTicketCase1Price').val() || 0 ));     
	setFinalPrice();
});

$("#shortTicketCase1Price").on("keyup", function() {
	$('#shortTicketCase1Result').val(parseInt($('#shortTicketCase1Count').val() || 0 ) * parseInt($('#shortTicketCase1Price').val() || 0 ));
	setFinalPrice();
});

$("#shortTicketCase2Count").on("keyup", function() {
	$('#shortTicketCase2Result').val(parseInt($('#shortTicketCase2Count').val() || 0 ) * parseInt($('#shortTicketCase2Price').val() || 0 ));
	setFinalPrice();
});

$("#shortTicketCase2Price").on("keyup", function() {
	$('#shortTicketCase2Result').val(parseInt($('#shortTicketCase2Count').val() || 0 ) * parseInt($('#shortTicketCase2Price').val() || 0 ));
	setFinalPrice();
});

function setFinalPrice(){
	var finalPrice = parseInt($('#fbRevenue').val() || 0) + 
					 parseInt($('#voucherRefundPrice').val() || 0) +  
					 parseInt($('#shortTicketCase1Result').val() || 0) + 
					 parseInt($('#shortTicketCase2Result').val() || 0);
	$('#finalPrice').text(finalPrice);
}

