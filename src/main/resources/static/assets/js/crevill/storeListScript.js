function storeDelete(storeId){
	if(confirm('해당 매장을 삭제하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            storeId : storeId
		    },
			url : '/store/delete.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('삭제되었습니다.');
					location.href = '/store/list.view';
				} else {
					alert('매장 삭제 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert("매장 삭제 중 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
		});	
	}
}

function storeUpdate(storeId){
	location.href = '/store/update.view?storeId='+storeId;
}