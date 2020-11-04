<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>e-SOP > SOP > Training Log</title>

</head>
<body>
<@pagination.bind trainingLog/>
<div class="panel panel-default">

    <!--Nav Tabs-->
    <#--    <ul class="nav nav-tabs">-->
    <#--        <#list user.jobDescriptions as jd>-->
    <#--        <li<#if jd?is_first> class="active"</#if>>-->
    <#--            <a data-toggle="tab" href="#jd-tab-${jd?index}">${jd.jobDescription.shortName}</a>-->
    <#--        </li>-->
    <#--        </#list>-->
    <#--    </ul>-->

    <!--Tabs Content-->
    <div class="panel-body">
        <div class="pad-all">
            <form name="f">
                <#if teamList?has_content>
                    <#assign pTeamCode = RequestParameters['teamCode']?default('')/>
            <div class="box-inline mar-btm pad-rgt">
                Team :
                <div class="select">
                    <select name="teamCode">
                        <option value=""${pTeamCode?has_content?then('', ' selected')}>ALL</option>
                        <#list teamList as team>
                        <option value="${team['teamCode']}"${pTeamCode?has_content?then((pTeamCode == team['teamCode'])?then(' selected', ''), '')}>${team['teamName']}</option>
                        </#list>
                    </select>
                </div>
            </div>
                </#if>
                <#if userList?has_content>
                    <#assign pUserId = RequestParameters['userId']?default('0')?number/>
            <div class="box-inline mar-btm">
                Employee :
                <div class="select">
                    <select name="userId">
                        <option value=""${(pUserId == 0)?then('', ' selected')}>ALL</option>
                        <#list userList as user>
                            <option value="${user.id}"${(pUserId != 0)?then((pUserId == user.id)?then(' selected', ''), '')}>${user.engName?has_content?then(user.engName, user.name)}</option>
                        </#list>
                    </select>
                </div>
            </div>
                </#if>
            <button class="btn btn-default">Filter</button>
            </form>
        </div>

        <div class="table-responsive">
            <table class="table table-bordered table-hover">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:70px;">No.</th>
                    <th class="text-center">Department/Team</th>
                    <th class="text-center">Name</th>
                    <th class="text-center">Completion Date<br/><span class="text-muted text-sm"><em>(DD-MMM-YYYY)</em></span> </th>
                    <th>Description of Training Course</th>
                    <th class="text-center">Training<br/>Hr.</th>
                    <th class="text-center">Organization<br/><span class="text-muted text-sm ital"><em>(Academy, Self-training, etc.)</em></span> </th>
                </tr>
                </thead>
                <tbody>
                <#if trainingLog.content?has_content>
                    <#list trainingLog.content as t>
                        <tr>
                            <td class="text-center">${(trainingLog.totalElements - (trainingLog.size * trainingLog.number)) - t?index}</td>
                            <td class="text-center">${t.user.orgDepart!''}/${t.user.orgTeam!''}</td>
                            <td class="text-center">${t.user.engName?default(t.user.name)}</td>
                            <td class="text-center">${t.completeDate?string('dd-MMM-yyyy')}</td>
                            <td>${t.trainingCourse}</td>
                            <td class="text-center">${t.hour}</td>
                            <td class="text-center">${t.organization}</td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="7" class="text-center">
                            Training 이력이 존재하지 않습니다.
                        </td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <@pagination.default/>
    </div>
</div>
</body>
</html>
