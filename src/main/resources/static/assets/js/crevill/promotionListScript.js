function deletePromotion(promotionId){
	if(confirm('해당 프로모션을 삭제 하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            promotionId : promotionId
		    },
			url : '/promotion/delete.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('삭제 처리되었습니다.');
					location.href = '/promotion/list.view';
				} else {
					alert('프로모션 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert('프로모션 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
				return false;
		    }
		});	
	}
}

function stopPromotion(promotionId){
	if(confirm('해당 프로모션을 중지 하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            promotionId : promotionId
		    },
			url : '/promotion/stop.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('중지 처리되었습니다.');
					location.href = '/promotion/list.view';
				} else {
					alert('프로모션 중지 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert('프로모션 중지 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
				return false;
		    }
		});	
	}
}

function updatePromotionInfo(promotionId){
	var url = '/promotion/update.view?promotionId=' + promotionId;
	location.href = url;
}