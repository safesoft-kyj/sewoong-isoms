<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>eSOP > User > Profile</title>
</head>
<body>
<div class="tab-base">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="/user/profile">Profile</a>
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
                        <input type="text" class="form-control" value="${user.orgTeam!''}" readonly="readonly">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-3 control-label">Department</label>
                    <div class="col-md-9">
                        <input type="text" class="form-control" value="${user.orgDepart!''}" readonly="readonly">
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
