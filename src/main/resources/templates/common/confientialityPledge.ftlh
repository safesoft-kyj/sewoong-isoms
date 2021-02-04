<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<#setting locale="en_US">
<html>
<head>
    <title>ISO Management System</title>
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
                signaturePad.fromDataURL($("#base64signature").val());
            }
            var saveButton = document.getElementById('save');
            var cancelButton = document.getElementById('clear');

            saveButton.addEventListener('click', function (event) {
                if(signaturePad.isEmpty()) {
                    event.preventDefault();
                    alert('Please sign.');
                } else {
                    var data = signaturePad.toDataURL('image/png');

                    $("#base64signature").val(data);
                    // $("#agreeForm").submit();
                    $("#sign").html("<img src='"+data+"' width='50px'/>")
                    $("#sign-modal").modal('hide');
                }
            });

            cancelButton.addEventListener('click', function (event) {
                signaturePad.clear();
            });

            $("#agreeForm").submit(function(e) {
                if(!$("#chk1").is(":checked") ||
                    !$("#chk2").is(":checked") ||
                    !$("#chk3").is(":checked") ||
                    !$("#chk4").is(":checked")) {
                    e.preventDefault();
                    alert('Please tick the all boxes when you have read and understand the statement below');
                } else if(!$("#base64signature").val()) {
                    e.preventDefault();
                    alert('Please sign.');
                }
            });
        });
    </script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="/static/js/bootstrap.min.js"></script>
</head>
<body>


<div class="cls-content">
    <div class="cls-content-lg panel" style="width:750px !important;">
        <div class="panel-body text-left">
            <div class="mar-btm row">
                <div class="col-xs-4"><img src="https://ch.caumc.or.kr/images/main_re_logo.gif" height="45px"/></div>
                <div class="col-xs-8 text-right pad-top"><span class="text-bold" style="font-size:25px;">&nbsp;&nbsp;Confidentiality Pledge</span></div>
            </div>
            <hr/>
            <div class="panel mar-top mar-btm">
                <div class="panel-body bg-gray" style="height:400px; overflow-y: scroll">
                    <div class="mar-btm text-center">
                        <h3>Pledge of Confidentiality</h3>
                    </div>
                    <div class="mar-top mar-btm">
                        I <span class="text-info">${user.engName!user.name}</span>, through my involvement with and work on ISO Management System will have access to data<br>
                        which contains confidential information that respondents generally perceive as personal and private.<br>
                        I understand that access to this confidential information and data carries with it responsibility to guard<br>
                        against unauthorized use and to abide by the data security plan.<br><br>

                        To treat information as confidential means to not divulge it or make it accessible<br>
                        to anyone who is not a project member. Such a disclosure would violate the confidentiality promised<br>
                        to participants and would violate University ethics policies.<br>

                        I agree to fulfill my responsibilities on this project in accordance with the following<br><br>

                        1.I agree to not permit non-project personnel access to the data, either electronically,<br>
                        &nbsp;&nbsp;&nbsp;in hard copy or orally.<br><br>

                        2.I agree to not attempt to identify individuals, families, households, schools, or institutions<br>
                        &nbsp;&nbsp;&nbsp;Select only those categories relevant to the nature of the ISO 14155<br>
                        &nbsp;&nbsp;&nbsp;except in those cases where it is necessary in accordance with my role on the research project.<br><br>

                        3.I agree that in the event I inadvertently uncover the identity of an individual, family, household,<br>
                        &nbsp;&nbsp;&nbsp;school or institution, I will maintain the highest level of confidentiality of this information,<br>
                        &nbsp;&nbsp;&nbsp;make no use of the knowledge and inform the study’s Principal Investigator.
                    </div>
<#--                    <div class="mar-btm">The Discloser (KCSG) intends to disclose proprietary information and data <span class="text-bold"><’The Confidential Information’></span> relating to KCSG Standard Operating Procedures (SOP) to the Recipient for the purpose as below (<span style="font-style: italic">Please tick where applicable</span>).</div>-->
<#--                    <div class="text-bold">Obligation of Recipient; </div>-->
<#--                    <div style="font-style: italic;" class="mar-btm"><u>* Please tick the all boxes when you have read and understand the statement below.</u></div>-->
                </div>
            </div>
        </div>
        <div class="panel-footer text-right">
            <form method="post" id="agreeForm">
                <input type="hidden" id="base64signature" name="base64signature" value="${signatureData!''}"/>
                <a class="btn btn-warning" href="/logout"><i class="fa fa-times"></i> Cancel</a>
                <button class="btn btn-info" type="submit"><i class="fa fa-check"></i> Submit</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
