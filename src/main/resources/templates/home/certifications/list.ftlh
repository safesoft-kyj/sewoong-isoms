<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>e-SOP > 인증현황</title>
    <#--    <script src="/static/plugins/jstree/jstree.min.js"></script>-->
    <#--    <link href="/static/plugins/jstree/themes/default/style.min.css" rel="stylesheet">-->
    <#--    <script src='/static/js/pdfobject.min.js'></script>-->

    <#-- PDF Viwer -->
<#--    <link href="/static/js/pdfjs/web/viewer.css" rel="stylesheet">-->
<#--    <script src="/static/js/pdfjs/web/viewer.js"></script>-->

    <style>
        .modal-dialog.modal-fullsize {
            width: 80%;
            height: 800px;
            margin: 3px;
            padding: 0;
        }

        #certification-html {
            height: 700px;
        }
    </style>
    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="/static/plugins/bootbox/bootbox.min.js"></script>
</head>
<body>
<div class="panel">
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <a class="btn btn-primary" href="javascript:goToURL('/certifications/new')"><i
                                    class="pli-pencil"></i>인증현황 작성</a>
                    </@security.authorize>
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
        <@pagination.bind certificationList/>
        <div class="table-responsive">
            <table class="table">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th>Title</th>
                    <th class="text-center" style="width:160px;">인증일자</i> </th>
                    <th class="text-center" style="width:160px;">등록일</th>
                    <th class="text-center" style="width:140px">작성자</th>
                    <th class="text-center" style="width:180px">#actions</th>
                </tr>
                </thead>
                <tbody>
                <#if certificationList.content?has_content>
                    <#list certificationList.content as certification>
                        <tr>
                            <td class="text-center">${(certificationList.totalElements - (certificationList.size * certificationList.number)) - certification?index}</td>
                            <td>${certification.title}</td>
                            <td class="text-center"><a href="#certification-modal" data-toggle="modal"
                                                       data-id="${certification.id}" data-lang="eng"
                                                       class="btn-link">${certification.certDate?string('yyyy-MM-dd')}</a>
                            </td>
                            <td class="text-center"><span
                                        class="text-muted">${certification.createdDate?string('yyyy-MM-dd')}</span></td>
                            <td class="text-center">${certification.createdBy?default('System')}</td>
<#--                            <td class="text-center">-->
<#--                                <#if notice.postStatus == 'NONE'>-->
<#--                                    --->
<#--                                <#else>-->
<#--                                    <span class="label label-info">Sent</span>-->
<#--                                </#if>-->
<#--                            </td>-->
                            <@security.authorize access="hasAnyAuthority('ADMIN')">
                            <td class="text-center">
                                <a href="/certifications/${certification.id}/edit" class="btn btn-mint"><i class="pli-edit"></i> 수정</a>
                                <button class="btn btn-danger" id="removeBtn"><i class="pli-trash icon-lg"></i> 삭제</button>
                                <form id="removeForm" action="/certifications/${certification.id}" method="post">
                                    <input type="hidden" name="_method" value="delete">
                                </form>
                            </td>
                            </@security.authorize>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="5" class="text-center">등록된 인증현황이 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <@pagination.default/>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>

<#-- PDF Object -->
<script src='/static/js/pdfjs/control/pdfobject.js'></script>

<script>
    $(document).ready(function () {

        $(document).on('click', "#removeBtn", function() {
            bootbox.confirm("정말로 삭제 하시겠습니까?", function (result) {
                if (result) {
                    $("#removeForm").submit();
                } else {
                    // $.niftyNoty({
                    //     type: 'danger',
                    //     icon: 'pli-cross icon-2x',
                    //     message: 'User declined dialog.',
                    //     container: 'floating',
                    //     timer: 5000
                    // });
                }
            });
        });

        $('#certification-modal').on('show.bs.modal', function (event) {
            $("#certification-html").html('<string>내용을 불러오는 중입니다...</string>');
            var button = $(event.relatedTarget); // Button that triggered the modal
            var certId = button.data('id');

            $.ajax({
                url: '/ajax/certifications/isFile/' + certId,
                method: 'get',
                data: {r: Math.random()},
                success: function (isFile) {
                    if(isFile) { // File이 있으면
                        var options = {
                            height: "800",
                            page: '1',
                            pdfOpenParams: {
                                view: 'FitV',
                                pagemode: 'thumbs',
                                search: 'lorem ipsum'
                            },
                            forcePDFJS: true, // 강제로 PDF Viewer를 실행시키는 옵션
                            PDFJS_URL: "/static/js/pdfjs/web/viewer.html" //PDF.js의 PDF viewer를 viewer로 지정.
                        };

                        //제일 처음 열리는 pdf파일 경로 설정.
                        PDFObject.embed("/certifications/download/" + certId , "#certification-html", options);
                    } else { // File이 없으면 첨부 파일 없다고 메세지 띄우기
                        $("#certification-html").html("첨부된 인증 파일이 존재하지 않습니다.");
                    }
                }
            });
        });
    });
</script>
<!-- Certification Viewer -->
<div class="modal fade" id="certification-modal" role="dialog" tabindex="-1" aria-labelledby="certification-modal"
     aria-hidden="true">
    <div class="modal-dialog modal-fullsize">
        <div class="modal-content">

            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title">ISO Certification</h4>
            </div>

            <!--Modal body-->
            <div class="modal-body">
                <div class="table-responsive">
                    <div id="certification-html" style="" >내용을 불러오는 중입니다...</div>
                </div>
            </div>

            <!--Modal footer-->
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button"><i class="fa fa-times"></i> Close
                </button>
            </div>
        </div>
    </div>
</div>
<!-- Certification viewer end -->
</div>
</body>
</html>