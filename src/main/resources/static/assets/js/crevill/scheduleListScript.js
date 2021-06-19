var vm = new Vue({
	el: '#page-body',
	data: {
		scheduleId: '',
		scheduleReservationYn: '',
		numberOfPeople: '',
		tutoringNumber: '',
	},
	methods: {
		updateSchedule(scheduleId) {
			var formdata = new FormData();
			formdata.append("scheduleId", scheduleId);
			if ($('#numberOfPeople').val() < 1) {
				alert('클래스 정원은 0보다 커야 합니다.');
				return false;
			}
		
			if ($('#tutoringNumber').val() < 1) {
				alert('튜터링 정원은 0보다 커야 합니다.');
				return false;
			}
			formdata.append("numberOfPeople", $('#numberOfPeople').val());
			formdata.append("tutoringNumber", $('#tutoringNumber').val());
		
			axios.post('/schedule/updateSchedule.proc', formdata, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			}).then((response) => {
				if (response.data.resultCd == '00') {
					alert('정상 처리 되었습니다.');
					location.href = '/schedule/list.view?storeId=' + $('#storeId').val();
				} else {
					alert('정원 변경 처리중 오류가 발생했습니다.');
					return false;
				}
			});
		},
		deleteSchedule(scheduleId) {
			var formdata = new FormData();
			formdata.append("scheduleId", scheduleId);
			axios.post('/schedule/deleteSchedule.proc', formdata, {
				headers: {
					'Content-Type': 'multipart/form-data'
				}
			}).then((response) => {
				if (response.data.resultCd == '00') {
					alert('삭제 처리 되었습니다.');
					location.href = '/schedule/list.view?storeId=' + $('#storeId').val();
				} else {
					alert('삭제 처리중 오류가 발생했습니다.');
					return false;
				}
			});
		}
	}
});

$('#storeId').change(function() {
	location.href = '/schedule/list.view?storeId=' + $('#storeId').val();
});

function updateSetup(scheduleId, numberOfPeople, tutoringNumber) {
	vm.scheduleId = scheduleId;
	vm.numberOfPeople = numberOfPeople;
	vm.tutoringNumber = tutoringNumber;
}

function deleteSetup(scheduleId) {
	vm.scheduleId = scheduleId;
	
	var formdata = new FormData();
	formdata.append("scheduleId", scheduleId);

	axios.post('/schedule/getReservationScheduleCount.proc', formdata, {
		headers: {
			'Content-Type': 'multipart/form-data'
		}
	}).then((response) => {
		if (response.data.resultCd == '00') {
			vm.scheduleReservationYn = response.data.reservationScheduleYn;			
		} else {
			alert('처리중 오류가 발생했습니다.');
			return false;
		}
	});
}