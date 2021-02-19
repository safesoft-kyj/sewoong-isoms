<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">
<#--    <script>-->
<#--        function dateFormat(value, row, index) {-->
<#--            let date = new Date(value);-->

<#--            return [-->
<#--                date.getFullYear().toString(),-->
<#--                (date.getMonth()+1) < 10? '0' + (date.getMonth()+1).toString():(date.getMonth()+1).toString(),-->
<#--                date.getDate() < 10? '0' + date.getDate().toString():date.getDate().toString(),-->
<#--            ].join('-');-->
<#--        }-->

<#--        function revType(value, row, index) {-->
<#--            return [-->
<#--                '<span class="label label-default">',-->
<#--                    value,-->
<#--                '</span>'-->
<#--            ].join('');-->
<#--        }-->

<#--        /**-->
<#--         * 변환된 문자열인지 확인 후 변환.-->
<#--         */-->
<#--        function changedValue(value, row, index, field){-->
<#--            let column = field.split(".")[1];-->
<#--            if(row.propertiesChanged.includes(column)) {-->
<#--                return ['<p style="color:red">',value,'</p>'].join('');-->
<#--            } else {-->
<#--                return value;-->
<#--            }-->
<#--        }-->

<#--        /**-->
<#--         * 변환된 문자열인지 확인 후 변환.-->
<#--         */-->
<#--        function changedRole(value, row, index, field){-->
<#--            let column = field.split(".")[1];-->

<#--            console.log(value);-->

<#--            if(row.propertiesChanged.includes(column)) {-->
<#--                return ['<p style="color:red">',value,'</p>'].join('');-->
<#--            } else {-->

<#--                return [-->
<#--                    value-->
<#--                ].join('');-->
<#--            }-->
<#--        }-->

<#--    </script>-->
<#--    <table id="auditTable"-->
<#--           data-toggle="table"-->
<#--           data-loading-template="loading"-->
<#--           data-url="/admin/ajax/change-control/account"-->
<#--           data-show-columns="true"-->
<#--           data-show-columns-search="true"-->
<#--           data-page-list="[5, 10, 20, 50]"-->
<#--           data-page-size="20"-->
<#--           data-pagination="true"-->
<#--           data-show-pagination-switch="true"-->
<#--           data-search="true"-->
<#--           data-search-on-enter-key="true">-->
<#--        <thead>-->
<#--        <tr class="bg-gray-light">-->
<#--            <th data-sortable="true" data-field="revisionEntity.rev">#Rev ID</th>-->
<#--            <th data-sortable="true" data-field="revisionEntity.revisionDate" data-formatter="dateFormat" >Revision Date</th>-->
<#--            <th data-sortable="true" data-field="revisionType" data-formatter="revType">Revision Type</th>-->
<#--            <th data-sortable="true" data-field="entity.id" data-formatter="changedValue">id</th>-->
<#--            <th data-sortable="true" data-field="entity.username" data-formatter="changedValue">User ID</th>-->
<#--            <th data-sortable="true" data-field="entity.name" data-formatter="changedValue">이름</th>-->
<#--            <th data-sortable="true" data-field="entity.deptName" data-formatter="changedValue">Department</th>-->
<#--            <th data-sortable="true" data-field="entity.teamName" data-formatter="changedValue">Team</th>-->
<#--            <th data-sortable="true" data-field="entity.email" data-formatter="changedValue">email</th>-->
<#--            <th data-sortable="true" data-field="entity.phone" data-formatter="changedValue">phone</th>-->
<#--            <th data-sortable="true" data-field="entity.birthDate" data-formatter="changedValue">birthDate</th>-->
<#--            <th data-sortable="true" data-field="entity.userJobDescriptions" data-formatter="changedRole">Role</th>-->
<#--&lt;#&ndash;            <th data-sortable="true" data-field="organization">State</th>&ndash;&gt;-->
<#--&lt;#&ndash;            <th data-sortable="true" data-visible="false" data-field="organization">계정 사용기한</th>&ndash;&gt;-->
<#--&lt;#&ndash;            <th data-sortable="true" data-visible="false" data-field="organization">계정 잠김여부</th>&ndash;&gt;-->
<#--&lt;#&ndash;            <th data-sortable="true" data-visible="false" data-field="organization">Enabled</th>&ndash;&gt;-->
<#--        </tr>-->
<#--        </thead>-->
<#--    </table>-->

<@pagination.bind auditList/>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center" style="width:80px">Rev No.</th>
            <th class="text-center">Revision Date</th>
            <th class="text-center">Revision Type</th>
            <th class="text-center">ID</th>
            <th class="text-center">Document Type</th>
            <th class="text-center">Document Category</th>
            <th class="text-center">Document No</th>
            <th class="text-center">Doc Id</th>
            <th class="text-center">title</th>
            <th class="text-center">SOP ID (Only RF)</th>
        </tr>
        </thead>
        <tbody>
        <#if auditList.content?has_content>
            <#list auditList.content as audit>
                <tr>
                    <#assign new = ''>
                    <#if audit.revisionType == 'ADD'>
                        <#assign revType = 'info' />
                        <#assign new = 'text-info text-bold bg-trans-dark'>
                    <#elseif audit.revisionType == 'MOD'>
                        <#assign revType = 'warning' />
                    <#elseif audit.revisionType == 'DEL'>
                        <#assign revType = 'danger' />
                    </#if>

                    <td class="text-center ${new}">${audit.revisionEntity.rev!''}</td>
                    <td class="text-center ${new}">${audit.revisionEntity.revisionDate?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td class="text-center ${new}"><span class="label label-${revType}">${audit.revisionType}</span></td>
                    <td class="text-center ${new}">${audit.entity.id}</td>
                    <td class="text-center ${new}"><span class="label label-${audit.entity.type=='SOP'?then('info','purple')}">${audit.entity.type}</span></td>
                    <td class="text-center ${new}">${audit.entity.name}</td>
                    <td class="text-center ${new}" style="${audit.contains('deptName')?then('color:red;', '')}">${audit.entity.deptName!''}</td>
                    <td class="text-center ${new}" style="${audit.contains('teamName')?then('color:red;', '')}">${audit.entity.teamName!''}</td>
                    <td class="text-center ${new}" style="${audit.contains('email')?then('color:red;', '')}">${audit.entity.email!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('phone')?then('color:red;', '')}">${audit.entity.phone!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('birthDate')?then('color:red;', '')}">${audit.entity.birthDate?has_content?then(audit.entity.birthDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('userJobDescriptions')?then('color:red;', '')}">${audit.entity.commaJobTitle!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('userStatus')?then('color:red;', '')}">${audit.entity.userStatus!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('credentialsExpiredDate')?then('color:red;', '')}">${audit.entity.credentialsExpiredDate?has_content?then(audit.entity.credentialsExpiredDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('accountExpiredDate')?then('color:red;', '')}">${audit.entity.accountExpiredDate?has_content?then(audit.entity.accountExpiredDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('accountNonLocked')?then('color:red;', '')}">${audit.entity.accountNonLocked?then('No', 'Locked')}</td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="7" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>
</div>
<@pagination.default />