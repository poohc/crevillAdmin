$('#storeId').change(function(){
	location.href = '/voucher/refundList.view?storeId=' + $('#storeId').val();
});