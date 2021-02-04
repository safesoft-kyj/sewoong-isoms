<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Billing e.g. invoices and receipts</title>
    <link href="http://${domain}/static/plugins/transactional-email-templates/templates/styles.css" media="all" rel="stylesheet" type="text/css" />
</head>

<body itemscope itemtype="http://schema.org/EmailMessage">

<table class="body-wrap">
    <tr>
        <td></td>
        <td class="container">
            <div class="content">
                <table class="main" width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="alert alert-good">
                            <strong>SOP Deviation Report 에 대한 ${lineType} 요청이 있습니다.</strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="content-wrap aligncenter">
                            <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="content-block aligncenter">
                                        <table class="invoice">
                                            <!--                                            <tr>-->
                                            <!--                                                <td>SOP Deviation Report 에 대한 승인(or 검토) 요청이 있습니다.</td>-->
                                            <!--                                            </tr>-->
                                            <tr>
                                                <td>
                                                    <!-- 1 SOP Deviation Report -->
                                                    <table class="invoice-items" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td>Project No.</td>
                                                            <td class="alignleft"><#if form.projectNo?has_content>${form.projectNo}</#if></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Deviated SOP</td>
                                                            <td class="alignleft">${form.deviatedSOPDocument.document.title} v${form.deviatedSOPDocument.version}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Date of Occurrence</td>
                                                            <td class="alignleft"><#if form.dateOfOccurrence?has_content>${form.dateOfOccurrence?string('dd-MMM-yyyy')?upper_case}</#if></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Date of Discovery</td>
                                                            <td class="alignleft"><#if form.dateOfDiscovery?has_content>${form.dateOfDiscovery?string('dd-MMM-yyyy')?upper_case}</#if></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="content-block aligncenter">
                                                    <a href="http://${domain}" target="_blank">CAUH ISO-MS 바로가기</a>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <div class="footer">
                    <table width="100%">
                        <tr>
                            <td class="aligncenter">
                                <small>본 이메일의 발신자 주소는 발신전용으로 회신되지 않습니다.<br/>
                                    COPYRIGHT© KCSG. CO. LTD. ALL RIGHTS RESERVED.
                                </small></td>
                        </tr>
                    </table>
                </div></div>
        </td>
        <td></td>
    </tr>
</table>

</body>
</html>
