$('#scheduleSearch').click(function(){
	$('#scheduleStart').val($('#scheduleDate').val().replace(/[^0-9]/g,""));
	$('#scheduleForm').submit(); 
});