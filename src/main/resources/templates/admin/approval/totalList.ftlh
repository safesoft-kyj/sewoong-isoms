<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO Management System > 전자결재</title>
    <script>
        $(document).ready(function() {
           $("a[name='removeBtn']").click(function(e) {
              e.preventDefault();

               if(confirm('정말로 삭제 하시겠습니까?')) {
                    $("#removeForm").attr("action", $(this).attr("href")).submit();
               }
           });
        });
    </script>
</head>
<body>
    <form id="removeForm" method="post">
        <input type="hidden" name="_method" value="delete"/>
    </form>

        <div class="panel">
            <!--Data Table-->
            <!--===================================================-->
            <div class="panel-body">
                <@pagination.bind approvalList/>
                <div class="pad-btm">
                    <form class="form-inline">
                        <div class="row">
                            <div class="col-sm-6 table-toolbar-left">
                                <#if RequestParameters['type']?has_content && RequestParameters['type'] == "SOP_Training_Deviation_Report">
                                    <a href="/admin/approval/training/deviation/print" class="btn btn-primary"><i class="pli-printer"></i> Training Deviation Log</a>
                                </#if>
                            </div>
                            <div class="col-sm-6 table-toolbar-right">
                                <div class="form-group">
                                    <select class="form-control" name="type">
                                        <option value="">ALL</option>
                                        <#list reportTypeList as a>
                                            <option value="${a.name()}"<#if reportType?has_content && reportType == a> selected</#if>>${a.label}</option>
                                        </#list>
                                    </select>
    <#--                                <input id="demo-input-search2" type="text" placeholder="Search" class="form-control" autocomplete="off">-->
                                </div>
                                <div class="btn-group">
                                    <button class="btn btn-default" type="submit"><i class="fa fa-search fa-lg"></i></button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr class="bg-gray-light">
                            <th class="text-center" style="width:80px">No.</th>
                            <th class="text-center"style="width:80px">상태</th>
                            <th class="text-center">Keyword</th>
                            <th>기안종류</th>
                            <th class="text-center" style="width:140px">기안자</th>
                            <th class="text-center" style="width:120px;">기안일</th>
                            <#--                    <th>완료일</th>-->

                            <th style="width:160px">#Actions</th>
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
                                    <td>${t.keyword!''}</td>
                                    <td>${t.type.label}</td>
                                    <td class="text-center">${t.createdBy}</td>
                                    <td class="text-center"><span class="text-muted"> ${t.createdDate?string('yyyy-MM-dd')}</span></td>

                                    <td>
                                        <a class="btn btn-info btn-sm" href="#approval-modal" data-toggle="modal" data-title="${t.type.label}" data-id="${t.id}"><i class="fa fa-search"></i></a>
                                       <#if t.status != "approved" && t.status != "rejected">
                                        <a class="btn btn-danger btn-sm" name="removeBtn" href="/admin/approval/${status?has_content?then(status.name() +'/', '')}${t.id}/v"><i class="fa fa-trash"></i></a>
                                        </#if>
                                        <#if t.status == "approved">
                                            <a class="btn btn-success btn-sm" href="/admin/approval/${t.id}/print"><i class="fa fa-file-pdf-o"></i></a>
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
    <#include "../authority/approval-modal.ftlh"/>
</body>
</html>