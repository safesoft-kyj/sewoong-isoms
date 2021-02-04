<#setting locale="en_US">
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
        <td class="container" style="width:800px !important;">
            <div class="content">
                <table class="main" width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="alert alert-good">
                            <strong>[CAUH] ISO Management System Access Code</strong>
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
                                                    <table class="invoice-items" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td>Access Code :</td>
                                                            <td class="alignleft">${code}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>URL : </td>
                                                            <td class="alignleft"><a href="http://${domain}" target="_blank">http://${domain}</a></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="content-block">
<#--                                                    상세 내용을 확인하여 주십시오.-->
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
                                <small>This email has sent from CAUH ISO Management System.
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
