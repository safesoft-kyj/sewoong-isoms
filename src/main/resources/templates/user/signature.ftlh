<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>eSOP > User > Signature</title>
    <script src="/static/js/signature_pad.min.js"></script>
    <script>
        $(document).ready(function() {
            var signaturePad = new SignaturePad(document.getElementById('signature-pad'), {
                backgroundColor: 'rgba(255, 255, 255, 0)',
                penColor: 'rgb(0, 0, 0)',
                minWidth:3,
                maxWidth:7
            });

            if($("#base64signature").val()) {
                // console.log($("#base64signature").val());
                $("#wrapper").append("<img src='" + $("#base64signature").val()+"' width='700' height='450' style=\"border:1px solid #eeeeee\" id='preview'/>");
                // signaturePad.fromDataURL($("#base64signature").val());
                $("#signature-pad").hide();
                $("#clear").hide();
                $("#save").hide();
                $("#edit").show();
            } else {
                $("#edit").hide();
            }

            $("#edit").click(function() {
                $("#preview").hide();
                $("#edit").hide();
                $("#signature-pad").show();
                $("#clear").show();
                $("#save").show();
            });

            var saveButton = document.getElementById('save');
            var cancelButton = document.getElementById('clear');

            saveButton.addEventListener('click', function (event) {
                if(signaturePad.isEmpty()) {
                    event.preventDefault();
                    alert('서명을 등록해 주세요.');
                } else {
                    var data = signaturePad.toDataURL('image/png');

                    $("#base64signature").val(data);
                    $("#signForm").submit();
                }
            });

            cancelButton.addEventListener('click', function (event) {
                signaturePad.clear();
            });
        });
    </script>
</head>
<body>
<div class="tab-base">
    <ul class="nav nav-tabs">
        <li>
            <a href="/user/profile">Profile</a>
        </li>
        <li class="active">
            <a href="/user/signature">Signature</a>
        </li>
        <li>
            <a href="/user/profile/role-changed">Role</a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane fade active in">
<#--            <#if user.base64signature?has_content == false>-->
<#--            <div class="alert alert-danger">-->
<#--                <strong>알림!</strong> 서명을 등록해 주세요.-->
<#--            </div>-->
<#--            </#if>-->
            <form class="form-horizontal form-padding" method="post" id="signForm">
                <div class="form-group">
                    <label class="col-md-3 control-label">Signature
                    </label>
                    <div class="col-md-9">
                        <input type="hidden" id="base64signature" name="base64signature" value="${signature.base64signature!''}"/>
                        <div class="wrapper" id="wrapper">
                            <canvas id="signature-pad" class="signature-pad" width=700 height=450 style="border:1px solid #eeeeee"></canvas>
                        </div>

                        <div>
                            <button class="btn btn-link" type="button" id="edit"><i class="fa fa-pencil"></i> Edit</button>
                            <button class="btn btn-default" type="button" id="clear"><i class="fa fa-eraser"></i> Clear</button>
                            <button class="btn btn-success" type="button" id="save"><i class="fa fa-floppy-o"></i> Save</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>