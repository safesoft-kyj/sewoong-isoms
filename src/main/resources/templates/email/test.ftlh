<#setting locale="en_US">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Notice Alert</title>
    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="http://${domain}/static/css/bootstrap.min.css" rel="stylesheet">
    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="http://${domain}/static/css/nifty.min.css" rel="stylesheet">
    <#--    <link href="${domain}/static/plugins/transactional-email-templates/templates/styles.css" media="all" rel="stylesheet" type="text/css" />-->
    <style type="text/css">
        html, body {
            background-color: #fff !important;
        }
    </style>
</head>

<body>
<div class="mar-all">
    <div class="panel panel-default">
        <div class="panel-body form-horizontal form-padding">

            <!--Text Input-->
            <div class="form-group">
                <label class="col-md-3 control-label">Training Date</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" placeholder="Text" readonly="readonly" value="">
                    <#--                    <small class="help-block">(DD-MMM-YYYY)</small>-->
                </div>
            </div>

            <!--Email Input-->
            <div class="form-group">
                <label class="col-md-3 control-label">Organization</label>
                <div class="col-md-9">
                    <input type="email" class="form-control" placeholder="Enter your email" readonly="readonly" value="">
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

                            <tr>
                                <td>${doc?index + 1}</td>
                                <td></td>
                                <td><input type="number" class="form-control input-sm" value="" readonly="readonly"/></td>
                            </tr>

                        </tbody>
                    </table>
                </div>
            </div>

            <#--            <div class="form-group">-->
            <#--                <label class="col-md-3 control-label" for="demo-email-input">Attendee</label>-->
            <#--                <div class="col-md-9">-->
            <#--                    <table class="table table-bordered">-->
            <#--                        <thead>-->
            <#--                        <tr class="bg-gray-light">-->
            <#--                            <th style="width:70px;">No.</th>-->
            <#--                            <th>Name</th>-->
            <#--                            &lt;#&ndash;                            <th>참석여부</th>&ndash;&gt;-->
            <#--                        </tr>-->
            <#--                        </thead>-->
            <#--                        <tbody>-->
            <#--                        <#list offlineTraining.offlineTrainingAttendees as atd>-->
            <#--                            <tr>-->
            <#--                                <td>${atd?index + 1}</td>-->
            <#--                                <td>${atd.user.korName}</td>-->
            <#--                            </tr>-->
            <#--                        </#list>-->
            <#--                        </tbody>-->
            <#--                    </table>-->
            <#--                    <small class="help-block">* 오프라인 교육 이력은 관리자 확인 후 반영 됩니다.</small>-->
            <#--                </div>-->
            <#--            </div>-->
        </div>
    </div>
</div>
</body>
</html>

