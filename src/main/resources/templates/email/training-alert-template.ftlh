<#setting locale="en_US">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Notice Alert</title>
    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="http://${domain}/static/css/bootstrap.min.css" rel="stylesheet">
    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="http://${domain}/static/css/nifty.min.css" rel="stylesheet">
    <#--    <link href="${domain}/static/plugins/transactional-email-templates/templates/styles.css" media="all" rel="stylesheet" type="text/css" />-->
    <style type="text/css">
        html, body {
            background-color: #fff !important;
        }
    </style>
</head>

<body>
<div class="mar-all">
    <div class="panel panel-default panel-bordered">
        <#--        <div class="panel-heading">-->
        <#--            -->
        <#--        </div>-->
        <div class="panel-body">
            <div class="mar-btm pad-btm bord-btm">
                <h1 class="page-header">SOP Training 공지 (${diff})일 전</h1>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th>Document ID</th>
                    <th>Version</th>
                    <th>Title</th>
                    <th>Effective Date</th>
                    <th>Training Deadline</th>
                </tr>
                </thead>
                <tbody>
                <#list trainings as t>
                    <tr>
                        <td>${t.document.docId}</td>
                        <td>${t.documentVersion.version}</td>
                        <td>${t.document.title}</td>
                        <td>${t.documentVersion.effectiveDate?string('dd-MMM-yyyy')}</td>
                        <td class="text-danger">${t.userEndDate?string('dd-MMM-yyyy')}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <div class="mar-top">
                <strong>SOP Training 기한(${diff})일 전 입니다. 본 SOP의 Training Period 이내에 Training 을 완료하여 주시기 바랍니다.</strong>
            </div>
        </div>
        <div class="panel-footer text-center">
            <small>본 이메일의 발신자 주소는 발신전용으로 회신되지 않습니다.<br/>
                COPYRIGHT© KCSG. CO. LTD. ALL RIGHTS RESERVED.
            </small>
        </div>
    </div>
</div>
</body>
</html>

