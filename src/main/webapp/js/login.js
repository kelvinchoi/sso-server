function checkMobilePhoneNumber(mpn) {
  var regex = /^1\d{10}$/;
  return mpn && regex.test(mpn);
}

function checkSmsCode(smsCode) {
  var regex = /\d{6}/;
  return smsCode && regex.test(smsCode);
}

function checkSmsCodeBtnStatus(mpn) {
  var $smsCodeBtn = $('.login-smscode-btn');
  // console.log($smsCodeBtn.prop("disabled"));
  // console.log($smsCodeBtn.is(":disabled"));
  if (checkMobilePhoneNumber(mpn) && $smsCodeBtn.is(":disabled")) {
    $smsCodeBtn.prop("disabled", false);
  } else if (!checkMobilePhoneNumber(mpn) && !$smsCodeBtn.is(":disabled")) {
    $smsCodeBtn.prop("disabled", true);
  }
}

function countdown() {
  var $smsCodeBtn = $('.login-smscode-btn');

  var time = 90;
  $smsCodeBtn.html("已发送（" + time-- + "s）").prop("disabled", true);
  var countdownFlag = setInterval(function() {
    $smsCodeBtn.html("已发送（" + time-- + "s）");
  }, 1000);

  setTimeout(function() {
    $smsCodeBtn.prop("disabled", false).html("重新获取");
    clearInterval(countdownFlag);
  }, (time + 1) * 1000);
}

$("input[name='mobilePhoneNumber'").on("input", function() {
  checkSmsCodeBtnStatus(this.value);
});

$(".login-smscode-btn").on('click', function() {
  if ($(this).is(":disabled")) {
    return false;
  }

  var mobilePhoneNumber = $("input[name='mobilePhoneNumber'").val();

  if (checkMobilePhoneNumber(mobilePhoneNumber)) {
    $.post(ctx + "/login/sendSmsCode", {
      "mobilePhoneNumber" : mobilePhoneNumber
    }, function(data) {
      if (data.status != "0") {
        toastr.error(data.message);
      } else {
        countdown();
      }
    }, "json");
  } else {
    toastr.error("请输入正确的手机号码");
  }
});

$(".login-submit-btn").click(function() {
  var mobilePhoneNumber = $("input[name='mobilePhoneNumber']").val();
  var smsCode = $("input[name='smsCode']").val();

  if (!checkMobilePhoneNumber(mobilePhoneNumber)) {
    toastr.error('请输入正确的手机号码');
    return false;
  } else if (!smsCode || smsCode.length == 0) {
    toastr.error("请输入验证码");
    return false;
  } else if (!checkSmsCode(smsCode)) {
    toastr.error("验证码错误，请确认")
    return false;
  } else {
    $(".login-form").submit();
    /*
     * $.post(ctx + "/login",{ "mobilePhoneNumber":telephone, "smsCode":checkCode },function(data){
     * if(data.status=="0"){ //window.location.href="address"; }else{ alert(data.info); } },"json");
     */
  }
});

$(function() {
  if ($("input[name='mobilePhoneNumber']") && $("input[name='mobilePhoneNumber']").val().length > 0) {
    checkSmsCodeBtnStatus($("input[name='mobilePhoneNumber']").val());
  }

  if (errorMsg && errorMsg.length > 0) {
    toastr.error(errorMsg);
  }
});