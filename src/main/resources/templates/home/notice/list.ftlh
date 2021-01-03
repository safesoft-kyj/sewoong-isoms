<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<@security.authentication property="principal" var="user"/>
<html>
<head>
    <title>CAUH ISO Management System > 공지사항</title>
<#--    <script src="/static/plugins/jstree/jstree.min.js"></script>-->
<#--    <link href="/static/plugins/jstree/themes/default/style.min.css" rel="stylesheet">-->

<#--    <script src='/static/js/pdfobject.min.js'></script>-->
    <style>
        .modal-dialog.modal-fullsize {
            width: 80%;
            height: 550px;
            margin: 3px;
            padding: 0;
        }
        .modal-content.modal-fullsize {
            height: auto;
            min-height: 550px;
            border-radius: 0;
        }
    </style>
</head>
<body>


<#--<div class="row">-->
<#--    <div class="col-md-3">-->
<#--        <div class="panel panel-purple panel-colorful media middle pad-all" id="currentSOP" style="cursor: pointer;">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="pli-file-word icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--&lt;#&ndash;                <p class="text-2x mar-no text-semibold">1</p>&ndash;&gt;-->
<#--                <p class="mar-no">Current SOP</p>-->
<#--            </div>-->
<#--        </div>-->

<#--    </div>-->
<#--    <div class="col-md-3">-->
<#--        <div class="panel panel-info panel-colorful media middle pad-all" id="currentRD" style="cursor: pointer;">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="pli-file-word icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--&lt;#&ndash;                <p class="text-2x mar-no text-semibold">1</p>&ndash;&gt;-->
<#--                <p class="mar-no">Current RD</p>-->
<#--            </div>-->
<#--        </div>-->

<#--    </div>-->
<#--    <div class="col-md-3">-->
<#--        <#if trainingMatrixFile?has_content>-->
<#--        <div class="panel panel-mint panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="pli-file-horizontal-text icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="mar-no"><button class="btn btn-trans" type="button" data-target="#matrix-modal" data-toggle="modal" data-title="${trainingMatrixFile.title}">${trainingMatrixFile.title}</button> </p>-->
<#--            </div>-->
<#--        </div>-->
<#--        </#if>-->
<#--    </div>-->
<#--    <script>-->
<#--        $(function() {-->
<#--            $("#currentSOP").click(function() {-->
<#--                location.href='/common/download/SOP/index';-->
<#--            });-->
<#--            $("#currentRD").click(function() {-->
<#--                location.href='/common/download/RD/index';-->
<#--            });-->
<#--            // $("#trainingMatrix").click(function() {-->
<#--            //     location.href='/common/download/trainingMatrix';-->
<#--            // });-->
<#--        });-->
<#--    </script>-->

    <!-- insert just before the closing body tag </body> -->
<#--    <script src='/path-to-your-javascript-file/pdfobject.js'></script>-->


<#--    <div class="col-md-3">-->
<#--        <div class="panel panel-mint panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="pli-camera-2 icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Photos</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--    <div class="col-md-3">-->
<#--        <div class="panel panel-purple panel-colorful media middle pad-all">-->
<#--            <div class="media-left">-->
<#--                <div class="pad-hor">-->
<#--                    <i class="pli-video icon-3x"></i>-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="media-body">-->
<#--                <p class="text-2x mar-no text-semibold">241</p>-->
<#--                <p class="mar-no">Videos</p>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->

<#--</div>-->

<div class="panel">
<#--    <div class="panel-heading">-->
<#--        <h3 class="panel-title">공지사항</h3>-->
<#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <#if reviewCount gt 0>
        <div class="alert alert-success">
            <button class="close" data-dismiss="alert"><i class="pci-cross pci-circle"></i></button>
            <strong><i class="pli-bell"></i> 전자결재</strong> 검토가 필요한 요청 <a href="/approval/box/reviewer/request" class="alert-link">${reviewCount}건</a> 있습니다.
        </div>
        </#if>
        <#if approvalCount gt 0>
        <div class="alert alert-info">
            <button class="close" data-dismiss="alert"><i class="pci-cross pci-circle"></i></button>
            <strong><i class="pli-bell"></i> 전자결재</strong> 승인이 필요한 요청 <a href="/approval/box/approver/request" class="alert-link">${approvalCount}건</a> 있습니다.
        </div>
        </#if>
        <@security.authorize access="hasAnyAuthority('ADMIN')">
        <#if signUpRequestCount gt 0>
            <div class="alert alert-purple">
                <button class="close" data-dismiss="alert"><i class="pci-cross pci-circle"></i></button>
                <strong><i class="pli-bell"></i> 회원가입</strong> 승인이 필요한 요청 <a href="/admin/authority/users" class="alert-link">${signUpRequestCount!'0'}건</a> 있습니다.
            </div>
        </#if>
        </@security.authorize>
        <div class="pad-btm form-inline">
            <div class="row">

