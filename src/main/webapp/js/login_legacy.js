$('.code-btn').on('click', function() {
  if ($(this).hasClass('code-btn-gray')) {
    return false;
  }
  var telephone = $('#tel_num').val();
  var phoneNumReg = /^1\d{10}$/;
  if (telephone && telephone.length == 11 && phoneNumReg.test(telephone)) {
    console.log(ctx);
    $.post(ctx + "/login/sendSmsCode", {
      "mobilePhoneNumber" : telephone
    }, function(data) {
      if (data.status != "0") {
        alert(data.message);
      } else {
        time_inter(); // 按钮倒计时
      }
    }, "json");
  } else {
    alert('请输入正确手机号码');
  }
})

$(".login-button").click(function() {
  var telephone = $('#tel_num').val();
  var checkCode = $("#code_num").val();
  var phoneNumReg = /^1[3|4|5|7|8]\d{9}$/;
  if (!telephone && telephone.length != 11 && !phoneNumReg.test(telephone)) {
    alert('请输入正确手机号码');
  } else if (checkCode == "") {
    alert("请输入验证码");
  } else {
    $("#loginForm").submit();
    /*
     * $.post(ctx + "/login",{ "mobilePhoneNumber":telephone, "smsCode":checkCode },function(data){
     * if(data.status=="0"){ //window.location.href="address"; }else{ alert(data.info); } },"json");
     */
  }
})

var code_time = 90;

function time_inter() {
  $('.code-btn').addClass('code-btn-gray');
  var timeflag = setInterval(function() {
    $('.code-btn-gray').html("" + code_time + "s后获取").css({
      "cursor" : "no-",
      "color" : "#999"
    });
    code_time--;
    if (code_time == 0) {
      clearInterval(timeflag);
      $('.code-btn-gray').html("获取验证码").css({
        "cursor" : "pointer",
        "color" : "#a07941"
      });
      $('.code-btn').removeClass('code-btn-gray');
    }
  }, 1000)
}