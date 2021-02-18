<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > 개인정보 활용동의 이력 (외부사용자)</title>
</head>
<body>

<div class="panel">
    <div class="panel-heading">
        <div class="panel-control" style="float:left;">
            <ul class="nav nav-tabs">
                <li>
                    <a href="/admin/authority/agreement-to-collect-and-use/internal">내부 사용자</a>
                </li>
                <li class="active">
                    <a href="/admin/authority/agreement-to-collect-and-use/external">외부 사용자</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="panel-body">
        <div class="tab-content">
            <div class="tab-pane fade active in">
                <@pagination.bind agreement/>
                <#--            <div class="pad-btm form-inline">-->
                <#--                <div class="row">-->
                <#--                    <div class="col-sm-6 table-toolbar-left">-->
                <#--                        <a class="btn btn-purple" href="/admin/authority/agreement-to-collect-and-use/internal"><i-->
                <#--                                    class="pli-door"></i> 내부 사용자</a>-->
                <#--                        &lt;#&ndash;                    <button class="btn btn-default"><i class="pli-printer"></i></button>&ndash;&gt;-->
                <#--                        &lt;#&ndash;                    <div class="btn-group">&ndash;&gt;-->
                <#--                        &lt;#&ndash;                        <button class="btn btn-default"><i class="pli-exclamation"></i></button>&ndash;&gt;-->
                <#--                        &lt;#&ndash;                        <button class="btn btn-default"><i class="pli-recycling"></i></button>&ndash;&gt;-->
                <#--                        &lt;#&ndash;                    </div>&ndash;&gt;-->
                <#--                    </div>-->
                <#--                    <div class="col-sm-6 table-toolbar-right">-->
                <#--                        <div class="form-group">-->
                <#--                            &lt;#&ndash;                        <input id="demo-input-search2" type="text" placeholder="Search" class="form-control" autocomplete="off">&ndash;&gt;-->
                <#--                        </div>-->
                <#--                        <div class="btn-group">-->
                <#--                        </div>-->
                <#--                    </div>-->
                <#--                </div>-->
                <#--            </div>-->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr class="bg-gray-light">
                            <th class="text-center" style="width:80px">No.</th>
                            <th class="text-center">Company Name<br/>or Institute Name</th>
                            <th class="text-center">Customer Name</th>
                            <th class="text-center">Email</th>
                            <th class="text-center">Role</th>
                            <th class="text-center">Agreement Date</th>
                            <th style="width:80px;">#Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if agreement.content?has_content>
                            <#list agreement.content as t>
                                <tr>
                                    <td class="text-center">${(agreement.totalElements - (agreement.size * agreement.number)) - t?index}</td>
                                    <td class="text-center">${t.externalCustomer.sopDisclosureRequestForm.companyNameOrInstituteName}</td>
                                    <td class="text-center">${t.externalCustomer.name}</td>
                                    <td class="text-center">${t.externalCustomer.email}</td>
                                    <td class="text-center">${t.externalCustomer.role!''}</td>

                                    <td class="text-center">${t.createdDate?string('yyyy-MM-dd')}</td>
                                    <td class="text-center">
                                        <a class="btn btn-info btn-xs" href="#approval-modal" data-toggle="modal"
                                           data-title="SOP & Digital Binder Disclosure Request Form"
                                           data-id="${t.externalCustomer.sopDisclosureRequestForm.approval.id}"><i
                                                    class="fa fa-file-text-o"></i></a>
                                        <a href="${t.id}/print"
                                           class="btn btn-mint btn-xs add-tooltip" data-toggle="tooltip"
                                           data-container="body" data-placement="top" data-original-title="Print"><i
                                                    class="fa fa-print"></i></a>
                                    </td>
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
        </div>
    </div>
    <!--===================================================-->
    <!--End Data Table-->
</div><!-- end panel-->
<#include "approval-modal.ftlh"/>

</body>
</html>