<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>eSOP > 전자결재</title>
</head>
<body>


<div class="tab-base">
    <ul class="nav nav-tabs">
        <li<#if reportStatus?has_content == false> class="active"</#if>>
            <a href="/approval/box/${type}">전체</a>
        </li>
        <li<#if reportStatus?has_content && reportStatus == 'request'> class="active"</#if>>
            <a href="/approval/box/${type}/request">요청</a>
        </li>
        <#if type == 'requester'>
        <li<#if reportStatus?has_content && reportStatus == 'progress'> class="active"</#if>>
            <a href="/approval/box/${type}/progress">진행</a>
        </li>
        </#if>
        <li<#if reportStatus?has_content && reportStatus == 'approved'> class="active"</#if>>
            <a href="/approval/box/${type}/approved">완료</a>
        </li>
        <li<#if reportStatus?has_content && reportStatus == 'rejected'> class="active"</#if>>
            <a href="/approval/box/${type}/rejected">반려</a>
        </li>
        <#if type == 'requester'>
        <li<#if reportStatus?has_content && reportStatus == 'temp'> class="active"</#if>>
            <a href="/approval/box/${type}/temp">보관</a>
        </li>
        <li<#if reportStatus?has_content && reportStatus == 'deleted'> class="active"</#if>>
            <a href="/approval/box/${type}/deleted">삭제</a>
        </li>
        </#if>
    </ul>
    <div class="tab-content">
<div class="panel">
<#--    <div class="panel-heading">-->
<#--        <h3 class="panel-title"><#if status?has_content == false>-->
<#--                전체-->
<#--        <#else>-->
<#--                ${status.box}-->
<#--        </#if></h3>-->
<#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <@pagination.bind approvalList/>
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <#--                    <a class="btn btn-purple" href="/notice/new"><i class="pli-add"></i> Add</a>-->
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
                        <#if type == 'requester'>
<#--                        <button class="btn btn-default"><i class="pli-download-from-cloud"></i></button>-->
                        <div class="btn-group dropdown">
                            <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
<#--                                <i class="pli-gear"></i>-->
                                <i class="fa fa-pencil"></i> 기안서 작성
                                <span class="caret"></span>
                            </button>
                            <ul role="menu" class="dropdown-menu dropdown-menu-right">
                                <li><a href="${reportStatus?has_content?then('', 'requester/')}SOP_Deviation_Report/new">SOP Deviation Report</a></li>
<#--                                <li><a href="${reportStatus?has_content?then('', 'requester/')}SOP_RD_Request_Form/new">SOP & RD Request Form</a></li>-->
<#--                                <li><a href="${reportStatus?has_content?then('', 'requester/')}RD_Approval_Form/new">RD Approval Form</a></li>-->
<#--                                <li><a href="${reportStatus?has_content?then('', 'requester/')}SOP_Waiver_Approval_Form/new">SOP Waiver Request and Approval Form</a></li>-->
                                <li class="divider"></li>
                                <li><a href="${reportStatus?has_content?then('', 'requester/')}SOP_Disclosure_Request_Form/new">SOP & Digital Binder Disclosure Request Form</a></li>
<#--                                <li><a href="${reportStatus?has_content?then('', 'requester/')}SOP_RD_Retirement_Form/new">SOP & RD Retirement Form</a></li>-->
                            </ul>
                        </div>
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:80px">No.</th>
                    <th class="text-center"style="width:80px">상태</th>
                    <th>기안종류</th>
                    <th class="text-center" style="width:140px">기안자</th>
                    <th class="text-center" style="width:120px;">기안일</th>
<#--                    <th>완료일</th>-->

                    <th style="width:100px">#Actions</th>
                </tr>
                </thead>
                <tbody>
                <#if approvalList.content?has_content>
                <#list approvalList.content as t>
                    <tr>
                        <td class="text-center">${(approvalList.totalElements - (approvalList.size * approvalList.number)) - t?index}</td>
                        <td class="text-center">
                            <span class="label label-table label-${t.status.className}">${t.status.label}</span>
                        </td>
                        <td>${t.type.label}</td>
                        <td class="text-center">${t.createdBy}</td>
                        <td class="text-center"><span class="text-muted"> ${t.createdDate?string('yyyy-MM-dd')}</span></td>

                        <td>
                            <#if type == 'requester' && (t.status == 'temp' || t.status == 'request') && (reportStatus?has_content == false || reportStatus != 'deleted')>
                                <#if reportStatus?has_content == true && reportStatus != 'temp'>
                            <a class="btn btn-default btn-sm" href="/approval/box/${type}/${reportStatus?has_content?then(reportStatus.name()+'/', '')}${t.type.name()}/${t.id}/edit"><i class="fa fa-pencil"></i></a>
                                <#else>
                                    <a class="btn btn-default btn-sm" href="/approval/box/${type}/${t.type.name()}/${t.id}/edit"><i class="fa fa-pencil"></i></a>
                                </#if>
                            </#if>
                            <#if t.status != 'temp'>
                            <a class="btn btn-default btn-sm" href="/approval/box/${type}/${reportStatus?has_content?then(reportStatus.name()+'/', '')}${t.id}/v"><i class="fa fa-search"></i></a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                <#else>
                    <tr>
                        <td colspan="6" class="text-center">등록된 데이터가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <@pagination.default/>
    </div>
    <!--===================================================-->
    <!--End Data Table-->
</div><!-- end panel-->
</div><!-- end tab-content -->
</div><!--end tab-base -->
</body>
</html>