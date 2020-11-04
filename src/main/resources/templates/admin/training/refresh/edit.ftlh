<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > Training Management > Refresh Training</title>
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<#--        <link href="/static/plugins/chosen/chosen.min.css" rel="stylesheet">-->
<#--        <script src="/static/plugins/chosen/chosen.jquery.min.js"></script>-->
    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">
    <script src="/static/plugins/select2/js/select2.min.js"></script>
    <script>
        $(document).ready(function() {
            // $('#startDate,#endDate').datepicker({format:'yyyy-mm-dd', autoclose:true, clearBtn:true});
            $('#training-period .input-daterange').datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                autoclose: true,
                todayHighlight: true
            });

            $("#documentVersion\\.id").select2({width:"100%"});
        });
    </script>
</head>
<body>


<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Refresh Training</h3>
    </div>
    <form class="form-horizontal" method="post">
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3 control-label">SOP</label>
                <div class="col-sm-9">
                    <@spring.formSingleSelect "trainingPeriod.documentVersion.id" sopMap "class='form-control'"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-3 control-label" >Training Period</label>
                <div class="col-sm-9">
                    <div id="training-period">
                        <div class="input-daterange input-group" id="datepicker">
<#--                            <input type="text" class="form-control" name="start" />-->
                            <@spring.formInput "trainingPeriod.startDate" "class='form-control' placeholder='Start Date'"/>
                            <span class="input-group-addon">to</span>
                            <@spring.formInput "trainingPeriod.endDate" "class='form-control' placeholder='End Date'"/>
                        </div>
                    </div>
                    <@spring.showErrors "<br/>" "color:red;"/>
                </div>
            </div>
        </div>
        <div class="panel-footer text-right">
            <a class="btn btn-link" href="/admin/training/refresh-training"><i class="fa fa-times"></i> Cancel</a>
            <button class="btn btn-primary" type="submit"><i class="fa fa-check"></i> Submit</button>
        </div>
    </form>
    <!--===================================================-->
    <!--End Horizontal Form-->

</div>

</body>
</html>