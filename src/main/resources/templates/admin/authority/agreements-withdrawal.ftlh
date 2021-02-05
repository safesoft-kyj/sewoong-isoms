<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > 철회 신청 내역</title>
</head>
<body>

<div class="panel">
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <@pagination.bind withdrawal/>
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
<#--                    <a class="btn btn-purple" href="/admin/authority/agreement-to-collect-and-use/external"><i class="pli-door"></i> 외부 사용자</a>-->
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
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:80px">No.</th>
                    <th class="text-center">부서</th>
                    <th class="text-center">팀</th>
                    <th class="text-center">이름</th>
                    <th class="text-center">Email</th>
                    <th class="text-center">ROLE</th>
                    <th class="text-center">Withdrawal Date</th>
                    <th class="text-center">Status</th>
<#--                    <th style="width:80px;">#Actions</th>-->
                </tr>
                </thead>
                <tbody>
                <#if withdrawal.content?has_content>
                    <#list withdrawal.content as t>
                        <tr>
                            <td class="text-center">${(withdrawal.totalElements - (withdrawal.size * withdrawal.number)) - t?index}</td>
                            <td class="text-center">${t.user.deptName!''}</td>
                            <td class="text-center">${t.user.teamName!''}</td>
                            <td class="text-center">${t.user.name!''}</td>
                            <td class="text-center">${t.user.email!''}</td>
                            <td class="text-center"><span class="label label-primary">${t.user.commaJobTitle!'N/A'}</span></td>
                            <td class="text-center">${t.withdrawalDate?string('yyyy-MM-dd')}</td>
                            <td class="text-center">
                                <span class="label label-${t.apply?then('danger', 'primary')}">${t.apply?then('Processed', 'Scheduled')}</span>
                            </td>
<#--                            <td class="text-center">-->
<#--&lt;#&ndash;                                <a href="agreement-to-collect-and-use/${t.id}/print" class="btn btn-mint btn-xs add-tooltip" data-toggle="tooltip" data-container="body" data-placement="top" data-original-title="Print"><i class="fa fa-print"></i></a>&ndash;&gt;-->
<#--                            </td>-->
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="7" class="text-center">등록된 데이터가 없습니다.</td>
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