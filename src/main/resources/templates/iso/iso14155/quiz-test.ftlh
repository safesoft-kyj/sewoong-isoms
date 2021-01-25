<div class="panel">
    <div class="panel-body">

        <#list quiz.quizQuestions as q>
            <div class="panel">
                <div class="panel-heading">
                    <h5>${q?index + 1}. ${q.text}</h5>
                </div>
                <div class="panel-body">
                    <form>
                        <#list q.answers as a>
                            <div class="form-group">
                                <label class="control-label"><input type="checkbox"> ${a.text}</label>
                            </div>
                        </#list>
                    </form>
                </div>
            </div>
            <hr/>

        </#list>
        <div class="text-right">
            <button class="btn btn-info" onclick="document.location.reload();"><i class="fa fa-refresh"></i> Refresh</button>
            <a href="../.." class="btn btn-link"><i class="fa fa-chevron-left"></i> Back</a>
        </div>
    </div>
</div>