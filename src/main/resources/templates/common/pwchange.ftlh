<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>Chung Ang University Hospital - ISO Management System</title>
    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="/static/css/nifty.min.css" rel="stylesheet">
    <!--Premium Icons [ OPTIONAL ]-->
    <link href="/static/premium/icon-sets/icons/line-icons/premium-line-icons.min.css" rel="stylesheet">
    <link href="/static/premium/icon-sets/icons/solid-icons/premium-solid-icons.min.css" rel="stylesheet">

    <!--jQuery [ REQUIRED ]-->
    <script src="/static/js/jquery.min.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="/static/js/bootstrap.min.js"></script>
    <!--Nifty Admin [ RECOMMENDED ]-->
    <script src="/static/js/nifty.min.js"></script>
    <!--Bootstrap Validator -->
    <script src="/static/plugins/bootstrap-validator/bootstrapValidator.js"></script>

    <script>
        $(document).ready(function(){
            $("#pwChangeForm").bootstrapValidator({
                message: 'This value is not valid',
                fields: {
                    newPassword: {
                        validators: {
                            remote: {
                                message: '현재 패스워드와 동일합니다.',
                                url: '/ajax/password-chanage/validation',
                                type: 'POST',
                                name: 'data' // 입력된 데이터 name을 keyword로 한다.
                            },
                            notEmpty: {
                                message: '신규 비밀번호를 입력해 주세요.'
                            },
                            identical: {
                                field: 'confirmPassword',
                                message: '신규 비밀번호와 비밀번호 확인란이 동일하지 않습니다.'
                            },
                            regexp: {
                                //최소 8자, 대문자 하나 이상, 소문자 하나, 숫자 하나 및 특수문자 하나 이상
                                // regexp: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/,
                                //최소 8자, 최소 하나의 문자, 숫자, 특수문자 조합
                                regexp: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/,
                                message: '최소 8자 이상 영문자, 숫자, 특수기호가 포함되어야 합니다.'
                            }
                        }
                    },
                    confirmPassword: {
                        validators: {
                            notEmpty: {
                                message: '비밀번호 확인란을 입력해 주세요.'
                            },
                            identical: {
                                field: 'newPassword',
                                message: '신규 비밀번호와 비밀번호 확인란이 동일하지 않습니다.'
                            },
                            regexp: {
                                //최소 8자, 대문자 하나 이상, 소문자 하나, 숫자 하나 및 특수문자 하나 이상
                                regexp: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/,
                                message: '최소 8자 이상 영문자, 숫자, 특수기호가 포함되어야 합니다.'
                            }
                        }
                    }
                }
            });
        })

    </script>

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
                            <input type="password" id="newPw" name="newPassword" placeholder="(알파벳,숫자,특수기호 포함 8자 이상)"
                                   class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="newPassword">비밀번호 확인</label>
                        <div class="col-sm-9">
                            <input type="password" id="confirmPw" name="confirmPassword"
                                   placeholder="(알파벳,숫자,특수기호 포함 8자 이상)" class="form-control"/>
                        </div>
                    </div>
                </div>
                <div class="panel-footer text-right">
                    <button class="btn btn-info" name="action" value="change" type="submit"><i class="pli-yes"></i>
                        비밀번호 변경
                    </button>
                    <a class="btn btn-warning" href="/password-change/skip" type="submit"><i class="pli-back"></i>
                        다음에 변경하기
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
