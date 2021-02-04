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
    <script>
        $(document).ready(function () {
            $(document).on("click", "#activeBtn", function(){
                let isoId = $(this).data("id");
                let test = $(this).data('test');
                let testColor = test=='Y'?'success':'warning';

                let cert = $(this).data('cert');
                let certColor = cert=='Y'?'success':'warning';

                let attendee = $(this).data('attendee');
                let attendeeColor = attendee=='ALL'?'info':'purple';

                let date = $(this).data('date');

                //startDate 지점이 오늘날짜 기준 과거인 경우, 에러 문구 표시.
                let strStartDate = date.indexOf('~')!=-1?date.substr(0,date.indexOf('~')).trim():date;
                let today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
                let startDate = strToDate(strStartDate);
                let error = startDate < today?"<br><span class='text-sm' style='color:red;'>*교육 일정에 대한 재 조정이 필요합니다.</span>":"";

                bootbox.dialog({
                    title: 'Training Activate',
                    message: "<div>"
                        + "<p>아래의 정보로 Training을 활성화 합니다. 계속 하시겠습니까?</p>"
                        + "<table class='table table-striped'>"
                        + "<tr>"
                        + "<th>시험 여부 : </th>"
                        + "<td><span class='label label-" + testColor + "'>" + test + "</span></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th>수료증 여부 : </th>"
                        + "<td><span class='label label-" + certColor + "'>" + cert + "</span></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th>교육 참석자 : </th>"
                        + "<td><span class='label label-" + attendeeColor + "'>" + attendee + "</span></td>"
                        + "</tr>"
                        + "<tr>"
                        + "<th>교육 일정 : </th>"
                        + "<td>"
                        + "<span class='label label-default'>" + date + "</span>" + error
                        + "</td>"
                        + "</tr>"
                        + "</table>"
                        + "</div>",
                    size: 'middle',
                    buttons: {
                        cancel: {
                            label: "Cancel",
                            className: 'btn-danger'
                        },
                        ok: {
                            label: "OK",
                            className: 'btn-info',
                            callback: function(){
                                $("#isoId").val(isoId);
                                $("#activeForm").submit();
                            }
                        }
                    }
                });
            });
        });

        function strToDate(date_str)
        {
            var yyyyMMdd = String(date_str);
            var sYear = yyyyMMdd.substring(0,4);
            var sMonth = yyyyMMdd.substring(5,7);
            var sDate = yyyyMMdd.substring(8,10);

            //alert("sYear :"+sYear +"   sMonth :"+sMonth + "   sDate :"+sDate);
            return new Date(Number(sYear), Number(sMonth)-1, Number(sDate));
        }
    </script>
</head>
<body>

