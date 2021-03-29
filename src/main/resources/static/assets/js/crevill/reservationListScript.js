$('#searchBtn').click(function() {
	location.href = "/reservation/list.view?scheduleStart=" + $('input[name="scheduleStart"]').val();   
});

$('input[name="scheduleStart"]').daterangepicker({
	singleDatePicker : true,
	locale: {
      format: 'YYYYMMDD',
      separator: '',
      applyLabel: "적용",
      cancelLabel: "닫기"
    } 	
});