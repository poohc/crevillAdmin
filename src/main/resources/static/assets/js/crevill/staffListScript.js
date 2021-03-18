function staffDelete(staffId){
	if(confirm('해당 직원을 퇴사 처리하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            staffId : staffId
		    },
			url : '/staff/updateEnd.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('퇴사 처리되었습니다.');
					location.href = '/staff/list.view';
				} else {
					alert('퇴사 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert("퇴사 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
		});	
	}
}

function staffUpdate(staffId, storeId){
	var url = '/staff/update.view?staffId=' + staffId + '&storeId=' + storeId;
	location.href = url;
}