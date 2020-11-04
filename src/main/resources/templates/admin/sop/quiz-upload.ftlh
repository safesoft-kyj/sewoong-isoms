<#import "/spring.ftl" as spring />
<script>
    $(document).ready(function() {
        $("#uploadForm").submit(function(e) {
           if(!$("#quizTemplate").val()) {
               alert('템플릿 파일을 선택해 주세요.');
               e.preventDefault();
               return false;
           }
        });
    });
</script>
<form method="post" id="uploadForm" enctype="multipart/form-data" action="../quiz">
    <input type="hidden" name="_method" value="put"/>
    <div class="panel">
        <div class="panel-heading">
            <h3 class="panel-title"><i class="fa fa-question-circle-o"></i> ${docId} 퀴즈 Template Upload</h3>

        </div>
        <div class="panel-body">
            <input type="file" class="form-control" id="quizTemplate" name="quizTemplate" accept=".xlsx">
        </div>
        <div class="panel-footer text-right">
            <a class="btn btn-link" href="../..">취소</a>
            <button class="btn btn-primary" type="submit">Upload</button>
        </div>
    </div>
</form>