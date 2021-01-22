<#import "/spring.ftl" as spring />
<html>
<head>
    <title>ISO 14155 작성</title>
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

            $('#topViewEndDate').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                clearBtn: true,
                todayBtn: "linked",
                todayHighlight: true,
                zIndexOffset: 1000
            });
            $('#isoTrainingPeriod\\.startDate').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                clearBtn: true,
                todayBtn: "linked",
                todayHighlight: true,
                zIndexOffset: 1000,
                startDate: new Date()
            });
            $('#isoTrainingPeriod\\.endDate').datepicker({
                format: 'yyyy-mm-dd',
                autoclose: true,
                clearBtn: true,
                todayBtn: "linked",
                todayHighlight: true,
                zIndexOffset: 1000,
                startDate: new Date()
            });

            $('#userIds').select2({width: '100%'});

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
            // });\

            $("#training").change(function (e) {
                if ($(this).is(":checked")) {
                    $("#formTraining").css("display", "block");
                } else {
                    $("#formTraining").css("display", "none");
                }
            });
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
    <div class="row">
        <div class="col-sm-9">
            <div class="panel">
                <div class="panel-body">
                    <@spring.formHiddenInput "iso.id"/>
                    <!-- Edit Case -->
                    <#assign disabled = iso.id?has_content?then('disabled', '') />
                    ]\<div class="form-group">
                        <@spring.formCheckbox "iso.training" "class='form-checkbox' ${disabled}" />
                        <label class="control-label" for="training">Training 등록</label>
                    </div>

                    <!-- Edit인데 training 체크를 한 경우, -->
                    <div id="formTraining" style="display:${iso.training?then('block', 'none')};">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="form-group text-center">
                                    <@spring.formCheckbox "iso.isoTrainingMatrix.trainingAll" "class='form-checkbox'" />
                                    <label class="control-label" for="isoTrainingPeriod.trainingAll">전부</label>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <label class="control-label col-sm-3" for="isoTrainingPeriod.trainingAll">교육 참석 대상자</label>
                                <div class="col-sm-9">
                                    <div class="form-group">
                                        <@spring.formMultiSelect "iso.userIds" userMap "class='form-control'" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-6">
                                <label class="control-label col-sm-3" style="margin-top: 7px;">교육 기간</label>
                                <div class="form-group col-sm-4">
                                    <@spring.formInput "iso.isoTrainingPeriod.startDate" "class='form-control' placeholder='Start Date'" />
                                </div>
                                <div class="form-group col-sm-1 text-center">
                                    <span class="text-2x">~</span>
                                </div>
                                <div class="form-group col-sm-4">
                                    <@spring.formInput "iso.isoTrainingPeriod.endDate" "class='form-control' placeholder='End Date'" />
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <label class="control-label col-sm-3" style="margin-top: 7px;">교육 시간</label>
                                <div class="form-group col-sm-9">
                                    <@spring.formInput "iso.hour" "class='form-control' placeholder='Hour (#.##)' step='.01'" "number"/>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <@spring.formInput "iso.title" "class='form-control input-lg' placeholder='Title'"/>
                        <@spring.showErrors "" "color:red;"/>
                    </div>

                    <div class="form-group">
                        <@spring.formTextarea "iso.content" "class='form-control'"/>
                        <@spring.showErrors "" "color:red;"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-3">
            <div class="panel">
                <div class="panel-body">
                    <p class="text-main text-bold text-uppercase">
                        첨부파일(ISO)<#if iso.attachFiles?has_content>(${iso.attachFiles?size})</#if></p>
                    <#if iso.attachFiles?has_content>
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
                                <#list iso.attachFiles as attachFile>
                                    <tr>
                                        <td>${attachFile?index + 1}</td>
                                        <td>${attachFile.originalFileName}</td>
                                        <td class="text-center">
                                            <@spring.formCheckbox "iso.attachFiles[" + attachFile?index + "].deleted", "class='form-checkbox'"/>
                                            <#--                                <input type="checkbox" class="form-checkbox" value="${attachFile.id}"/>-->
                                        </td>
                                    </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                    <#else>
                        <@spring.formHiddenInput "iso.attachFiles" />
                    </#if>
                    <div class="form-group">
                        <input name="uploadingFiles" class="form-control" type="file" accept=".pdf">
                        <#--                    <@spring.showErrors "" "color:red;"/>-->
                    </div>
                    <hr>
                    <p class="text-main text-bold text-uppercase">상단공지</p>
                    <#--                <div class="form-horizontal">-->
                    <#--                    <div class="form-group">-->
                    <#--                        <label class="col-sm-5 control-label text-left">종료일</label>-->
                    <#--                        <div class="col-sm-7">-->
                    <#--                            <div class="input-group date">-->
                    <#--                                <@spring.formInput "iso.topViewEndDate" "class='form-control'"/>-->
                    <#--                                <span class="input-group-addon"><i class="pli-calendar-4"></i></span>-->
                    <#--                            </div>-->
                    <#--                            <@spring.showErrors "" "color:red;"/>-->
                    <#--                        </div>-->
                    <#--                    </div>-->
                    <#--                </div>-->
                    <hr/>
                    <div class="text-right">
                        <button type="button" class="btn btn-link" onclick="history.back();">Cancel</button>
                        <button type="submit" class="btn btn-primary"> Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
