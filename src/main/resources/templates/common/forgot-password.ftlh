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
    <div class="cls-content-lg">
        <div class="panel panel-bordered-info">
            <form method="post" id="pwChangeForm">
                <div class="panel-body text-left">
                    <div class="text-center mar-btm">
                        <div class="row">
                            <div class="col-xs-4"><img src="https://ch.caumc.or.kr/images/main_re_logo.gif"></div>
                            <div class="text-right"><span class="text-bold" style="font-size:25px;">임시 비밀번호 발급</span>
                            </div>
                        </div>
                        <hr>
                        <div class="mar-btm pad-top breadcrumb bg-info">
                            <span>임시 비밀번호를 발급 받으실 ID, E-mail 주소를 입력해 주세요.</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="control-label col-sm-3 pad-top" for="username">ID</label>
                            <div class="col-sm-9">
                                <@spring.formInput "user.username" "class='form-control'" />
                                <@spring.showErrors "" "color:red;" />
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <label class="control-label col-sm-3" for="newPassword">E-mail</label>
                            <div class="col-sm-9">
                                <@spring.formInput "user.email" "class='form-control'" />
                                <@spring.showErrors "" "color:red;" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer text-right">
                    <button class="btn btn-info" name="action" value="change" type="submit"><i class="pli-yes"></i>
                        비밀번호 발급
                    </button>
                    <a class="btn btn-warning" href="/login"><i class="pli-back"></i>
                        다음에 변경하기
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
