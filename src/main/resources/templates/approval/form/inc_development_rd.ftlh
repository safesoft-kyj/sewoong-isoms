<#import "/spring.ftl" as spring />
<#if approval.sopRdRequestForm.rdDevelopmentDocs?has_content>
    <#list approval.sopRdRequestForm.rdDevelopmentDocs as c>
        <tr>
            <td><@spring.formInput "approval.sopRdRequestForm.rdDevelopmentDocs[${c?index}].docId" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td><@spring.formInput "approval.sopRdRequestForm.rdDevelopmentDocs[${c?index}].title" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td><@spring.formInput "approval.sopRdRequestForm.rdDevelopmentDocs[${c?index}].version" "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            <td>
                <button type="button" class="btn btn-default" data-type="rd" name="removeDevelopment"<#if c?index == 0> disabled="disabled"</#if> data-id="${c?index}"><i class="fa fa-times"></i></button>
            </td>
        </tr>
    </#list>
<#else>
    <tr>
        <td><@spring.formInput "approval.sopRdRequestForm.rdDevelopmentDocs[0].docId" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><@spring.formInput "approval.sopRdRequestForm.rdDevelopmentDocs[0].title" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><@spring.formInput "approval.sopRdRequestForm.rdDevelopmentDocs[0].version" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td><button type="button" class="btn btn-default" data-type="rd" name="removeDevelopment" disabled="disabled" data-id="0"><i class="fa fa-times"></i></button></td>
    </tr>
</#if>
<tr>
    <td colspan="5"><button class="btn btn-block btn-primary" data-type="rd" name="addDevelopment" type="button"><i class="fa fa-plus"></i> Add</button></td>
</tr>