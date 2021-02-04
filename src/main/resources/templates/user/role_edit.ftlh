<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > User > Profile</title>

    <!-- select2 -->
    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">
    <!-- select2 -->
    <script src="/static/plugins/select2/js/select2.min.js"></script>

    <!-- Bootstrap DatePicker -->
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <!-- Bootstrap DatePicker -->
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>

    <style></style>

    <script>
        $(document).ready(function(){
            //JD select2 Setting
            let jds = "${commaJdIds?has_content?then("${commaJdIds}", '')}";
            let JDList = String(jds).indexOf(",")!=-1?jds.split(','):jds;
            $('#jdIds').select2({width: '100%'});
            $("#jdIds").val(JDList).trigger("change");

            $('#jdIds').change(function(){
                let nextRoleList = [];
                $("#jdIds option:selected").each(function(){
                    nextRoleList.push($(this).text());
                });
                let nextRole = nextRoleList.join(",");

                $("#nextJobDescription").val(nextRole);
            })

            $('#assignDate').datepicker({format:'yyyy-mm-dd', autoclose:true, clearBtn:true, todayBtn: "linked", todayHighlight: true, endDate: new Date()});
        });
    </script>

</head>
<body>
<div class="panel">
    <form id="f" id="roleForm" method="post">
        <div class="panel-body">
            <div class="form-group">
                <div class="row">
                    <div class="col-sm-5">
                        <label class="control-label">Current Roles</label>
                        <@spring.formInput "userJobDescriptionChangeLog.prevJobDescription" "class='form-control' readonly" />
                        <@spring.showErrors "" "color:red;"/>
                    </div>
                    <div class="col-sm-1 text-center">
                        <span class="text-3x"><i class="pli-right-3"></i></span>
                    </div>
                    <div class="col-sm-5">
                        <label class="control-label">Request Roles</label>
                        <@spring.formInput "userJobDescriptionChangeLog.nextJobDescription" "class='form-control' readonly" />
                        <@spring.showErrors "" "color:red;"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label">Roles</label>
                <@spring.formMultiSelect "userJobDescriptionChangeLog.jdIds" jobDescriptionMap "class='form-control'" />
                <@spring.showErrors "" "color:red;"/>
            </div>
            <div class="form-group">
                <label class="control-label">Assign Date</label>
                <@spring.formInput "userJobDescriptionChangeLog.assignDate" "class='form-control'"/>
                <@spring.showErrors "" "color:red;"/>
            </div>
            <div class="form-group">
                <label class="control-label">Reason</label>
                <@spring.formTextarea "userJobDescriptionChangeLog.reason" "class='form-control' rows='4'"/>
                <@spring.showErrors "" "color:red;"/>
            </div>
        </div>
        <div class="panel-footer">
            <button class="btn btn-primary"> ROLE 변경 신청</button>
            <a class="btn btn-danger" href="/user/profile/role">취소</a>
        </div>
    </form>
</div>
</body>
</html>
