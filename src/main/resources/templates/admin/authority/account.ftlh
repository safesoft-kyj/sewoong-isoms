<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > Authority Management > 외부사용자</title>
</head>
<body>

<div class="panel">
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <@pagination.bind account/>
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
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:80px">No.</th>
                    <th class="text-center">Company Name<br/>or Institute Name</th>
                    <th class="text-center">Customer Name</th>
                    <th class="text-center">Email</th>
                    <th class="text-center">Job Title</th>
                    <th class="text-center">Request Date<br/><small>(Date or Period to access e-SOP system)</small></th>
                    <th style="width:100px">#Actions</th>
                </tr>
                </thead>
                <tbody>
                <#if account.content?has_content>
                    <#list account.content as t>
                        <tr>
                            <td class="text-center">${(account.totalElements - (account.size * account.number)) - t?index}</td>
                            <td class="text-center">${t.sopDisclosureRequestForm.companyNameOrInstituteName}</td>
                            <td class="text-center">${t.name}</td>
                            <td class="text-center">${t.email}</td>
                            <td class="text-center">${t.jobTitle!''}</td>
                            <td class="text-center">${t.sopDisclosureRequestForm.requestStartDate?string('yyyy-MM-dd')}~${t.sopDisclosureRequestForm.requestEndDate?string('yyyy-MM-dd')}</td>
                            <td class="text-center">
                                <a class="btn btn-xs btn-info" href="#approval-modal" data-toggle="modal" data-title="SOP & Digital Binder Disclosure Request Form" data-id="${t.sopDisclosureRequestForm.approval.id}"><i class="fa fa-file-text-o"></i></a>
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
    <!--===================================================-->
    <!--End Data Table-->
</div><!-- end panel-->
<#include "approval-modal.ftlh"/>
</body>
</html>