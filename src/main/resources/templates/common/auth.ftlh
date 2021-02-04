<html>
<head>
    <title>ISO Management System</title>
    <script>
        $(document).ready(function() {
           $("#authForm").submit(function(e) {
               e.preventDefault();
              if(!$("#accessCode").val()) {
                  alert('Access code 를 입력해 주세요.');
                  $("#accessCode").focus();
              } else {
                  $.ajax({
                      url: "/ajax/auth",
                      method: "post",
                      data: { r : Math.random(),accessCode:$("#accessCode").val()},
                      dataType: "json"
                  }).done(function(response) {
                     if(response.result == 'Y') {
                         location.replace("/external/sop/effective");
                     } else {
                         alert('Access code가 일치 하지 않습니다.');
                         $("#accessCode").val("").focus();
                     }
                  });
              }
           });
        });
    </script>
</head>
<body>


<div class="cls-content">
    <div class="cls-content-sm panel">
        <div class="panel-body">
<#--            <div class="mar-ver pad-btm">-->
<#--                <h1 class="h3">Aaron Chavez</h1>-->
<#--                <span>Administrator</span>-->
<#--            </div>-->
<#--            <div class="pad-btm mar-btm">-->
<#--                <img alt="Profile Picture" class="img-lg img-circle img-border-light" src="img/profile-photos/1.png">-->
<#--            </div>-->
            <p>Please enter your access code to unlock the screen!</p>
            <form id="authForm">
                <div class="form-group">
                    <input type="password" class="form-control" name="accessCode" id="accessCode" placeholder="Access code">
                </div>
                <div class="form-group text-right">
                    <button class="btn btn-block btn-lg btn-success" type="submit">Submit</button>
                </div>
            </form>
<#--            <div class="pad-ver">-->
<#--                <a href="pages-login.html" class="btn-link mar-rgt text-bold">Sign in using different account</a>-->
<#--            </div>-->
        </div>
    </div>
</div>

</body>
</html>
