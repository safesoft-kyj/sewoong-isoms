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
        <td class="container">
            <div class="content">
                <table class="main" width="100%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td class="alert alert-good">
                            <strong>${notice.title}</strong>
                        </td>
                    </tr>
                    <tr>
                        <td class="content-block aligncenter">
                            <div>[e-SOP] 새로운 공지사항이 등록 되었습니다. 확인해 주세요.</div>
                            <a href="http://kcsgesop.safesoft.co.kr" target="_blank">e-SOP 바로가기</a>
                        </td>
                    </tr>
                </table>
                <div class="footer">
                    <table width="100%">
                        <tr>
                            <td class="aligncenter">
                                <small>본 이메일의 발신자 주소는 발신전용으로 회신되지 않습니다.<br/>
                                    COPYRIGHT© KCSG. CO. LTD. ALL RIGHTS RESERVED.
                                </small>
                            </td>
                        </tr>
                    </table>
                </div></div>
        </td>
        <!--        <td></td>-->
    </tr>
</table>

</body>
</html>
