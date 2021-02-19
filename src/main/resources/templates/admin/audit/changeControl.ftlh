<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Admin > Change Control</title>

    <!--Bootstrap Table [ OPTIONAL ]-->
    <link href="/static/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <script src="plugins/bootstrap-table/bootstrap-table.min.js"></script>

</head>
<body>

<div class="panel">
    <div class="panel-heading">
        <div class="panel-control" style="float:left;">
            <ul class="nav nav-tabs">
                <#if audit?has_content>
                    <li class="${(audit == 'account')?then('active', '')}">
                        <a href="/admin/change-control/account">User Profile</a>
                    </li>
                    <li class="${(audit == 'notice')?then('active', '')}">
                        <a href="/admin/change-control/notice">공지사항</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
    <!--Pagination-->
    <!--===================================================-->
    <div class="panel-body">
        <#include "form/" + viewName + ".ftlh"/>
    </div>
    <!--===================================================-->
    <!--End Pagination-->

</div>
</body>
</html>