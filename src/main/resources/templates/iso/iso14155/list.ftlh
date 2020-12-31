<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>CAUH ISO Managerment System > ISO-14155</title>
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

<div class="panel">
<#--    <div class="panel-heading">-->
<#--        <h3 class="panel-title">공지사항</h3>-->
<#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
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