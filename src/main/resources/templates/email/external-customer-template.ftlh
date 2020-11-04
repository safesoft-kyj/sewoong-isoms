<#setting locale="en_US">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Billing e.g. invoices and receipts</title>
    <link href="http://kcsgesop.safesoft.co.kr/static/plugins/transactional-email-templates/templates/styles.css" media="all" rel="stylesheet" type="text/css" />
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
                            <strong>[KCSG] e-SOP Invitation</strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="content-wrap aligncenter">
                            <table width="100%" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td class="content-block aligncenter">
                                        <table class="invoice">
                                            <tr>
                                            <td>Welcome to KCSG e-SOP System. You have been invited to access the e-SOP system.<br/>
                                                In order to use the e-SOP system, you will need the following software;<br/>
                                                - Microsoft Internet Explorer 9.0 and above OR Mozilla Firefox and Google Chrome.<br/>
                                            </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <table class="invoice-items" cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td>Name</td>
                                                            <td class="alignleft">${customer.name}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Email</td>
                                                            <td class="alignleft">${customer.email}</td>
                                                        </tr>
                                                        <tr>
                                                            <td>Date of Period to access e-SOP</td>
                                                            <td class="alignleft">
                                                                ${form.requestStartDate?string('dd-MMM-yyyy')} ~ ${form.requestEndDate?string('dd-MMM-yyyy')}
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>URL</td>
                                                            <td class="alignleft">
                                                                <a href="http://kcsgesop.safesoft.co.kr" target="_blank">http://kcsgesop.safesoft.co.kr</a>
                                                            </td>
                                                        </tr>
                                                    </table>
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
                                <small>본 이메일의 발신자 주소는 발신전용으로 회신 되지 않습니다.<br/>
                                    Please do not reply to this email.<br/>
                                    이 전자우편은 지정수신인에게만 전송될 의도로 보내진 것입니다. 이에 포함된 내용은 보안을 유지하여야 하며 임의로 공개해서는 안되는 정보 및 법률상 공개가 금지된 정보가 들어 있을 수 있습니다. 본 문서에 포함된 정보의 전부 또는 일부를 무단으로 제3자에게 공개, 배포, 복사 또는 사용하는 것은 엄격히 금지됩니다. 본 메일이 잘못 전송된 경우, 발신인 또는 당사에 알려주시고, 본 메일을 즉시 삭제하여 주시기 바랍니다.<br/>
                                    The above message is intended solely for the named addressee and may contain information that is privileged, confidential or otherwise protected under applicable law. Any unauthorized dissemination, distribution, copying or use of the information contained in this communication is strictly prohibited. If you have received this communication in error, please notify the sender by email and erase this communication immediately.

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

