function deletePlayInfo(playId){
	if(confirm('해당 플레이를 삭제 하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            playId : playId
		    },
			url : '/play/delete.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('삭제 처리되었습니다.');
					location.href = '/play/list.view';
				} else {
					alert('플레이 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert('플레이 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
				return false;
		    }
		});	
	}
}

function updatePlayInfo(playId){
	var url = '/play/update.view?playId=' + playId;
	location.href = url;
}