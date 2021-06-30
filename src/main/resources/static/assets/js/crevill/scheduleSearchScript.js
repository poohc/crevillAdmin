$('#scheduleSearch').click(function(){
	
	if($('#scheduleDate').val().length == 0){
		alert('검색 날짜를 입력해주세요.');
		return false;	
	}
	
	$('#scheduleStart').val($('#scheduleDate').val().replace(/[^0-9]/g,""));
	$('#scheduleForm').submit(); 
});


$('#storeId').change(function(){
	
	if($('#scheduleDate').val().length == 0){
		alert('검색 날짜를 입력해주세요.');
		return false;	
	}
	location.href = '/schedule/search.view?scheduleStart=' + $('#scheduleDate').val().replace(/[^0-9]/g,"") + '&storeId=' + $('#storeId').val();
	 
});