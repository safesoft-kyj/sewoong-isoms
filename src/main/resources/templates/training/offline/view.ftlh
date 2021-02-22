<html>
<head>
    <title>Off-line Training</title>
    <script src="/static/plugins/chosen/chosen.jquery.min.js"></script>
    <link href="/static/plugins/chosen/chosen.min.css" rel="stylesheet">
    <#--    <script src="/static/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>-->
    <#--    <link href="/static/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.css" rel="stylesheet">-->

</head>
<body>


<div class="col-lg-12">
    <div class="panel">
        <div class="panel-heading">
        </div>


        <!-- BASIC FORM ELEMENTS -->
        <!--===================================================-->
        <form class="panel-body form-horizontal form-padding">

            <!--Text Input-->
            <div class="form-group">
                <label class="col-md-3 control-label">Training Date</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" placeholder="Text" readonly="readonly" value="${offlineTraining.trainingDate?string('yyyy-MM-dd')}">
                    <#--                    <small class="help-block">(DD-MMM-YYYY)</small>-->
                </div>
            </div>

            <!--Email Input-->
            <div class="form-group">
                <label class="col-md-3 control-label">Organization</label>
                <div class="col-md-9">
                    <input type="email" class="form-control" placeholder="Enter your email" readonly="readonly" value="${offlineTraining.organization}">
                    <#--                    <small class="help-block">(Academy, Self-training, etc.)</small>-->
                </div>
            </div>

            <!--Password-->
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-password-input">Training SOP</label>
                <div class="col-md-9">
                    <#--                    <select data-placeholder="Choose a SOP..." id="sop-select" tabindex="2" multiple>-->
                    <#--                        <#list 1..20 as i>-->
                    <#--                            <option value="SOP${i}">SOP-STAT001 STAT Project Management v1.1</option>-->
                    <#--                        </#list>-->
                    <#--                    </select>-->

                    <table class="table table-bordered">
                        <thead>
                        <tr class="bg-gray-light">
                            <th class="col-xs-1">No.</th>
                            <th>SOP</th>
                            <th class="col-xs-2">Training Hr.</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list offlineTraining.offlineTrainingDocuments as doc>
                            <tr>
                                <td>${doc?index + 1}</td>
                                <td>${doc.documentVersion.document.docId} ${doc.documentVersion.document.title} v${doc.documentVersion.version}</td>
                                <td><input type="number" class="form-control input-sm" value="${doc.hour}" readonly="readonly"/></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-email-input">Attendee</label>
                <div class="col-md-9">
                    <table class="table table-bordered">
                        <thead>
                        <tr class="bg-gray-light">
                            <th style="width:70px;">No.</th>
                            <th>Name</th>
                            <#--                            <th>참석여부</th>-->
                        </tr>
                        </thead>
                        <tbody>
                        <#list offlineTraining.offlineTrainingAttendees as atd>
                            <tr>
                                <td>${atd?index + 1}</td>
                                <td>${atd.account.name}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                    <small class="help-block">* 오프라인 교육 이력은 관리자 확인 후 반영 됩니다.</small>
                </div>
            </div>
        </form>
        <div class="panel-footer text-right">
            <form method="post" id="applyForm">
                <a class="btn btn-default" href="../offline-training">Back</a>
<#--                <#if offlineTraining.status == 'SUBMITTED'>-->
<#--                    <button class="btn btn-primary" type="submit"><i class="fa fa-check"></i> 트레이닝 이력 반영</button>-->
<#--                </#if>-->
            </form>
        </div>
        <!--===================================================-->
        <!-- END BASIC FORM ELEMENTS -->


    </div>
</div>

<script>
    $(function() {
        $('#applyForm').submit(function() {
            return confirm('트레이닝 이력 반영 처리 하시겠습니까?');
        });
    });
</script>
</body>
</html>
