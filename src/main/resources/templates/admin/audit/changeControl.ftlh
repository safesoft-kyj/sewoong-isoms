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
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <#include "form/" + viewName + ".ftlh"/>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
</body>
</html>