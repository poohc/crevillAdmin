var acceessableCount = 1; //동시접근제한수

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

var listVm = new Vue({
    el: '#page-body',
    data: {
    	buyCellPhone : '',
		voucherMember : [],
		memberInfo : [],
		voucherList : [],
		storeList : []
    },
	methods: {
    validateBeforeSubmit() {
      this.$validator.validate().then((result) => {
        if (result) {
	        
			acceessableCount  = acceessableCount - 1; //count부터 뺀다
			
			if (acceessableCount < 0 ) {
		    	alert("이미 작업이 수행중입니다.");
		    } else {
				if($('input[name="productNo"]:checked').val().length == 0){
					alert('선택유형에 맞는 바우처가 없습니다. 바우처를 생성해주세요.');
					return false;
				} else {
					var formdata = new FormData();
					formdata.append("buyCellPhone", $('#buyCellPhone').val());
					formdata.append("productNo", $('input[name="productNo"]:checked').val());
					formdata.append("usedChildrenName", $('#usedChildrenName').val());
					formdata.append("pgType", $('#pgType').val());
					formdata.append("approvalNo", $('#approvalNo').val());
					formdata.append("storeId", $('#storeId').val());
					
					if($('#promotionId').val().length > 0){
						formdata.append("promotionId", $('#promotionId').val());	
					}
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
				}	
			}
			
			acceessableCount = acceessableCount + 1;	
 			
        } else {
			alert('항목을 올바르게 입력해주세요.');
		}
        
      });
    }
  }
}); 

$('#searchMemberNameBtn').click(function(){
	
	$("select[name='usedChildrenName'] option").remove();
	
	var formdata = new FormData();
		formdata.append("cellPhone", $('#buyCellPhone').val());
		
		axios.post('/member/getMemberInfo.proc', formdata,{
			  headers: {
				'Content-Type': 'multipart/form-data'
			  }
		}).then((response) => {
			  
			  if(response.data.resultCd == '00'){
		      	  for(var i=0; i < response.data.voucherMemberInfo.length; i++){
			  	 	 Vue.set(listVm.voucherMember, i, response.data.voucherMemberInfo[i]);
			  	  }
				  
				  $('#searchResultName').text(response.data.voucherMemberInfo[0].name);
				  for(var i=0; i < response.data.voucherMemberInfo.length; i++){
				  	  $("#usedChildrenName").append('<option value="' + response.data.voucherMemberInfo[i].childName + '">' + response.data.voucherMemberInfo[i].childName + '</option>');
					  $("#usedChildrenName").data('memberStoreId', response.data.voucherMemberInfo[i].storeId);
				  }
		
				  $('.memberVoucherListDiv').remove();
				  var appendDiv = "";
				  for(var i=0; i < response.data.voucherList.length; i++){
//			  	 	 Vue.set(listVm.voucherList, i, response.data.voucherList[i]);
					 appendDiv += '<div class="memberVoucherListDiv">';
					 appendDiv += response.data.voucherList[i].ticketName + '[' + response.data.voucherList[i].storeNameShort + ']';
					 appendDiv += '</div>';
			  	  } 
				  if(response.data.voucherList.length == 0){
					 appendDiv += '<div class="memberVoucherListDiv">';			  	  
					 appendDiv += '보유 바우처가 없습니다.';
					 appendDiv += '</div>';			
				  }
		
				  $("#voucherListSpan").append(appendDiv);
		
				  Vue.set(listVm.memberInfo, 0, response.data.memberInfo);
//				  Vue.set(listVm.voucherList, 0, response.data.voucherList);
				  listVm.voucherMember.splice(response.data.voucherMemberInfo.length);
				  
				  var storeId = response.data.memberInfo.storeId;
				  $("select[name='storeId'] option").remove();
		
		          var formdata = new FormData();
					formdata.append("storeId", storeId);
					
					axios.post('/store/getStoreList.proc', formdata,{
						  headers: {
							'Content-Type': 'multipart/form-data'
						  }
					}).then((response) => {
						  
						  if(response.data.resultCd == '00'){
					      	  for(var i=0; i < response.data.storeList.length; i++){
//						  	 	 Vue.set(listVm.storeList, i, response.data.storeList[i]);
								 $("#storeId").append('<option value="' + response.data.storeList[i].storeId + '">' + response.data.storeList[i].storeNameShort + '</option>');
						  	  }
//							  $('#gradeType').trigger('change');		  
//							  Vue.set(listVm.storeList, 0, response.data.storeList);
					
						  } else {
							alert('매장정보를 불러오는 중 오류가 발생했습니다.');
						  } 
			
					}).catch(function (error) {
						alert('매장정보를 불러오는 중 오류가 발생했습니다.');
					});
					
			  } else {
				alert('해당 매장의 회원이 아니거나, 조회되지 않는 회원입니다.');
			  } 

		}).catch(function (error) {
			alert('회원정보를 불러오는 중 오류가 발생했습니다.');
		});
});

$('#storeId').change(function(){
	getVoucherList();	 
});

//$('#gradeType').change(function(){
//	
//	if($(this).val() == 'REGIST'){
//		$('#usedChildrenNameDiv').show();
//	} else {
//		$('#usedChildrenNameDiv').hide();
//	}
//	getVoucherList();	 
//});

function getVoucherList(){
	$.ajax({
			type : "POST",
			data: {
//		            gradeType : $('#gradeType').val(),
				    storeId : $('#storeId').val()
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
					appendDiv += '				<input id="productNo_'+i+'" type="radio" name="productNo" value="'+data[i].productNo+'">';
					appendDiv += '				<label for="productNo_'+i+'"></label>';
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
				setPromotion();
			},
			error : function(error) {
		        alert("바우처 목록을 가져오는 중에 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
	});	 
}

function setPromotion(){
	$("select[name='promotionId'] option").remove();
	$("#promotionId").append('<option value="">프로모션 없음</option>');
	$.ajax({
			type : "POST",
			data: {
		            storeId : $("#usedChildrenName").data('memberStoreId')
		    },
			url : '/promotion/getPromotionList.proc',
			success : function(data){
				
				if(data.length > 0){
					for(var i=0; i < data.length; i++){
						$("#promotionId").append('<option value="' + data[i].promotionId + '">' + data[i].promotionName + '</option>');
					}	
				} else {
//					alert('사용가능한 프로모션이 없습니다.');
					return false;	
				}
								
			},
			error : function(error) {
		        alert("프로모션 검색 중 오류가 발생했습니다. 다시 시도하여 주세요.");
				return false;
		    }
	});	 	
}

function cancel(){
	location.href = '/voucher/sale.view';
}