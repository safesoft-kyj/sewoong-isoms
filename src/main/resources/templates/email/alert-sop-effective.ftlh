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
                <h1 class="page-header">${title}</h1>
            </div>
            <#if effectiveSOPs?has_content>
            <h3>Effective SOP Information</h3>
                <table class="table table-bordered">
                    <thead>
                    <tr class="bg-gray-light">
                        <th>Document ID</th>
                        <th>Version</th>
                        <th>Title</th>
                        <th>Effective Date</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list effectiveSOPs as sop>
                    <tr>
                        <td>${sop.document.docId}</td>
                        <td>${sop.version}</td>
                        <td>${sop.document.title}</td>
                        <td>${sop.effectiveDate?string('dd-MMM-yyyy')}</td>
                        <td>Effective</td>
                    </tr>
                    </#list>
                    </tbody>
                </table>
            </#if>
            <#if effectiveRDs?has_content>
            <h3>Effective RD Information</h3>
                <table class="table table-bordered">
                    <thead>
                    <tr class="bg-gray-light">
                        <th>Document ID</th>
                        <th>Version</th>
                        <th>Title</th>
                        <th>Effective Date</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list effectiveRDs as rd>
                        <tr>
                            <td>${rd.document.docId}</td>
                            <td>${rd.version}</td>
                            <td>${rd.document.title}</td>
                            <td>${rd.effectiveDate?string('dd-MMM-yyyy')}</td>
                            <td>Effective</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </#if>
        </div>
        <div class="panel-footer">
            <small>본 이메일의 발신자 주소는 발신전용으로 회신되지 않습니다.<br/>
                <!-- TODO 20210419 -->
                ${footerMsg}
            </small>
        </div>
    </div>
</div>
</body>
</html>

