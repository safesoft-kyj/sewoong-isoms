<html>
<head>
    <title>SOP & RD Request Form</title>
    <script src="/static/plugins/chosen/chosen.jquery.min.js"></script>
    <link href="/static/plugins/chosen/chosen.min.css" rel="stylesheet">
    <script src="/static/plugins/nestable-list/jquery.nestable.js"></script>
    <link href="/static/plugins/nestable-list/nestable-list.min.css" rel="stylesheet">
    <#--    <script src="/static/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>-->
    <#--    <link href="/static/plugins/bootstrap-tagsinput/bootstrap-tagsinput.min.css" rel="stylesheet">-->

</head>
<body>

<div class="col-lg-8">
    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title">SOP & RD Request Form</h3>
        </div>


        <!-- BASIC FORM ELEMENTS -->
        <!--===================================================-->
        <form class="panel-body form-horizontal form-padding">

            <!--Text Input-->
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-text-input">Name of Requester</label>
                <div class="col-md-9">
<#--                    <input type="text" id="demo-text-input" class="form-control" placeholder="Text">-->
<#--                    <small class="help-block">(DD-MMM-YYYY)</small>-->
                    <p class="form-control-static">홍길동</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-text-input">Name of Team/Department</label>
                <div class="col-md-9">
                    <input type="text" id="demo-text-input" class="form-control" placeholder="Text">
<#--                    <small class="help-block">(DD-MMM-YYYY)</small>-->
                    <p class="form-control-static">IT/PMA</p>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-password-input">Request For</label>
                <div class="col-md-9">
                    <select data-placeholder="Choose a SOP..." id="request-for-select" tabindex="2">
                        <option value="SOP">New SOP Development</option>
                        <option value="SOP">New RD Development</option>
                        <option value="SOP">SOP Revision</option>
                        <option value="SOP">RD Revision</option>
                    </select>
                </div>
            </div>

            <!--Password-->
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-password-input">Effective SOP(s)</label>
                <div class="col-md-9">
                    <select data-placeholder="Choose a SOP..." id="sop-select" tabindex="2" multiple>
                        <#list 1..20 as i>
                            <option value="SOP${i}">SOP-STAT001 STAT Project Management v1.1</option>
                        </#list>
                    </select>

                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th class="col-xs-1">No.</th>
                            <th>Title</th>
                            <th class="col-xs-2">Version</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list 1..3 as i>
                            <tr>
                                <td>${i}</td>
                                <td>SOP-AD0001 Test SOP v1.1</td>
                                <td><input type="number" class="form-control input-sm"/></td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>

            <!--Email Input-->
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-email-input">Reason for the request</label>
                <div class="col-md-9">
                    <textarea class="form-control" placeholder="If you select new SOP/RD development, please specify new SOP/RD title, purpose and the target date (expected effective date).
If you select SOP/RD revision, please specify the reason of revision and the target date (expected effective date).
For new RD development, please specify the SOP information (number and title) which is associated with the new RD.
"></textarea>
                </div>
            </div>

<#--            <div class="form-group">-->
<#--                <label class="col-md-3 control-label" for="demo-email-input">Attendee</label>-->
<#--                <div class="col-md-9">-->
<#--                    <select data-placeholder="Choose a SOP..." id="attendee-select" tabindex="2" multiple data-search="true">-->
<#--                        <#list 1..20 as i>-->
<#--                            <option value="SOP${i}">홍길동${i}</option>-->
<#--                        </#list>-->
<#--                    </select>-->
<#--                </div>-->
<#--            </div>-->
        </form>
        <div class="panel-footer text-right">
            <a class="btn btn-info" href="/training/offline-training">임시저장</a>
            <a class="btn btn-primary" href="/training/offline-training">결재요청</a>
        </div>
        <!--===================================================-->
        <!-- END BASIC FORM ELEMENTS -->


    </div>
</div>

<div class="col-lg-4">
    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title">결재선</h3>
        </div>
        <div class="panel-body">
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-email-input">검토</label>
                <div class="col-md-9">
                    <select data-placeholder="Choose a Reviewer..." id="reviewer-select" tabindex="2">
                        <#list 1..20 as i>
                            <option value="SOP${i}">홍길동${i}</option>
                        </#list>
                    </select>

                    <div id="demo-nestable-theme-2" class="dd">
                        <ol class="dd-list">
                            <li class="dd-item" data-id="Item 1">
                                <div class="dd-handle dd-outline dd-anim">
                                    <div class="media-left">
                                        <i class="pli-user icon-2x"></i>
                                    </div>
                                    <div class="media-body">
                                        <div class="text-semibold text-nowrap">리뷰어1</div>
                                        <small class="text-muted">Manager</small>
                                    </div>
                                </div>
                            </li>
                            <li class="dd-item" data-id="Item 2">
                                <div class="dd-handle dd-outline dd-anim">
<#--                                    <span class="badge badge-success pull-right">90%</span>-->
                                    <div class="media-left">
                                        <i class="pli-user icon-2x"></i>
                                    </div>
                                    <div class="media-body">
                                        <p class="text-semibold mar-no">리뷰어2</p>
                                        <small class="text-muted">Manager</small>
                                    </div>
                                </div>
                            </li>
                        </ol>
                    </div>
                    <script>
                        $('#demo-nestable-theme-2').nestable({group: 11});
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label" for="demo-email-input">승인</label>
                <div class="col-md-9">
                    <div class="dd">
                        <ol class="dd-list">
                            <li class="dd-item" data-id="Item 1">
                                <div class="dd-handle dd-outline dd-anim">
                                    <div class="media-left">
                                        <i class="pli-checked-user icon-2x"></i>
                                    </div>
                                    <div class="media-body">
                                        <div class="text-semibold text-nowrap">임현진</div>
                                        <small class="text-muted">QMO</small>
                                    </div>
                                </div>
                            </li>
                        </ol>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    $(function() {
        $('#request-for-select, #sop-select, #reviewer-select').chosen({width:'100%'});
    });
</script>
</body>
</html>