<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#setting locale="en_US">
<html>
<head>
    <title>Training > Team/Department Employee Training Log</title>
    <script src="/static/plugins/jstree/jstree.min.js"></script>
    <link href="/static/plugins/jstree/themes/default/style.min.css" rel="stylesheet">

    <script src='/static/js/pdfobject.min.js'></script>
    <style>
        /*.modal-dialog.modal-fullsize {*/
        /*    width: 1200px;*/
        /*    height: 650px;*/
        /*    margin: 3px;*/
        /*    padding: 0;*/
        /*}*/
        /*.modal-content.modal-fullsize {*/
        /*    height: auto;*/
        /*    min-height: 650px;*/
        /*    border-radius: 0;*/
        /*    !*overflow-x: hidden !important;*!*/
        /*}*/

        #pdfViewer {
            overflow: hidden;!important;
        }
    </style>
</head>
<body>
<div class="panel">
    <#--    <div class="panel-heading">-->
    <#--        <h3 class="panel-title">Off-line Training</h3>-->
    <#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <small class="text-danger"> **** My Training 처럼(팀/부서) 별로 ***</small>
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
<#--                    <div class="btn-group">-->
<#--                        <a class="btn btn-primary" href="/training/offline-training/request"><i class="fa fa-plus"></i> Add Off-line Training</a>-->
<#--                    </div>-->
                </div>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                $.ajax({
                    url: "/common/organization/chart",
                    method: "GET",
                    data: { r : Math.random() },
                    dataType: "json"
                }).done(function(response) {
                    // $('#org-jstree-json').jstree('destroy');
                    $('#org-jstree-json').jstree({
                        'core': {
                            // 'check_callback' : true,
                            // 'dblclick_toggle' : false,
                            'data': response
                        },
                        'plugins': ['types'],
                        'types' : {
                            'company' : {
                                'icon' : 'pli-building icon-lg'
                            },
                            'dept' : {
                                'icon' : 'pli-folders icon-lg text-warning'
                            },
                            'team' : {
                                'icon' : 'pli-folder text-warning'
                            },
                            'user_mng_male' : {
                                'icon' : 'pli-business-man icon-lg'
                            },
                            'user_mng_female' : {
                                'icon' : 'pli-business-woman icon-lg'
                            },
                            'user_male' : {
                                'icon' : 'pli-male icon-lg'
                            },
                            'user_female' : {
                                'icon' : 'pli-female icon-lg'
                            }
                        }
                    }).bind('select_node.jstree', function (event, data) {
                        var jsTreeData = {};
                        for (var i = 0; i < data.selected.length; i++) {
                            var dataJson = data.instance.get_node(data.selected[i]).data;
                            var code = dataJson.code;    //data 에서 a 가져오기
                        }
                    }).bind('dblclick.jstree', function(e) {
                        var instance = $.jstree.reference(this);
                        var node = instance.get_node(e.target);
                        var jsonData = node.data;

                        if(jsonData.type == 'user') {
                            alert('empNo:' + jsonData.empNo + ',korName:' + jsonData.empName);
                            if(jsonData.empNo) {
                                $("#btn_" + jsonData.empNo).click();
                            }
                        }
                        // console.log(event);
                        // var node = $(event.target).closest("li");
                        // console.log(node);
                        // var data = node.data("jstree");
                        // console.log(data);
                    });
                });
            });
        </script>
        <div id="org-jstree-json">

        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center">No.</th>
                    <th class="text-center">Department</th>
                    <th>Team</th>
                    <th class="text-center">Name</th>
                    <th>#Actions</th>
                </tr>
                </thead>
                <tbody>
                <#if employees?has_content>
                    <#list employees as emp>
                        <tr>
                            <td>${emp?index + 1}</td>
                            <td>${emp['deptName']?default('')}</td>
                            <td>${emp['teamName']?default('')}</td>
                            <td>${emp['name']?default('')}</td>
                            <td>
                                <#if emp['empNo']?has_content>
                                <a class="btn btn-icon" id="btn_${emp['empNo']}" href="#trainingLog-modal" data-toggle="modal" data-empno="${emp['empNo']}" data-title="Employee Training Log - ${(emp['deptName']?has_content)?then(emp['deptName'] + '/', '')}${(emp['teamName']?has_content)?then(emp['teamName'] + '/', '')}${emp['korName']}"><i class="fa fa-search-plus"></i></a>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                <#else>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>

<script>
    $(document).ready(function() {
        $('#trainingLog-modal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var title = button.data('title'); // Extract info from data-* attributes
            var empNo = button.data('empno'); // Extract info from data-* attributes
            var modal = $(this);
            modal.find('.modal-title').text(title);
            var options = {
                // width:"1130px",
                pdfOpenParams: { toolbar: '0', statusbar: '0', messages: '0', scrollbar: '1', view:'FitH', zoom:90}//pagemode: 'thumbs', zoom:90
            };

            var $container = $("#pdfViewer");
            PDFObject.embed("/training/trainingLog/employees/" + empNo, $container, options);
        });
    });
</script>
<div class="modal fade" id="trainingLog-modal" role="dialog" tabindex="-1" aria-labelledby="matrix-modal" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title"></h4>
            </div>

            <!--Modal body-->
            <div class="modal-body">
                <div id="pdfViewer" style="width:100% !important;height:650px !important;">
                </div>

                <!--Modal footer-->
                <div class="modal-footer">
                    <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
