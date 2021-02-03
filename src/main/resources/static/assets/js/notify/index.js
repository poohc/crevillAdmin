'use strict';
var notify = $.notify('<i class="fa fa-bell-o"></i><strong>로딩 시작</strong>', {
    type: 'theme',
    allow_dismiss: false,
    delay: 2000,
    showProgressbar: true,
    timer: 1000,
    animate:{
        enter:'animated fadeInDown',
        exit:'animated fadeOutUp'
    }
});

setTimeout(function() {
    notify.update('message', '<i class="fa fa-bell-o"></i><strong>데이터</strong> 로딩중.');
}, 1000);
