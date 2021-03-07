Vue.use(VeeValidate, {
  locale: 'ko',
  dictionary: {
    ko: {
		    attributes: {
		      buyCellPhone : '고객전화번호',
		    }
	  	}
  }
});

new Vue({
    el: '#page-body',
    data: {
    	buyCellPhone : '',
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
 			var formdata = new FormData();
			formdata.append("buyCellPhone", $('#buyCellPhone').val());
			formdata.append("voucherNo", $('input[name="voucherNo"]').val());
			formdata.append("usedChildrenName", $('#usedChildrenName').val());
			formdata.append("pgType", $('#pgType').val());
			formdata.append("approvalNo", $('#approvalNo').val());
			axios.post('/voucher/sale.proc', formdata,{
				  headers: {
					'Content-Type': 'multipart/form-data'
				  }
				}).then((response) => {
				if (response.data.resultCd == '00') {
			      	alert('정상처리 되었습니다.');
					location.href = '/voucher/sale.view';
			    }
				
			});	
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}); 

$('#searchMemberNameBtn').click(function(){
	
	$("select[name='usedChildrenName'] option").remove();
	
	$.ajax({
			type : "POST",
			data: {
		            cellPhone : $('#buyCellPhone').val()
		    },
			url : '/member/getMemberInfo.proc',
			success : function(data){
				$('#searchResultName').text(data[0].name);
				for(var i=0; i < data.length; i++){
					$("#usedChildrenName").append('<option value="' + data[i].childName + '">' + data[i].childName + '</option');
				}
				$('#gradeType').trigger('change');				
			},
			error : function(error) {
		        alert("이름검색 중 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
	});	 
});

$('#gradeType').change(function(){
	
	if($(this).val() == 'REGIST'){
		$('#usedChildrenNameDiv').show();
	} else {
		$('#usedChildrenNameDiv').hide();
	}
	
	$.ajax({
			type : "POST",
			data: {
		            gradeType : $('#gradeType').val()
		    },
			url : '/voucher/getVoucherList.proc',
			success : function(data){
				
				$("#voucherListDiv *").remove();
				var appendDiv = "";
				for(var i=0; i < data.length; i++){
					appendDiv += '<div class="col-sm-12">';
					appendDiv += '	<div class="card">';
					appendDiv += '		<div class="media p-20">';
					appendDiv += '			<div class="radio radio-primary mr-3">';
					appendDiv += '				<input id="voucherNo_'+i+'" type="radio" name="voucherNo" value="'+data[i].voucherNo+'">';
					appendDiv += '				<label for="voucherNo_'+i+'"></label>';
					appendDiv += '			</div>';
					appendDiv += '			<div class="media-body">';
					appendDiv += '				<h6 class="mt-0 mega-title-badge">';
					appendDiv += '					'+data[i].ticketName+'<span class="badge badge-primary pull-right">'+data[i].salePrice+'</span>';
					appendDiv += '				</h6>';
					for(var j=0; j < data[i].attribute.split(',').length; j ++){
						var style = 'badge-primary';
						if(j == 1) style = 'badge-secondary';
						if(j == 2) style = 'badge-info';
						if(j == 4) style = 'badge-success';
						appendDiv += '				<span class="badge '+style+'">'+data[i].attribute.split(',')[j]+'</span>';
					}
					appendDiv += '			</div>';
					appendDiv += '		</div>';
					appendDiv += '	</div>';
					appendDiv += '</div>';
				}
				$("#voucherListDiv").append(appendDiv);
			},
			error : function(error) {
		        alert("바우처 목록을 가져오는 중에 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
	});	 
});


function cancel(){
	location.href = '/voucher/sale.view';
}