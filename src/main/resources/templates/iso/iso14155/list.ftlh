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
                        <a class="btn btn-primary" href="javascript:goToURL('/iso-14155/new')"><i
                                    class="pli-pencil"></i> ISO-14155 등록</a>
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
        <@pagination.bind isoList/>
<#--        <div class="table-responsive">-->
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th class="text-center" style="width:90px;">Type</th>
                    <th>Title</th>
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                    <th class="text-center" style="width:90px;">시험</th>
                    </@security.authorize>
                    <th class="text-center" style="width:200px;">교육 일정</th>
                    <th class="text-center" style="width:160px;">등록일</th>
                    <th class="text-center" style="width:140px;">작성자</th>
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <th class="text-center" style="width:140px">Active</th>
                    </@security.authorize>
                </tr>
                </thead>
                <tbody>
                <#if topISOList?has_content>
                    <#list topISOList as iso>
                        <tr class="bg-gray-light">
                            <td class="text-center"><i class="fa fa-exclamation-circle text-warning"></i></td>
                            <td class="text-center"><span class="label label-${iso.training?then('info','warning')}">${iso.training?then('Training','Board')}</span></td>
                            <td><a class="btn-link" href="/iso-14155/${iso.id}">${iso.title}</a></td>
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <!--===================================================-->
                                <div class="btn-group btn-group-xs dropdown">
                                    <button class="btn btn-${(iso.quiz?has_content)?then('info', 'default')} btn-active-pink dropdown-toggle dropdown-toggle-icon" data-toggle="dropdown" type="button">
                                        Quiz <i class="dropdown-caret"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="/iso-14155/${iso.id}/quiz"><i class="fa fa-pencil"></i> Edit</a></li>
                                        <li><a href="/iso-14155/${iso.id}/quiz/upload"><i class="fa fa-upload"></i> Template Upload</a></li>
                                        <#if iso.quiz?has_content>
                                            <li class="divider"></li>
                                            <li><a href="/iso-14155/${iso.id}/quiz/test"><i class="fa fa-check-square-o"></i> Test</a></li>
                                        </#if>
                                    </ul>
                                </div>
                                <!--===================================================-->
                            </td>
                            </@security.authorize>
                            <td class="text-center"><span class="text-info">${iso.training?then(iso.trainingDate, '-')}</span></td>
                            <td class="text-center"><span class="text-muted">${iso.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${iso.createdBy?default('System')}</td>
                            <td class="text-center">
                                <#if iso.training>
                                    <#if iso.active>
                                        <span class="label label-primery">Activated</span>
                                    <#else>
                                        <span class="label label-purple">Active</span>
                                    </#if>
                                <#else>
                                    -
                                </#if>
                            </td>
                        </tr>
                    </#list>
                </#if>
                <#if isoList.content?has_content>
                    <#list isoList.content as iso>
                        <tr>
                            <td class="text-center">${(isoList.totalElements - (isoList.size * isoList.number)) - iso?index}</td>
                            <td class="text-center"><span class="label label-${iso.training?then('info','warning')}">${iso.training?then('Training','Board')}</span></td>
                            <td><a class="btn-link" href="/iso-14155/${iso.id}<@pagination.getQueryString/>">${iso.title}</a></td>
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <#if iso.training>
                                <!--===================================================-->
                                <div class="btn-group btn-group-xs dropdown">
                                    <button class="btn btn-${(iso.quiz?has_content)?then('info', 'default')} btn-active-pink dropdown-toggle dropdown-toggle-icon" data-toggle="dropdown" type="button">
                                        Quiz <i class="dropdown-caret"></i>
                                    </button>
                                    <ul class="dropdown-menu">
                                        <li><a href="/iso-14155/${iso.id}/quiz"><i class="fa fa-pencil"></i> Edit</a></li>
                                        <li><a href="/iso-14155/${iso.id}/quiz/upload"><i class="fa fa-upload"></i> Template Upload</a></li>
                                        <#if iso.quiz?has_content>
                                            <li class="divider"></li>
                                            <li><a href="/iso-14155/${iso.id}/quiz/test"><i class="fa fa-check-square-o"></i> Test</a></li>
                                        </#if>
                                    </ul>
                                </div>
                                <!--===================================================-->
                                <#else>
                                    -
                                </#if>
                            </td>
                            </@security.authorize>
                            <td class="text-center"><span class="text-info">${iso.trainingDate!'-'}</span></td>
                            <td class="text-center"><span class="text-muted">${iso.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${iso.createdBy?default('System')}</td>
                            <td class="text-center">
                                <#if iso.training>
                                    <#if iso.active>
                                        <span class="label label-primery">Activated</span>
                                    <#else>
                                        <span class="label label-purple">Active</span>
                                    </#if>
                                <#else>
                                    -
                                </#if>
                            </td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="5" class="text-center">등록된 ISO-14155가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
<#--        </div>-->
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
    $(document).ready(function () {
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
                data: {r: Math.random()},
                dataType: "html"
            }).done(function (response) {
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