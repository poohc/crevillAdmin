$('#searchBtn').click(function(){
	location.href='/voucher/typeStat.view?searchStartDate=' + $('#searchDate').val().split('~')[0].replace(/[^0-9]/g,"") + '&searchEndDate=' + $('#searchDate').val().split('~')[1].replace(/[^0-9]/g,"");
});