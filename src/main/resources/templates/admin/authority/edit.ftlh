<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > Authority Management > 사용자 관리</title>
    <script src="/static/js/jquery.mask.min.js"></script>
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">
    <script src="/static/plugins/select2/js/select2.min.js"></script>

    <script>
        $(document).ready(function() {
            $("#birthDate").datepicker({format:'yyyy-mm-dd', autoclose:true, clearBtn:true, todayBtn: "linked", todayHighlight: true});

            $("#roles").select2({allowClear: true});
        });
    </script>
</head>
<body>
<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">내부 사용자 수정</h3>
    </div>
    <form class="form-horizontal" method="post" enctype="multipart/form-data" id="documentForm">
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3 control-label">ID</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.username" "class='form-control' readonly"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">이름</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.name" "class='form-control' readonly"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">부서</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.orgDepart" accountDeaprtment "class='form-control'"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">팀</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.orgTeam" accountTeam "class='form-control'"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">생년월일</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.birthDate" birthDate "class='form-control'"/>
                    <@spring.showErrors "" "color:red;"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">연락처</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.phone" phone "class='form-control'"/>
                    <@spring.showErrors "" "color:red;"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label">email</label>
                <div class="col-sm-9">
                    <@spring.formInput "account.email" email "class='form-control'"/>
                    <@spring.showErrors "" "color:red;"/>
                </div>
            </div>
<#--            <div class="form-group">-->
<#--                <label class="col-sm-3 control-label">ROLE</label>-->
<#--                <div class="col-sm-9">-->
<#--                    <@spring.formMultiSelect "roles" "class='form-control'"/>-->
<#--                    <@spring.showErrors "" "color:red;"/>-->
<#--                </div>-->
<#--            </div>-->
        </div>
        <div class="panel-footer text-right">
            <a class="btn btn-link" href="/admin/authority/users"><i class="fa fa-times"></i> 취소</a>
            <button class="btn btn-primary" type="submit" id="submitBtn" data-save-text="Saving.." data-loading-text="정보 수정 중.. 잠시만 기다려 주세요."><i class="fa fa-check"></i>수정</button>
        </div>
    </form>
    <!--===================================================-->
    <!--End Horizontal Form-->
</div>
</body>
</html>
