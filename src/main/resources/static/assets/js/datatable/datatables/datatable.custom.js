$(document).ready(function() {
    $('#basic-1').DataTable();
	
//	$('.export-button').DataTable();
	$('.export-button').DataTable( {
        dom: 'Bfrtip',
        buttons: [
            'copyHtml5',
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    } );

//	$('#statTable').DataTable({
//        "order": [[8, 'asc' ]]
//    });

});