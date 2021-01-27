<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">
<html>
<head>
    <title>e-SOP > Training > Off-line Training</title>
</head>
<body>
<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Off-line Training</h3>
    </div>

    <!--Data Table-->
    <!--===================================================-->
    <@pagination.bind isoOfflineTraining/>
    <div class="panel-body">
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
<#--                    <div class="form-group">-->
<#--                        <input id="demo-input-search2" type="text" placeholder="Document ID" class="form-control" autocomplete="off">-->
<#--                    </div>-->
                    <div class="btn-group">
                        <a class="btn btn-primary" href="/training/iso/offline-training/request"><i class="fa fa-plus"></i> Off-line Training</a>
                    </div>
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center">No.</th>
                    <th class="text-center">Completion Date</th>
                    <th>Description of Training Course / Hour</th>
<#--                    <th>Training Hr.</th>-->
                    <th class="text-center">Organization
<#--                        <br/><span class="text-muted">(Academy, Self-training, etc.)</span>-->
                    </th>
                    <th class="text-center">Created By</th>
                    <th class="text-center">Created On</th>
                    <th class="text-center">Status</th>
<#--                    <th>#Actions</th>-->
                </tr>
                </thead>
                <tbody>
                <#if isoOfflineTraining.content?has_content>
                <#list isoOfflineTraining.content as t>
                    <tr>
                        <td class="text-center">${(isoOfflineTraining.totalElements - (isoOfflineTraining.size * isoOfflineTraining.number)) - t?index}</td>
                        <td class="text-center">${t.isoOfflineTraining.trainingDate?string('dd-MMM-yyyy')}</td>
                        <td>
                            <a href="offline-training/${t.isoOfflineTraining.id}">
                            <#list t.isoOfflineTraining.isoOfflineTrainingDocuments as doc>
                                <div<#if t.isoOfflineTraining.status == 'DELETED'> style="text-decoration: line-through;color:indianred !important;" </#if>><i class="fa fa-file-pdf-o"></i> [${doc.iso.isoType.label}] ${doc.iso.title} / <span class="label label-default"><i class="fa fa-clock-o"></i> ${doc.hour}</span></div>
                        </#list>
                            </a>
                        </td>
<#--                        <td>${t.hour}</td>-->
                        <td class="text-center">${t.isoOfflineTraining.organization}</td>
                        <td class="text-center">${t.isoOfflineTraining.createdBy}</td>
                        <td class="text-center">${t.isoOfflineTraining.createdDate?string('yyyy-MM-dd')}</td>
                        <td class="text-center">
                            <span class="label label-${t.isoOfflineTraining.status.className} add-tooltip" data-toggle="tooltip" data-container="body" data-placement="top" data-original-title="
                                <#if t.isoOfflineTraining.status == 'APPROVED'>교육 이력이 반영 되었습니다.
                                <#elseif t.isoOfflineTraining.status == 'DELETED'>관리자의 의해 반려 처리 되었습니다.
                                </#if>">
                                ${t.isoOfflineTraining.status.label}</span>
                            </td>
<#--                        <td>${(i % 3 == 0)?then('<span class="label label-info">승인대기</span>', '<span class="label label-success">승인완료</span><span class="label label-warning">반려</span>')}</td>-->
<#--                        <td><#if i % 3 == 0>-->
<#--                            <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 삭제</button>-->
<#--                        </#if>-->
<#--                        </td>-->
                    </tr>
                </#list>
                <#else>
                    <tr>
                        <td colspan="7" class="text-center">Off-line Training 데이터가 없습니다.</td>
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