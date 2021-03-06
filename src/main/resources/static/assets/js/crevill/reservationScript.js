var acceessableCount = 1; //동시접근제한수

Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      cellPhone : '전화번호',
			  voucherNo : '바우처',
   			  scheduleDate : '스케쥴날짜',		  
			  scheduleTime : '스케쥴시간',
			  scheduleId : '수업',
		    }
	  	}
  }
});

var listVm = new Vue({
    el: '#page-body',
    data: {
    	cellPhone : '',
	  	voucherNo : '',
		scheduleDate : '',	  	
		scheduleTime : '',
	  	scheduleId : '',
		storeList : []
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
			acceessableCount  = acceessableCount - 1; //count부터 뺀다
			
			if (acceessableCount < 0 ) {
		    	alert("이미 작업이 수행중입니다.");
		    } else {
			
				if($('#scheduleId').val() != 'N' && $('#tutoringYn').val() == 'Y'){
					alert('클래스와 튜터링은 동시에 선택할 수는 없습니다.');
					acceessableCount = 1;
					return false;
				} else {
					var formdata = new FormData();
					formdata.append("cellPhone",  $('#cellPhone').val());
					formdata.append("voucherNo",  $('#voucherNo').val());
					formdata.append("tutoringYn", $('#tutoringYn').val());
					formdata.append("scheduleId", $('#scheduleId').val());
					formdata.append("childName",  $('#childName').val());
					
					axios.post('/reservation/regist.proc', formdata,{
						  headers: {
							'Content-Type': 'multipart/form-data'
						  }
						}).then((response) => {
						if (response.data.resultCd == '00') {
					      	alert('정상처리 되었습니다.');
							location.href = '/reservation/regist.view';
					    } else {
							alert(response.data.resultMsg);
							return false;
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

$('#voucherSearchBtn').click(function(){
	
	$.ajax({
		type : "POST",
		data: {
	            buyCellPhone : $('#cellPhone').val()
	    },
		url : '/voucher/getMemberVoucherList.proc',
		success : function(data){
			if(data.resultCd == '00'){
				
				if(data.voucherList.length == 0){
					alert('바우처가 없습니다.');
					$("select[name='voucherNo'] option").remove();
					$("select[name='childName'] option").remove();
					$("select[name='scheduleId'] option").remove();
					$("select[name='tutoringYn'] option").remove();
					return false;
				} else {
					alert('바우처가 확인되었습니다.');
					$('#cellPhoneTxt').text($('#cellPhone').val());
					$("select[name='voucherNo'] option").remove();
					$("#voucherNo").append('<option value="">선택</option>');
					for(var i=0; i < data.voucherList.length; i++){
						$("#voucherNo").append('<option value="' + data.voucherList[i].voucherNo + '" data-ticketName="' + data.voucherList[i].ticketName + '"data-storeId="' + data.voucherList[i].storeId +'">[지점: ' + data.voucherList[i].storeNameShort + ' | 잔여시간 : ' + data.voucherList[i].timeLeftHour + ']' + data.voucherList[i].ticketName + '</option>');
					}
					
					$.ajax({
						type : "POST",
						data: {
					            cellPhone : $('#cellPhone').val()
					    },
						url : '/member/getChildList.proc',
						success : function(data){
							if(data.resultCd == '00'){
								
								if(data.list.length == 0){
									alert('등록된 자녀가 없습니다.');
									return false;
								} else {
									$("select[name='childName'] option").remove();
									$("#childName").append('<option value="">선택</option>');
									for(var i=0; i < data.list.length; i++){
										$("#childName").append('<option value="' + data.list[i].childName + '">' + data.list[i].childName + '</option>');
									}
								}
								
							} else {
								alert('휴대폰 번호를 다시 입력해주세요.');
								return false;	
							}
							
						},
						error : function(error) {
					        alert('자녀 조회 중 오류가 발생했습니다. 다시 시도하여 주세요.');
							return false;
					    }
					});
				}
				
			} else {
				alert('회원이 아닙니다.');
				return false;	
			}
			
		},
		error : function(error) {
	        alert("바우처 목록을 가져오는 중에 오류가 발생했습니다. 다시 시도하여 주세요.");
			return false;
	    }
	});
	
});

$('#scheduleSearch').click(function(){
	
	$.ajax({
		type : "POST",
		data: {
	            scheduleStart : $('#scheduleDate').val().replace(/[^0-9]/g,""),
				storeId : $('#storeId').val(),
				voucherNo : $('#voucherNo').val()
	    },
		url : '/schedule/getScheduleList.proc',
		success : function(data){
			if(data.resultCd == '00'){
				$("select[name='scheduleId'] option").remove();
				$("select[name='tutoringYn'] option").remove();
				$("#scheduleId").append('<option value="N">선택</option>');
				for(var i=0; i < data.scheduleList.length; i++){
					$("#scheduleId").append('<option value="' + data.scheduleList[i].scheduleId + '">[잔여 : '+data.scheduleList[i].classAvaCnt+']' + data.scheduleList[i].scheduleTime +' : ' + data.scheduleList[i].playName + ' | ' + data.scheduleList[i].storeNameShort + '</option>');
				}
				
				if(data.attributeList.includes('TUTORING')){
					$("#tutoringYn").append('<option value="N">선택</option>');
					for(var i=0; i < data.scheduleList.length; i++){
						$("#tutoringYn").append('<option value="' + data.scheduleList[i].scheduleId + '">[잔여 : '+data.scheduleList[i].tutoringAvaCnt+']' + data.scheduleList[i].scheduleTime +' : 튜터링 | ' + data.scheduleList[i].storeNameShort + '</option>');
					}	
				}
				
			} else {
				alert('해당 날짜에 등록된 수업이 없습니다.');
				return false;	
			}
			
		},
		error : function(error) {
	        alert("수업 목록을 가져오는 중에 오류가 발생했습니다. 다시 시도하여 주세요.");
			return false;
	    }
	});
	
});

$('#voucherNo').change(function(){
	$.ajax({
		type : "POST",
		data: {
	            storeId : $("#voucherNo option:selected").data().storeid,
				type : 'RESERVATION'
	    },
		url : '/store/getStoreList.proc',
		success : function(data){
			if(data.resultCd == '00'){
				
				 for(var i=0; i < data.storeList.length; i++){
					  Vue.set(listVm.storeList, i, data.storeList[i]);
				 }	
				 listVm.storeList.splice(data.storeList.length);
			} else {
				alert('선택한 바우처가 등록된 매장이 없습니다. 관리자에게 문의하여 주세요.');
				return false;	
			}
			
		},
		error : function(error) {
	        alert('선택한 바우처가 등록된 매장이 없습니다. 관리자에게 문의하여 주세요.');
			return false;
	    }
	});
});

$('#storeId').change(function(){
	if($('#storeId').find(':selected').data('time') == 0){
		alert('1회권 사용이 불가능한 매장입니다.');		
		$("#voucherNo option:eq(0)").prop("selected", true);
		document.querySelectorAll(`[data-ticketname='1회권']`).forEach(function(item) {
		  item.style.display = 'none';
		});
	} else {
		$("#voucherNo option:eq(0)").prop("selected", true);
		document.querySelectorAll(`[data-ticketname='1회권']`).forEach(function(item) {
		  item.style.display = 'block';
		});
	}
});


var today = new Date();
var year = today.getFullYear(); // 년도
var month = today.getMonth() + 1;  // 월
var date = today.getDate();  // 날짜

year = year + '';
month = month + '';
date = date + '';
if(month < 10) month = '0' + month;
if(date < 10) date = '0' + date;

$('input[name="scheduleDate"]').daterangepicker({
    "singleDatePicker": true,
    "startDate": year + month + date,
    "minDate": year + month + date,
	"locale": {
        "format": "YYYYMMDD",
        "separator": " - ",
        "applyLabel": "적용",
        "cancelLabel": "취소",
        "weekLabel": "W",
        "daysOfWeek": [
            "Su",
            "Mo",
            "Tu",
            "We",
            "Th",
            "Fr",
            "Sa"
        ]}
});

function cancel(){
	location.href = '/reservation/regist.view';
}