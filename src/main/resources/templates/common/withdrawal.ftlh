<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Chung Ang University Hospital - ISO Management System</title>

    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="/static/js/bootstrap.min.js"></script>
    <!--Nifty Admin [ RECOMMENDED ]-->
    <script src="/static/js/nifty.min.js"></script>
    <!--Bootstrap Validator -->
    <script src="/static/plugins/bootstrap-validator/bootstrapValidator.js"></script>

    <script>
        $(document).ready(function() {
            //초기 화면 전환시 세팅
            departmentSelect($("#deptSelect").val());

            $("#deptSelect").change(function(){
                departmentSelect($(this).val());
            })

            $("#teamSelect").change(function(){
                let res = $(this).val();

                if(res == 'department') {
                    $("#department").val($("#deptSelect").val());
                }else {
                    $("#department").val(res);
                }
            })
        });

        function departmentSelect(value) {
            $("#department").val(value);

            $.ajax({
                url: "/signUp/ajax/department",
                method: 'post',
                data: {id: value},
                success: function (data) {
                    let teamSelect = $("#teamSelect");

                    if(data != '') {
                        teamSelect.attr("disabled", false);
                        teamSelect.html(data);
                    } else {
                        teamSelect.attr("disabled", true);
                        teamSelect.html(data);
                    }
                }
            });
        }
    </script>
</head>
<body>

<div class="cls-content">
    <div class="cls-content-lg panel panel-primary">
        <div class="panel-heading">
            <span class="text-bold" style="font-size: 28px;">사용자 확인</span>
        </div>
        <div class="panel-body">
            <div class="mar-ver pad-btm">
                <span style="font-size: 14px;">철회 신청을 위한 사용자 정보를 입력해 주세요.</span>
            </div>
            <hr>
<#--            <div class="pad-btm mar-btm">-->
<#--                <img alt="Profile Picture" class="img-lg img-circle img-border-light" src="img/profile-photos/1.png">-->
<#--            </div>-->
            <form id="withdrawalForm" method="post" class="text-left">
                <div class="form-group">
                    <label class="control-label" for="email">E-mail </label>
                    <@spring.formInput "user.email" "class='form-control'" "email"/>
                    <@spring.showErrors "" "color:red;"/>
                </div>
                <div class="form-group">
                    <label class="control-label" for="department">부서 / 팀 </label>
                    <@spring.formHiddenInput "user.department" "class='form-control'"/>
                    <div class="row">
                        <div class="col-lg-6">
                            <select id="deptSelect" class="form-control">
                                <#list departments  as d>
                                    <option value="${d.id}">${d.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-lg-6">
                            <select id="teamSelect" class="form-control" disabled></select>
                        </div>
                    </div>
                    <@spring.showErrors "" "color:red;"/>
                </div>
                <div class="form-group">
                    <label class="control-label" for="name">이름 </label>
                    <@spring.formInput "user.name" "class='form-control'" />
                    <@spring.showErrors "" "color:red;"/>
                </div>
<#--                <div class="form-group">-->
<#--                    <label class="control-label" for="withdrawalDate">철회 날짜 </label>-->
<#--                    <input class="form-control" type="text" id="withdrawalDate" name="name" />-->
<#--                </div>-->
                <div class="form-group text-right">
                    <div class="col-lg-6">
                        <button class="btn btn-block btn-lg btn-primary" type="submit"><i class="pli-yes"></i> 계속</button>
                    </div>
                    <div class="col-lg-6">
                        <a href="/" class="btn btn-block btn-lg btn-warning"><i class="pli-close"></i> 취소</a>
                    </div>
                </div>
            </form>
<#--            <div class="pad-ver">-->
<#--                <a href="pages-login.html" class="btn-link mar-rgt text-bold">Sign in using different account</a>-->
<#--            </div>-->
        </div>
    </div>
</div>

</body>
</html>
