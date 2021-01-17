<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Chung Ang University Hospital - ISO Management System</title>

    <!-- Bootstrap DatePicker  -->
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#withdrawalDate").datepicker({format:'yyyy-mm-dd', autoclose:true, clearBtn:true, todayBtn: "linked", todayHighlight: true});

        });
    </script>
</head>
<body>

<div class="cls-content">
    <div class="cls-content-lg panel panel-primary">
        <div class="panel-heading">
            <span class="text-bold" style="font-size: 28px;">철회 신청</span>
        </div>
        <div class="panel-body">
            <div class="mar-ver pad-btm">
                <span style="font-size: 14px;">철회 신청을 위한 정보를 입력해주세요.</span><br>
                <span style="font-size: 12px; color:red;">* 여기서 입력 된 e-mail 정보는 저장되지 않고 철회 신청을 위한 목적으로 사용 후 폐기됩니다. </span>
            </div>
            <hr>
<#--            <div class="pad-btm mar-btm">-->
<#--                <img alt="Profile Picture" class="img-lg img-circle img-border-light" src="img/profile-photos/1.png">-->
<#--            </div>-->
            <form id="withdrawalForm" method="post" class="text-left">
                <div class="form-group">
                    <label class="control-label" for="email">E-mail</label>
                    <input type="email" name="email" id="email" class="form-control" />
                </div>
                <div class="form-group">
                    <label class="control-label" for="withdrawalDate">철회 날짜</label>
                    <input type="text" name="withdrawalDate" id="withdrawalDate" class="form-control" />
                </div>
                <div class="form-group text-right">
                    <div class="col-lg-6">
                        <button class="btn btn-block btn-lg btn-primary" type="submit"><i class="pli-yes"></i> 철회 신청</button>
                    </div>
                    <div class="col-lg-6">
                        <a href="/withdrawal/cancel" class="btn btn-block btn-lg btn-warning"><i class="pli-close"></i> 취소</a>
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
