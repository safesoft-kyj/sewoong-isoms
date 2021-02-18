<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Admin > User Management > 사용자별 ROLE 관리</title>
</head>
<body>

<div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title">사용자별 ROLE 관리</h3>
        </div>
    <div class="panel-control" style="float:left;">
        <ul class="nav nav-tabs">
            <li>
                <a href="/admin/role">Role 관리</a>
            </li>
            <li class='active'>
                <a href="/admin/role/user">사용자별 Role 관리</a>
            </li>
        </ul>
    </div>
    <!--Data Table-->
    <!--===================================================-->
    <@pagination.bind userJobDescriptions/>
    <div class="panel-body" style="margin-top:45px;">
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:60px;">No.</th>
                    <th class="text-center">팀</th>
                    <th class="text-center">이름</th>
                    <th>ROLE</th>
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
                                <a href="/admin/role/user/${t.username}" class="btn btn-sm btn-info"><i class="fa fa-pencil"></i> ROLE 정보 수정</a>
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
</body>
</html>