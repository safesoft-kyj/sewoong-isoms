<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > User > Profile</title>
</head>
<body>
<div class="tab-base">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="/user/profile">Profile</a>
        </li>
        <li>
            <a href="/user/signature">Signature</a>
        </li>
        <li>
            <a href="/user/profile/role">Role</a>
        </li>
        <li style="float:right;">
            <a href="/agreements-withdrawal">
                <i class="pli-file-trash"></i>
                개인정보 동의 철회 신청 바로가기
                <#--                <img class="menu-title" src="/static/img/withdrawal.jpg" alt="철회신청">-->
            </a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade active in">
            <form class="form-horizontal form-padding" method="post"
            ">
            <!--Static-->
            <div class="form-group">
                <div class="form-group">
                    <label class="col-sm-3 control-label">현재 비밀번호</label>
                    <div class="col-sm-9">
                        <@spring.formPasswordInput "pwUser.password" "class='form-control'"/>
                        <@spring.showErrors "" "color:red;"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">신규 비밀번호</label>
                    <div class="col-sm-9">
                        <@spring.formPasswordInput "pwUser.newPassword" "class='form-control' placeholder='(영문자,숫자,특수기호 포함 8자 이상)'" />
                        <@spring.showErrors "" "color:red;"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">신규 비밀번호 확인</label>
                    <div class="col-sm-9">
                        <@spring.formPasswordInput "pwUser.confirmPassword" "class='form-control' placeholder='(영문자,숫자,특수기호 포함 8자 이상)'"/>
                        <@spring.showErrors "" "color:red;"/>
                    </div>
                </div>
            </div>
            <div class="panel-footer text-right">
                <a class="btn btn-danger" href="/user/profile"><i class="fa fa-times"></i> 취소</a>
                <button class="btn btn-primary" type="submit" id="submitBtn" data-save-text="Saving.."
                        data-loading-text="정보 수정 중.. 잠시만 기다려 주세요."><i class="fa fa-check"></i>비밀번호 변경
                </button>
            </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
