<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>CAUH ISO Management System > User > Profile</title>
</head>
<body>
<div class="tab-base">
    <ul class="nav nav-tabs">
        <li>
            <a href="/user/profile">Profile</a>
        </li>
        <li>
            <a href="/user/signature">Signature</a>
        </li>
        <li class="active">
            <a href="/user/profile/role">Role</a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade active in">
            <form class="form-horizontal form-padding" method="post" id="signForm">
                <!--Static-->
                <div class="form-group">
                    <label class="col-md-3 control-label">Name</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" value="${user.engName!''}" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Team</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" value="${user.teamName!''}" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Department</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" value="${user.deptName!''}" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Email</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" value="${user.email!''}" readonly="readonly">
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
