<html>
<head>
    <title>Admin > Dashboard</title>
</head>
<body>
<#--<div class="row">-->
<#--    <div class="col-md-2">-->
<#--        <div class="panel panel-warning panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="demo-pli-file-word icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Effective</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <div class="col-md-2">-->
<#--        <div class="panel panel-info panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="demo-pli-file-zip icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Approved</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <div class="col-md-2">-->
<#--        <div class="panel panel-mint panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="demo-pli-camera-2 icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Superseded</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <div class="col-md-2">-->
<#--        <div class="panel panel-purple panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="demo-pli-video icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Retirement</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <div class="col-md-2">-->
<#--        <div class="panel panel-pink panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="demo-pli-video icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Revision</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <div class="col-md-2">-->
<#--        <div class="panel panel-dark panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="demo-pli-video icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Development</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->

<#--</div>-->

<div class="row">
    <div class="col-md-6">
        <div class="panel">

            <div class="panel">
                <div class="panel-heading">
                    <h3 class="panel-title">전자결재</h3>
                </div>

                <!--Bordered Table-->
                <!--===================================================-->
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th class="text-center">#</th>
                                <th>Type</th>
                                <th>User</th>
                                <th>Registration date</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if approvalList?has_content>
                                <#list approvalList as app>
                                <tr>
                                    <td class="text-center">${app.id}</td>
                                    <td><a href="#approval-modal" data-toggle="modal" data-title="${app.type.label}" data-id="${app.id}" class="btn-link">${app.type.label}</a></td>
                                    <td><span class="text-muted">${app.createdBy}</span></td>
                                    <td><span class="text-muted">${app.createdDate?string('yyyy-MM-dd')}</span></td>
                                    <td><span class="label label-${app.status.className}">${app.status.label}</span></td>
                                </tr>
                                </#list>
                            <#else>
                                <tr>
                                    <td class="text-center" colspan="5">No Data</td>
                                </tr>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!--===================================================-->
                <!--End Bordered Table-->

            </div>

        </div>
    </div>
    <div class="col-md-6">
        <div class="panel">

            <div class="panel">
                <div class="panel-heading">
                    <h3 class="panel-title">Off-line Training</h3>
                </div>

                <!--Bordered Table-->
                <!--===================================================-->
                <div class="panel-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                            <tr>
                                <th class="text-center">#</th>
                                <th>Training date</th>
                                <th>Organization</th>
                                <th>Users</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#if offlineTrainingList.content?has_content>
                            <#list offlineTrainingList.content as app>
                            <tr>
                                <td class="text-center">${app.id}</td>
                                <td><span class="text-muted">${app.trainingDate?string('yyyy-MM-dd')}</span></td>
                                <td><a href="/admin/training/offline-training/${app.id}" class="btn-link">${app.organization}</a></td>
                                <td>${app.createdBy}(${app.offlineTrainingAttendees?size})</td>
                                <td><span class="label label-${app.status.className}">${app.status.label}</span></td>
                            </tr>
                            </#list>
                            <#else>
                            <tr>
                                <td colspan="5" class="text-center">No Data.</td>
                            </tr>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
                <!--===================================================-->
                <!--End Bordered Table-->

            </div>

        </div>
    </div>

</div>
<#include "authority/approval-modal.ftlh"/>
</body>
</html>