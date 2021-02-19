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
                <li class="active">
                    <a href="/admin/authority/agreement-to-collect-and-use/internal">내부 사용자</a>
                </li>
                <li>
                    <a href="/admin/authority/agreement-to-collect-and-use/external">외부 사용자</a>
                </li>
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