var listVm = new Vue({
    el: '#dashboardDiv',
    data: {
			list : [],
			reservationTimeList : [],
			trialReservationList : [],
			instructorList : []
	},
	methods: {
	    	예약검색 : function(event){
				
				var formdata = new FormData();
					formdata.append("reservationTime", event.target.value.replace('시',''));
					formdata.append("storeId", $('#storeId').val());
					
					axios.post('/main/getTodayReservationInfo.proc', formdata,{
						  headers: {
							'Content-Type': 'multipart/form-data'
						  }
					}).then((response) => {
						  if(response.data.length == 0){
							  listVm.list.splice(0);
							  listVm.reservationTimeList.splice(0);
						  } else {
						  	  for(var i=0; i < response.data.length; i++){
								  Vue.set(listVm.list, i, response.data[i]);
								  Vue.set(listVm.reservationTimeList, i, response.data[i]);
							  }	
							  listVm.list.slice().sort(function(a, b) {
				    		  	  return b.reservationTime - a.reservationTime;
				              });	
							  listVm.reservationTimeList.slice().sort(function(a, b) {
				    		  	  return b.reservationTime - a.reservationTime;
				              });	
						  }
						  
					}).catch(function (error) {
						console.log(error);
					});
			},
        },
}); 

$(function(){
	
	var formdata = new FormData();
		formdata.append("reservationTime", $('#reservationTime').val().replace('시',''));
		formdata.append("storeId", $('#storeId').val());
		
		axios.post('/main/getTodayReservationInfo.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  for(var i=0; i < response.data.length; i++){
			  	  Vue.set(listVm.list, i, response.data[i]);
				  Vue.set(listVm.reservationTimeList, i, response.data[i]);
			  }	
			  
			  listVm.list.splice(response.data.length);
			  listVm.reservationTimeList.splice(response.data.length);
	
		}).catch(function (error) {
			console.log(error);
		});
		
});


$('#storeIdTrial').change(function(){
	var formdata = new FormData();
		formdata.append("storeId", $('#storeIdTrial').val());
		
		axios.post('/reservation/getTrialReservationList.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  for(var i=0; i < response.data.length; i++){
			  	  Vue.set(listVm.trialReservationList, i, response.data[i]);
			  }	
			  listVm.trialReservationList.splice(response.data.length);

		}).catch(function (error) {
			console.log(error);
		});
});

$('#storeIdNs').change(function(){
	var formdata = new FormData();
		formdata.append("storeId", $('#storeIdNs').val());
		
		axios.post('/staff/getInstructorList.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  for(var i=0; i < response.data.length; i++){
			  	  Vue.set(listVm.instructorList, i, response.data[i]);
			  }
			  listVm.instructorList.splice(response.data.length);

		}).catch(function (error) {
			console.log(error);
		});
});

setTimeout(() => {
	$('#storeIdTrial').trigger('change');
	$('#storeIdNs').trigger('change');
}, 1000);