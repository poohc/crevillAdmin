var listVm = new Vue({
    el: '#page-body',
    data: {
			statList : [],
	}
});


$('#searchBtn').click(function(){
//	var formdata = new FormData();
//		formdata.append("searchStartDate", $('#searchDate').val().split('~')[0].replace(/[^0-9]/g,""));
//		formdata.append("searchEndDate", $('#searchDate').val().split('~')[1].replace(/[^0-9]/g,""));
//		
//		axios.post('/voucher/getVoucherStatList.proc', formdata,{
//			  headers: {
//				'Content-Type': 'multipart/form-data'
//			  }
//		}).then((response) => {
//			  for(var i=0; i < response.data.length; i++){
//			  	  Vue.set(listVm.statList, i, response.data[i]);
//			  }	
//			  listVm.statList.splice(response.data.length);
//			  $('.dataTables_empty').remove();
//		}).catch(function (error) {
//			console.log(error);
//		});

	location.href='/voucher/stat.view?searchStartDate=' + $('#searchDate').val().split('~')[0].replace(/[^0-9]/g,"") + '&searchEndDate=' + $('#searchDate').val().split('~')[1].replace(/[^0-9]/g,"");  

});