<#--                <script>-->
<#--                    $(document).ready(function() {-->
<#--                        $.ajax({-->
<#--                            url: "/common/organization/chart",-->
<#--                            method: "GET",-->
<#--                            data: { r : Math.random() },-->
<#--                            dataType: "json"-->
<#--                        }).done(function(response) {-->
<#--                            // $('#org-jstree-json').jstree('destroy');-->
<#--                            $('#org-jstree-json').jstree({-->
<#--                                'core': {-->
<#--                                    // 'check_callback' : true,-->
<#--                                    // 'dblclick_toggle' : false,-->
<#--                                    'data': response-->
<#--                                },-->
<#--                                'plugins': ['types'],-->
<#--                                'types' : {-->
<#--                                    'company' : {-->
<#--                                        'icon' : 'pli-building icon-lg'-->
<#--                                    },-->
<#--                                    'dept' : {-->
<#--                                        'icon' : 'pli-folders icon-lg text-warning'-->
<#--                                    },-->
<#--                                    'team' : {-->
<#--                                        'icon' : 'pli-folder text-warning'-->
<#--                                    },-->
<#--                                    'user_mng_male' : {-->
<#--                                        'icon' : 'pli-business-man icon-lg'-->
<#--                                    },-->
<#--                                    'user_mng_female' : {-->
<#--                                        'icon' : 'pli-business-woman icon-lg'-->
<#--                                    },-->
<#--                                    'user_male' : {-->
<#--                                        'icon' : 'pli-male icon-lg'-->
<#--                                    },-->
<#--                                    'user_female' : {-->
<#--                                        'icon' : 'pli-female icon-lg'-->
<#--                                    }-->
<#--                                }-->
<#--                            }).bind('select_node.jstree', function (event, data) {-->
<#--                                var jsTreeData = {};-->
<#--                                for (var i = 0; i < data.selected.length; i++) {-->
<#--                                    var dataJson = data.instance.get_node(data.selected[i]).data;-->
<#--                                    var code = dataJson.code;    //data 에서 a 가져오기-->
<#--                                }-->
<#--                            }).bind('dblclick.jstree', function(e) {-->
<#--                                var instance = $.jstree.reference(this);-->
<#--                                var node = instance.get_node(e.target);-->
<#--                                var jsonData = node.data;-->

<#--                                if(jsonData.type == 'user') {-->
<#--                                    alert('empNo:' + jsonData.empNo + ',korName:' + jsonData.empName);-->
<#--                                }-->
<#--                                // console.log(event);-->
<#--                                // var node = $(event.target).closest("li");-->
<#--                                // console.log(node);-->
<#--                                // var data = node.data("jstree");-->
<#--                                // console.log(data);-->
<#--                            });-->
<#--                        });-->
<#--                    });-->
<#--                </script>-->
<#--                <div id="org-jstree-json">-->

<#--                </div>-->
                <div class="col-sm-6 table-toolbar-left">
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                    <a class="btn btn-primary" href="javascript:goToURL('/notice/new')"><i class="pli-pencil"></i> 공지 작성</a>
                    </@security.authorize>
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
<#--                        <button class="btn btn-default"><i class="fa fa-search fa-lg"></i></button>-->
<#--                        <div class="btn-group dropdown">-->
<#--                            <button data-toggle="dropdown" class="btn btn-default dropdown-toggle">-->
<#--                                <i class="pli-list-view"></i>-->
<#--                                <span class="caret"></span>-->
<#--                            </button>-->
<#--                            <ul role="menu" class="dropdown-menu dropdown-menu-right">-->
<#--                                <#list 1..6 as i>-->
<#--                                    <#assign viewSize = i * 5/>-->
<#--                                <li><a href="?view=${viewSize}">${viewSize}</a></li>-->
<#--                                </#list>-->
<#--                            </ul>-->
<#--                        </div>-->
                    </div>
                </div>
            </div>
        </div>
        <@pagination.bind noticeList/>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th>Title</th>
                    <th class="text-center" style="width:30px;"><i class="fa fa-floppy-o"></i> </th>
                    <th class="text-center" style="width:160px;">등록일</th>
                    <th class="text-center" style="width:140px">작성자</th>
