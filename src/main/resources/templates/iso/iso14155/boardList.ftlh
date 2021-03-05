<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title> ISO Management System > ISO > ISO-14155</title>
    <#--    <script src="/static/plugins/jstree/jstree.min.js"></script>-->
    <#--    <link href="/static/plugins/jstree/themes/default/style.min.css" rel="stylesheet">-->

    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="/static/plugins/bootbox/bootbox.min.js"></script>

    <style>
        .modal-dialog.modal-fullsize {
            width: 80%;
            height: 550px;
            margin: 3px;
            padding: 0;
        }

        .modal-content.modal-fullsize {
            height: auto;
            min-height: 550px;
            border-radius: 0;
        }
    </style>
</head>
<body>

<div class="tab-base">
    <ul class="nav nav-tabs">
        <li class="active">
            <a href="/iso-14155/board">Board</a>
        </li>
        <li>
            <a href="/iso-14155/training">Training</a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <a class="btn btn-primary" href="javascript:goToURL('/iso-14155/new?type=board')"><i
                                    class="pli-pencil"></i> ISO-14155 등록</a>
                    </@security.authorize>
                    <#--                    <button class="btn btn-default"><i class="pli-printer"></i></button>-->
                    <#--                    <div class="btn-group">-->
                    <#--                        <button class="btn btn-default"><i class="pli-exclamation"></i></button>-->
                    <#--                        <button class="btn btn-default"><i class="pli-recycling"></i></button>-->
                    <#--                    </div>-->
                </div>
                <div class="col-sm-6 table-toolbar-right">
                    <div class="form-group">
                        <#--                        <input id="demo-input-search2" type="text" placeholder="Search" class="form-control" autocomplete="off">-->
                    </div>
                    <div class="btn-group">
                        <#--                        <button class="btn btn-default"><i class="fa fa-search fa-lg"></i></button>-->
                        <#--                        <div class="btn-group dropdown">-->
                        <#--                            <button data-toggle="dropdown" class="btn btn-default dropdown-toggle">-->
                        <#--                                <i class="pli-list-view"></i>-->
                        <#--                                <span class="caret"></span>-->
                        <#--                            </button>-->
                        <#--                            <ul role="menu" class="dropdown-menu dropdown-menu-right">-->
                        <#--                                <#list 1..6 as i>-->
                        <#--                                    <#assign viewSize = i * 5/>-->
                        <#--                                <li><a href="?view=${viewSize}">${viewSize}</a></li>-->
                        <#--                                </#list>-->
                        <#--                            </ul>-->
                        <#--                        </div>-->
                    </div>
                </div>
            </div>
        </div>
        <@pagination.bind isoList/>
<#--        <div class="table-responsive">-->
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th class="text-center" style="width:90px;">Type</th>
                    <th>Title</th>
                    <th class="text-center" style="width:160px;">등록일</th>
                    <th class="text-center" style="width:140px;">작성자</th>
                </tr>
                </thead>
                <tbody>
                <#if topISOList?has_content>
                    <#list topISOList as iso>
                        <tr class="bg-gray-light">
                            <td class="text-center"><i class="fa fa-exclamation-circle text-warning"></i></td>
                            <td class="text-center"><span class="label label-${iso.training?then('info','mint')}">${iso.training?then('Training','Board')}</span></td>
                            <td>
                                <a class="btn-link" href="/iso-14155/${iso.id}">
                                    ${iso.title}
                                </a>
                            </td>
                            <td class="text-center"><span class="text-muted">${iso.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${iso.createdBy?default('System')}</td>
                        </tr>
                    </#list>
                </#if>
                <#if isoList.content?has_content>
                    <#list isoList.content as iso>
                        <tr>
                            <td class="text-center">${(isoList.totalElements - (isoList.size * isoList.number)) - iso?index}</td>
                            <td class="text-center"><span class="label label-${iso.training?then('info','mint')}">${iso.training?then('Training','Board')}</span></td>
                            <td>
                                <a class="btn-link" href="/iso-14155/${iso.id}">${iso.title}</a>
                            </td>
                            <td class="text-center"><span class="text-muted">${iso.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${iso.createdBy?default('System')}</td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="5" class="text-center">등록된 ISO-14155가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
<#--        </div>-->
        <@pagination.default/>

        <#--        <nav style="float:left;">-->
        <#---->
        <#--        </nav>-->
        <#--        <@pagination.counter />-->
    </div>
    <!--===================================================-->
    <!--End Data Table-->
</div>
</body>
</html>