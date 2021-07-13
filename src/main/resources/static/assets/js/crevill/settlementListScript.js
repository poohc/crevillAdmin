$("#storeId").on("change", function() {
	location.href = '/settlement/list.view?storeId=' + $('#storeId').val();
});

function setMemo(staffName, memo){
	$('#memo').val(memo);
	$('#staffName').text(staffName);
}	