<#--                    <th class="text-center" style="width:120px">Status</th>-->
<#--                    <th class="text-center" style="width:120px">#actions</th>-->
                </tr>
                </thead>
                <tbody>
                <#if trainingMatrixFile?has_content>
                <tr class="bg-gray-light">
                    <td class="text-center"><i class="fa fa-exclamation-circle text-warning"></i></td>
                    <td><a class="btn-link" target="_blank" href="/viewer/trainingMatrix" data-toggle="modal" data-title="${trainingMatrixFile.title}">${trainingMatrixFile.title}</a></td>
                    <td class="text-center"></td>
                    <td class="text-center"><span class="text-muted">${trainingMatrixFile.createdDate?string('yyyy-MM-dd')}</span></td>
                    <td class="text-center">System</td>
<#--                    <td class="text-center">-->
<#--                        --->
<#--                    </td>-->
                </tr>
                </#if>
                <#if topNoticeList?has_content>
                <#list topNoticeList as notice>
                    <tr class="bg-gray-light">
                        <td class="text-center"><i class="fa fa-exclamation-circle text-warning"></i></td>
                        <td><a class="btn-link" href="/notice/${notice.id}">${notice.title}</a></td>
                        <td class="text-center"><#if notice.attachFiles?has_content><i class="fa fa-floppy-o"></i></#if></td>
                        <td class="text-center"><span class="text-muted">${notice.createdDate?string('yyyy-MM-dd')}</span></td>
                        <td class="text-center">${notice.createdBy?default('System')}</td>
<#--                        <td class="text-center">-->
<#--                            <#if notice.postStatus == 'NONE'>-->
<#--                                --->
<#--                            <#else>-->
<#--                                <span class="label label-info">Sent</span>-->
<#--                            </#if>-->
<#--                        </td>-->
                        <#--                    <td class="text-center">-->
                        <#--                        <a href="/notice/${notice.id}/edit" class="btn-link">edit</a>-->
                        <#--                    </td>-->
                    </tr>
                </#list>
                </#if>
                <#if noticeList.content?has_content>
                <#list noticeList.content as notice>
                <tr>
                    <td class="text-center">${(noticeList.totalElements - (noticeList.size * noticeList.number)) - notice?index}</td>
                    <td><a class="btn-link" href="/notice/${notice.id}<@pagination.getQueryString/>">${notice.title}</a></td>
                    <td class="text-center"><#if notice.attachFiles?has_content><i class="fa fa-floppy-o"></i></#if></td>
                    <td class="text-center"><span class="text-muted">${notice.createdDate?string('yyyy-MM-dd')}</span></td>
                    <td class="text-center">${notice.createdBy?default('System')}</td>
<#--                    <td class="text-center">-->
<#--                <#if notice.postStatus == 'NONE'>-->
<#--                    --->
<#--                <#else>-->
<#--                    <span class="label label-info">Sent</span>-->
<#--                </#if>-->
<#--                    </td>-->
<#--                    <td class="text-center">-->
<#--                        <a href="/notice/${notice.id}/edit" class="btn-link">edit</a>-->
<#--                    </td>-->
                </tr>
                </#list>
                <#else>
                <tr>
                    <td colspan="5" class="text-center">등록된 공지사항이 없습니다.</td>
                </tr>
                </#if>
                </tbody>
            </table>

        </div>
        <@pagination.default/>

<#--        <nav style="float:left;">-->
<#---->
<#--        </nav>-->
<#--        <@pagination.counter />-->
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
    <script>
        $(document).ready(function() {
            $('#matrix-modal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget); // Button that triggered the modal
                var title = button.data('title'); // Extract info from data-* attributes
                var modal = $(this);
                modal.find('.modal-title').text(title);
                // var options = {
                //     pdfOpenParams: { toolbar: '0', statusbar: '0', messages: '0', scrollbar: '1', view:'FitH'}
                // };

                // var $container = $("#pdfViewer");
                $("#pdfViewer").html("문서를 불러오는 중입니다...");
                $.ajax({
                    url: "/ajax/common/viewTrainingMatrix",
                    method: "GET",
                    data: { r : Math.random() },
                    dataType: "html"
                }).done(function(response) {
                    $("#pdfViewer").html(response);
                });
                // PDFObject.embed("/common/download/trainingMatrix", $container, options);
            });
        });
    </script>
    <div class="modal fade" id="matrix-modal" role="dialog" tabindex="-1" aria-labelledby="matrix-modal" aria-hidden="true">
        <div class="modal-dialog modal-fullsize">
            <div class="modal-content modal-fullsize">

                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title"></h4>
                </div>

                <!--Modal body-->
                <div class="modal-body">
                    <div id="pdfViewer" style="width:100% !important;min-height:550px !important;">
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