<div class="panel">
    <#--    <div class="panel-heading">-->
    <#--        <h3 class="panel-title">공지사항</h3>-->
    <#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <a class="btn btn-primary" href="javascript:goToURL('/iso-14155/new')"><i
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
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                    <th class="text-center" style="width:90px;">시험</th>
                    </@security.authorize>
                    <th class="text-center" style="width:90px;">수료증 여부</th>
                    <th class="text-center" style="width:200px;">교육 일정</th>
                    <th class="text-center" style="width:120px;">등록일</th>
                    <th class="text-center" style="width:100px;">작성자</th>
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <th class="text-center" style="width:140px">Active</th>
                    </@security.authorize>
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
<#--                                <#if iso.active?has_content && iso.active>-->
<#--                                    &nbsp;<span class="label label-purple">Active</span>-->
<#--                                </#if>-->
                            </td>
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <!--===================================================-->
                                <div class="btn-group btn-group-xs dropdown">
                                    <button class="btn btn-${(iso.quiz?has_content)?then('info', 'default')} btn-active-pink dropdown-toggle dropdown-toggle-icon" data-toggle="dropdown" type="button">
                                        Quiz <i class="dropdown-caret"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="/iso-14155/${iso.id}/quiz"><i class="fa fa-pencil"></i> Edit</a></li>
                                        <li><a href="/iso-14155/${iso.id}/quiz/upload"><i class="fa fa-upload"></i> Template Upload</a></li>
                                        <#if iso.quiz?has_content>
                                            <li class="divider"></li>
                                            <li><a href="/iso-14155/${iso.id}/quiz/test"><i class="fa fa-check-square-o"></i> Test</a></li>
                                        </#if>
                                    </ul>
                                </div>
                                <!--===================================================-->
                            </td>
                            </@security.authorize>
                            <td class="text-center" style="width:90px;"><span class="label label-${iso.certification?then('success', 'warning')}">${iso.certification?then('Y', 'N')}</span></td>
                            <td class="text-center"><span class="text-info">${iso.trainingDate}</span></td>
                            <td class="text-center"><span class="text-muted">${iso.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${iso.createdBy?default('System')}</td>
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <#if iso.training>
                                    <#if iso.active>
                                        <span class="label label-primary">Activated</span>
                                    <#else>
                                        <button id="activeBtn" data-cert="${iso.certification?then('Y', 'N')}"
                                                data-date="${iso.trainingDate}" data-attendee="${iso.attendee}"
                                                data-test="${iso.quiz?has_content?then('Y', 'N')}" data-id="${iso.id}"
                                                class="btn btn-purple btn-sm">Active
                                        </button>
                                    </#if>
                                <#else>
                                    -
                                </#if>
                            </td>
                            </@security.authorize>
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
<#--                                <#if iso.active?has_content && iso.active>-->
<#--                                    &nbsp;<span class="label label-purple">Active</span>-->
<#--                                </#if>-->
                            </td>
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <#if iso.training>
                                <!--===================================================-->
                                <div class="btn-group btn-group-xs dropdown">
                                    <button class="btn btn-${(iso.quiz?has_content)?then('info', 'default')} btn-active-pink dropdown-toggle dropdown-toggle-icon" data-toggle="dropdown" type="button">
                                        Quiz <i class="dropdown-caret"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="/iso-14155/${iso.id}/quiz"><i class="fa fa-pencil"></i> Edit</a></li>
                                        <li><a href="/iso-14155/${iso.id}/quiz/upload"><i class="fa fa-upload"></i> Template Upload</a></li>
                                        <#if iso.quiz?has_content>
                                            <li class="divider"></li>
                                            <li><a href="/iso-14155/${iso.id}/quiz/test"><i class="fa fa-check-square-o"></i> Test</a></li>
                                        </#if>
                                    </ul>
                                </div>
                                <!--===================================================-->
                                <#else>
                                    -
                                </#if>
                            </td>
                            </@security.authorize>
                            <td class="text-center" style="width:90px;"><span class="label label-${iso.certification?then('success', 'warning')}">${iso.certification?then('Y', 'N')}</span></td>
                            <td class="text-center"><span class="text-info">${iso.trainingDate}</span></td>
                            <td class="text-center"><span class="text-muted">${iso.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${iso.createdBy?default('System')}</td>
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <#if iso.training>
                                    <#if iso.active>
                                        <span class="label label-primary">Activated</span>
                                    <#else>
                                        <button id="activeBtn" data-cert="${iso.certification?then('Y', 'N')}"
                                                data-date="${iso.trainingDate}" data-attendee="${iso.attendee}"
                                                data-test="${iso.quiz?has_content?then('Y', 'N')}" data-id="${iso.id}"
                                                class="btn btn-purple btn-sm">Active
                                        </button>
                                    </#if>
                                <#else>
                                    -
                                </#if>
                            </td>
                            </@security.authorize>
                        </tr>
                    </#list>
                    <form method="post" id="activeForm" action="/iso-14155/active">
                        <input type="hidden" name="_method" value="put" />
                        <input type="hidden" id="isoId" name="isoId" />
                    </form>
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