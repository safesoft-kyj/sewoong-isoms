<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Admin > User Management > 사용자별 직무 관리</title>
</head>
<body>
<div class="tab-base">
    <ul class="nav nav-tabs">
        <li>
            <a href="/admin/jobDescription">직무 관리</a>
        </li>
        <li class="active">
            <a href="/admin/jobDescription/user">사용자별 직무 관리</a>
        </li>
    </ul>
    <div class="tab-content">
<div class="panel">
    <#--    <div class="panel-heading">-->
    <#--        <h3 class="panel-title">사용자별 직무 관리</h3>-->
    <#--    </div>-->
    <#--    <div class="pad-btm form-inline">-->
    <#--        <div class="row">-->
    <#--            <div class="col-sm-6 table-toolbar-left">-->
    <#--            </div>-->
    <#--            <div class="col-sm-6 table-toolbar-right">-->
    <#--                <div class="btn-group">-->
    <#--                    <a class="btn btn-primary" href="/admin/training/refresh-training/new"><i class="fa fa-plus"></i> Add Refresh Training</a>-->
    <#--                </div>-->
    <#--            </div>-->
    <#--        </div>-->
    <#--    </div>-->
    <!--Data Table-->
    <!--===================================================-->
    <@pagination.bind userJobDescriptions/>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:60px;">No.</th>
                    <th class="text-center">팀</th>
                    <th class="text-center">이름</th>
                    <th>직무</th>
                    <th class="text-center">#actions</th>
                </tr>
                </thead>
                <tbody>
                <#if userJobDescriptions.content?has_content>
                    <#list userJobDescriptions.content as t>
                        <tr>
                            <td class="text-center">${(userJobDescriptions.totalElements - (userJobDescriptions.size * userJobDescriptions.number)) - t?index}</td>
                            <td class="text-center">${t.teamName?default('')}</td>
                            <td class="text-center">${t.name?default('')}</td>
                            <td><#if t.userJobDescriptions?has_content>
                                    <#list t.userJobDescriptions as jd>
                                        <div class="mar-btm">
                                        <span class="label label-${jd.jobDescription.enabled?then('success', 'default')}">${jd.jobDescription.shortName}(<i class="fa fa-calendar"></i> ${jd.assignDate?string('yyyy-MM-dd')})
<#--(${jd.jobDescription.title})-->
                                        </span>

                                            <#--                                        <#if  == false> - <span class="label label-warning">Inactive</span></#if>-->
                                        </div>
                                    </#list>
                                <#else><small class="text-danger"><i class="fa fa-exclamation-circle"></i> 직무 정보가 없습니다.</small></#if>
                            </td>
                            <td>
                                <a href="/admin/user/jobDescription/${t.username}" class="btn btn-sm btn-info"><i class="fa fa-pencil"></i> 직무 정보 수정</a>
                            </td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="5" class="text-center">등록된 데이터가 없습니다.</td>
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
</div><!-- end tab-based -->
</body>
</html>