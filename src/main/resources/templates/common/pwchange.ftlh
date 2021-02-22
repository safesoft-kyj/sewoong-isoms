<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>ISO Management System</title>

    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="/static/js/bootstrap.min.js"></script>
    <!--Nifty Admin [ RECOMMENDED ]-->
    <script src="/static/js/nifty.min.js"></script>
    <!--Bootstrap Validator -->
    <script src="/static/plugins/bootstrap-validator/bootstrapValidator.js"></script>
</head>
<body>
<div class="cls-content">
    <div class="cls-content-lg" style="width:750px !important;">
        <div class="panel panel-bordered-info">
            <form method="post" id="pwChangeForm">
                <div class="panel-body text-left">
                    <div class="mar-btm row">
                        <div class="text-center"><span class="text-bold" style="font-size:25px;">Password Change</span>
                        </div>
                    </div>
                    <hr/>
                    <div class="mar-btm pad-top breadcrumb bg-info">
                        <span>[${user.name}]님의 비밀번호 기한이 지났거나, 비밀번호가 초기화 되어 비밀번호 변경이 필요합니다.</span>
                    </div>
                    <div class="form-group" style="margin-bottom: 75px;">
                        <@spring.formHiddenInput "user.id" />
                        <label class="control-label col-sm-3 pad-top" for="password">현재 비밀번호</label>
                        <div class="col-sm-9">
                            <@spring.formPasswordInput "user.password" "class='form-control'" />
                            <@spring.showErrors "" "color:red;" />
                        </div>
                    </div>
                    <div class="form-group" style="margin-bottom: 120px;">
                        <label class="control-label col-sm-3" for="newPassword">신규 비밀번호</label>
                        <div class="col-sm-9">
                            <@spring.formPasswordInput "user.newPassword" "class='form-control' placeholder='(영문자,숫자,특수기호 포함 8자 이상)'" />
                            <@spring.showErrors "" "color:red;" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="newPassword">비밀번호 확인</label>
                        <div class="col-sm-9">
                            <@spring.formPasswordInput "user.confirmPassword" "class='form-control' placeholder='(영문자,숫자,특수기호 포함 8자 이상)'" />
                            <@spring.showErrors "" "color:red;" />
                        </div>
                    </div>
                </div>
                <div class="panel-footer text-right">
                    <button class="btn btn-info" name="action" value="change" type="submit"><i class="pli-yes"></i>
                        비밀번호 변경
                    </button>
                    <a class="btn btn-warning" href="/password-change/skip"><i class="pli-back"></i>
                        다음에 변경하기
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
