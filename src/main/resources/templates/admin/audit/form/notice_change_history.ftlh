<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">

<@pagination.bind auditList/>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center" style="width:80px">Rev No.</th>
            <th class="text-center">Revision Date</th>
            <th class="text-center">Revision Type</th>
            <th class="text-center">ID</th>
            <th>Title</th>
            <th style="width:500px;">Content</th>
            <th class="text-center">Attach Files</th>
            <th class="text-center">topViewEndDate</th>
            <th class="text-center">postStatus</th>
            <th class="text-center">Deleted</th>
        </tr>
        </thead>
        <tbody>
        <#if auditList.content?has_content>
            <#list auditList.content as audit>
                <tr>
                    <#assign new = ''>
                    <#if audit.revisionType == 'ADD'>
                        <#assign revType = 'info' />
                        <#assign new = 'text-info text-bold bg-trans-dark'>
                    <#elseif audit.revisionType == 'MOD'>
                        <#assign revType = 'warning' />
                    <#elseif audit.revisionType == 'DEL'>
                        <#assign revType = 'danger' />
                    </#if>

                    <td class="text-center ${new}" style="vertical-align: middle;">${audit.revisionEntity.rev!''}</td>
                    <td class="text-center ${new}" style="vertical-align: middle;">${audit.revisionEntity.revisionDate?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td class="text-center ${new}" style="vertical-align: middle;"><span class="label label-${revType}">${audit.revisionType}</span></td>
                    <td class="text-center ${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">${audit.entity.id}</td>
                    <td class="${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">${audit.entity.title!'N/A'}</td>
                    <td class="${new}" style="${audit.contains('content')?then('color:red;', '')}">
                        <#if audit.entity.content?has_content>
                            <button type="button" class="btn btn-xs ${new?has_content?then('btn-info', audit.contains('content')?then('btn-danger', 'bg-gray-dark'))}"
                                    data-content="${audit.entity.content}" data-toggle="modal" data-target="#content-modal">
                                <i class="fa fa-comments" style="font-size: 16px;"></i>
                            </button>
                        </#if>
                    </td>
                    <td class="text-center ${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">
                        <#if audit.entity.attachFiles?has_content>
                            <#list audit.entity.attachFiles as file>
                                <i class="fa fa-file"></i>&nbsp;${file.originalFileName!'N/A'}<br />
                            </#list>
                        </#if>
                    </td>
                    <td class="text-center ${new}"style="${audit.contains('topViewEndDate')?then('color:red;', '')}">${audit.entity.topViewEndDate?has_content?then(audit.entity.topViewEndDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}"style="${audit.contains('postStatus')?then('color:red;', '')}">${audit.entity.postStatus!'N/A'}</td>
                    <td class="text-center ${new}"style="${audit.contains('deleted')?then('color:red;', '')}">${audit.entity.deleted?then('Y', 'N')}</td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="10" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>

    <div class="modal fade" id="content-modal" role="dialog" tabindex="-1" aria-labelledby="training-modal" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title">Content Details</h4>
                </div>
                <!--Modal body-->
                <div class="modal-body">
                    <div id="content-html" style="max-height: 700px; overflow: auto;"></div>
                </div>

                <!--Modal footer-->
                <div class="modal-footer">
                    <div class="row">
                        <div class="col-xs-8 text-left">
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-info" data-dismiss="modal" type="button"><i class="fa fa-close"></i>Close</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script>
        $("#content-modal").on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget); // Button that triggered the modal
            let content = button.data('content');

            $("#content-html").html(content);
        });
    </script>
</div>
<@pagination.default />