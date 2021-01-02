<#import "/spring.ftl" as spring />
<html>
<head>
    <title>인증현황 작성</title>
    <link href="/static/plugins/summernote/summernote.min.css" rel="stylesheet">
    <script src="/static/plugins/summernote/summernote.min.js"></script>
    <link href="/static/plugins/dropzone/dropzone.min.css" rel="stylesheet">
    <script src="/static/plugins/dropzone/dropzone.min.js"></script>
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function () {
            if ($('#content').length) {
                $('#content').summernote({
                    height: 430
                    , focus: true
                    , toolbar: [
                        // [groupName, [list of button]]
                        ['font2', ['fontname', 'fontsize', 'height']],
                        ['color', ['color']],
                        ['style', ['bold', 'italic', 'underline', 'clear']],
                        ['font1', ['strikethrough', 'superscript', 'subscript']],
                        ['table', ['table']],
                        ['para', ['ul', 'ol', 'paragraph']],
                        ['insert', ['link', 'picture', 'video', 'hr']]
                    ]
                    // airMode: true
                });
            }

            $('#certDate').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                clearBtn: true,
                todayBtn: "linked",
                todayHighlight: true
            });
            // $('#dropzone').dropzone({
            //     //url: '/file/post',
            //     autoProcessQueue: false,
            //     addRemoveLinks: true,
            //     maxFiles: 1,
            //     init: function() {
            //         var myDropzone = this;
            //         myDropzone.on('maxfilesexceeded', function(file) {
            //             this.removeAllFiles();
            //             this.addFile(file);
            //         });
            //     }
            // });
        });
    </script>
</head>
<body>
<!-- Contact Toolbar -->
<!---------------------------------->

<#--<div class="row pad-btm">-->
<#--    <div class="col-sm-6 col-sm-offset-6 toolbar-right text-right">-->
<#--&lt;#&ndash;        <button class="btn btn-default">Preview</button>&ndash;&gt;-->
<#--&lt;#&ndash;        <button class="btn btn-default">Draft</button>&ndash;&gt;-->
<#--        <button class="btn btn-primary">Save</button>-->
<#--    </div>-->
<#--</div>-->
<!---------------------------------->
<form role="form" method="post" enctype="multipart/form-data">
    <div class="panel">
        <div class="panel-body">
            <div class="form-group">
                <@spring.formHiddenInput "isoCertification.id"/>
                <p class="text-main text-bold text-uppercase">제목</p>
                <@spring.formInput "isoCertification.title" "class='form-control input-lg' placeholder='Title'"/>
                <@spring.showErrors "" "color:red;"/>
            </div>

            <p class="text-main text-bold text-uppercase">
                첨부파일<#if isoCertification.attachFiles?has_content>(${isoCertification.attachFiles?size})</#if></p>
            <#if isoCertification.attachFiles?has_content>
                <div class="form-group">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>File</th>
                            <th class="text-warning text-center"><i class="fa fa-trash-o fa-lg"></i></th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list isoCertification.attachFiles as attachFile>
                            <tr>
                                <td>${attachFile?index + 1}</td>
                                <td>${attachFile.originalFileName}</td>
                                <td class="text-center">
                                    <@spring.formCheckbox "isoCertification.attachFiles[" + attachFile?index + "].deleted", "class='form-checkbox'"/>
                                    <#--                                <input type="checkbox" class="form-checkbox" value="${attachFile.id}"/>-->
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </#if>
            <div class="form-group">
                <input name="uploadingFiles" class="form-control" type="file" accept=".pdf">
            </div>
            <hr>
            <p class="text-main text-bold text-uppercase">인증일자</p>
            <div class="form-horizontal">
                <div class="form-group">
                    <div class="input-group date">
                        <@spring.formInput "isoCertification.certDate" "class='form-control'"/>
                        <span class="input-group-addon"><i class="pli-calendar-4"></i></span>
                    </div>
                    <@spring.showErrors "" "color:red;"/>
                </div>
            </div>
            <hr/>
            <div class="text-right">
                <button type="button" class="btn-link" onclick="history.back();">Cancel</button>
                <button type="submit" class="btn btn-primary"> Save</button>
            </div>
        </div>
    </div>
</form>
</body>
</html>
