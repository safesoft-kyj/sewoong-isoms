<#import "/common/pagination.ftl" as pagination />
<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
<head>
    <title>ISO Management System > Training > Employee Training</title>
    <script>
        $(document).ready(function() {

            $("#exportBtn").click(function() {
               $("#f").attr("action",window.location.pathname).attr("method", "post").submit();
            });
            $("#searchBtn").click(function() {
               $("#f").attr("method", "get").submit();
            });
        });
    </script>
</head>
<body>


<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Training Log
            <@security.authentication property="principal.indate" var="inDate"/>
        </h3>
    </div>

    <!--Data Table-->
    <!--===================================================-->
    <@pagination.bind trainingLog/>
    <div class="panel-body">
        <div class="pad-all">

            <form name="f" id="f">
                <div class="pull-right">
                    <button type="button" class="btn btn-primary" id="exportBtn"><i class="fa fa-file-excel-o"></i> Export</button>
                </div>
                <#if deptList?has_content>
                    <#assign pDeptCode = RequestParameters['deptId']?default('')/>
                    <div class="box-inline mar-btm">
                        <#--                        Team :-->
                        <#--                        <div class="select">-->
                        <select name="deptId" id="deptCode" class="form-control">
                            <option value=""${pTeamCode?has_content?then('', ' selected')}>ALL</option>
                            <#list deptList as dept>
                                <option value="${dept.id}" ${pDeptCode?has_content?then((pDeptCode == dept.id?string)?then(' selected', ''), '')}>${dept.name}</option>
                            </#list>
                        </select>
                        <#--                        </div>-->
                    </div>
                    <script>
                        $("#deptCode").change(function() {
                            $("#teamCode").val("");

                            $("#f").attr("method", "get").submit();
                        });
                    </script>
                </#if>
                <#if teamList?has_content>
                    <#assign pTeamCode = RequestParameters['teamId']?default('')/>
                    <div class="box-inline mar-btm">
<#--                        Team :-->
<#--                        <div class="select">-->
                            <select name="teamId" id="teamCode" class="form-control">
                                <option value=""${pTeamCode?has_content?then('', ' selected')}>ALL</option>
                                <#list teamList as team>
                                    <option value="${team.id}" ${pTeamCode?has_content?then((pTeamCode == team.id?string)?then(' selected', ''), '')}>${team.name}</option>
                                </#list>
                            </select>
<#--                        </div>-->
                    </div>
                    <script>
                        $("#teamCode").change(function() {
                           $("#userList").val("");

                           $("#f").attr("method", "get").submit();
                        });
                    </script>
                </#if>
                <#if userList?has_content>
                    <#assign pUserId = RequestParameters['userId']?has_content?then(RequestParameters['userId'], '0')?number/>
                    <div class="box-inline mar-btm">
<#--                        Employee :-->
<#--                        <div class="select">-->
                            <select name="userId" id="userList" class="form-control">
                                <option value=""${(pUserId == 0)?then('', ' selected')}>ALL</option>
                                <#list userList as user>
                                    <option value="${user.id}"${(pUserId != 0)?then((pUserId == user.id)?then(' selected', ''), '')}>${user.engName?has_content?then(user.engName, user.korName)}</option>
                                </#list>
                            </select>
<#--                        </div>-->
                    </div>
                </#if>
                <div class="box-inline mar-btm">
                    <input type="text" name="docId" value="${RequestParameters['docId']?default('')}" class="form-control" placeholder="Document ID">
                </div>
                <div class="box-inline mar-btm">
                <button class="btn btn-default" style="margin-top:-5px !important;" type="button" id="searchBtn"><i class="fa fa-search"></i></button>
                </div>
            </form>
        </div>
        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center">No.</th>
                    <th class="text-center">Team/Dept</th>
                    <th class="text-center">Name</th>
                    <th class="text-center">Document ID</th>
                    <th>Title</th>
                    <th class="text-center">Version</th>
                    <th class="text-center">Effective Date</th>
                    <th class="text-center">Training period</th>
                    <th class="text-center">Progress</th>
                    <th class="text-center">Status</th>
                    <th class="text-center">Complete date</th>
<#--                    <th>#Actions</th>-->
                </tr>
                </thead>
                <tbody>
                <#if trainingLog.content?has_content>
                    <#list trainingLog.content as t>
                        <tr<#if bgClass?has_content> class="${bgClass}"</#if>>
                            <td class="text-center">${(trainingLog.totalElements - (trainingLog.size * trainingLog.number)) - t?index}</td>
                            <td class="text-center">${t.user.teamDept!''}</td>
                            <td class="text-center">${t.user.engName?has_content?then(t.user.engName, t.user.name)}</td>
                            <td class="text-center">${t.document.docId}</td>
                            <td>${t.document.title}<#if t.trainingPeriod.trainingType == 'REFRESH'> <span class="badge badge-warning">Refresh</span></#if></td>
                            <td class="text-center">${t.documentVersion.version}</td>
                            <td class="text-center">${t.documentVersion.effectiveDate?string('yyyy.MM.dd')}</td>
                            <td class="text-center">
                                <span class="text-info"> ${t.userStartDate?string('yyyy.MM.dd')}~${t.userEndDate?string('yyyy.MM.dd')}</span>
                                <#if t.isUserOutOfPeriod() && (t.trainingLog?has_content == false || t.trainingLog.completeDate?has_content == false)>
                                    <span class="text-danger"><i class="fa fa-exclamation-circle"></i> </span>
                                </#if>
                            </td>
                            <td class="text-center">
                                <#assign percent = 0/>
                                <#if t.trainingLog?has_content>
                                    <#assign percent = (t.trainingLog.progressPercent?has_content)?then(t.trainingLog.progressPercent, 0)/>
                                </#if>
                                <div class="progress"><div style="width: ${percent}%;" class="progress-bar progress-bar-info">${percent}%</div></div>
                            </td>
                            <td class="text-center">
                                <#if t.trainingLog?has_content>
                                    <span class="label label-${t.trainingLog.status.className}">${t.trainingLog.status.label}</span>
                                <#else>
                                    <span class="label label-default">Not Started</span>
                                <#--                            <#elseif t.trainingLog?has_content == false && t.isOutOfPeriod() && requirement == "mandatory">-->
                                <#--                                <a class="btn btn-purple btn-xs" href="/approval/box/requester/SOP_Deviation_Report/new?trainingPeriodId=${t.trainingPeriod.id}&sopId=${t.documentVersion.id}"><i class="fa fa-external-link"></i> Deviation Report</a>-->
                                </#if>
                            </td>
                            <td class="text-center"><#if t.trainingLog?has_content && t.trainingLog.completeDate?has_content>
                                    <span class="text-mint">${t.trainingLog.completeDate?string('yyyy.MM.dd')}</span>
                                </#if></td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="11" class="text-center">등록된 데이터가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <@pagination.default/>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
</body>
</html>
