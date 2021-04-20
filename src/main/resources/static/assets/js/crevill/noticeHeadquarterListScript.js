function deleteNotice(noticeId){
	if(confirm('해당 공지를 삭제 하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            noticeId : noticeId
		    },
			url : '/branches/noticeDelete.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('삭제 처리되었습니다.');
					location.href = '/branches/headquarterList.view';
				} else {
					alert('공지 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert("공지 삭제 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
		});	
	}
}

function updateNotice(noticeId){
	var url = '/branches/headquarterUpdate.view?noticeId=' + noticeId;
	location.href = url;
}