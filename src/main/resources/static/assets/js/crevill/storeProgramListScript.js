function stopProgram(programId){
	if(confirm('해당 프로그램을 중지 하시겠습니까?')){
		$.ajax({
			type : "POST",
			data: {
		            programId : programId
		    },
			url : '/storeProgram/stop.proc',
			success : function(data){
				if(data.resultCd == '00'){
					alert('중지 처리되었습니다.');
					location.href = '/storeProgram/list.view';
				} else {
					alert('프로그램 중지 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
					return false;	
				}
				
			},
			error : function(error) {
		        alert('프로그램 중지 처리 중 오류가 발생했습니다. 다시 시도하여 주세요.');
				return false;
		    }
		});	
	}
}

function updateProgram(programId){
	var url = '/storeProgram/update.view?programId=' + programId;
	location.href = url;
}