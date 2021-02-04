<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
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
            <form class="form-horizontal form-padding" method="post" id="signForm">
                <!--Static-->
                <div class="form-group">
                    <label class="control-label">Name</label>
                    <input type="text" class="form-control" value="${user.engName!''}" readonly="readonly">
                    <label class="control-label">Team / Department</label>
                    <input type="text" class="form-control" value="${user.teamDept!''}" readonly="readonly">
                    <label class="control-label">Email</label>
                    <input type="text" class="form-control" value="${user.email!''}" readonly="readonly">
                    <label class="control-label">Birth Date</label>
                    <input type="text" class="form-control"
                           value="${user.birthDate?has_content?then(user.birthDate?string('yyyy-MM-dd'), 'N/A')}"
                           readonly="readonly">
                    <label class="control-label">Phone</label>
                    <input type="text" class="form-control" value="${user.phone!'N/A'}" readonly="readonly">
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
