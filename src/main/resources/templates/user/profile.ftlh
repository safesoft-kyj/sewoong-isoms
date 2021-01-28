<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>CAUH ISO Management System > User > Profile</title>
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
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade active in">
            <form class="form-horizontal form-padding" method="post" id="signForm">
                <!--Static-->
                <div class="form-group">
                    <div class="col-md-9">
                    <label class="control-label">Name</label>
                    <input type="text" class="form-control" value="${user.engName!''}" readonly="readonly">
                    <label class="control-label">Team</label>
                    <input type="text" class="form-control" value="${user.teamName!''}" readonly="readonly">
                    <label class="control-label">Department</label>
                    <input type="text" class="form-control" value="${user.deptName!''}" readonly="readonly">
                    <label class="control-label">Email</label>
                    <input type="text" class="form-control" value="${user.email!''}" readonly="readonly">
                    </div>
                    <div class="col-md-3">
                        <a href="/agreements-withdrawal" style="margin: 0px; padding: 0px;">
                            <img class="menu-title" src="/static/img/withdrawal.png" alt="철회신청">
                        </a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
