<#import "/spring.ftl" as spring />
<#--
 * Copyright 2011 Alan Shaw
 *
 * http://www.freestyle-developments.co.uk
 * https://github.com/alanshaw/pagination-freemarker-macros
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
-->

<#--
 * Pagination macros.
 *
 * The use of these macros requires either an model attribute called "paginationData" to be set or if you want to call
 * it something else, or have more than one list of items that needs paginating, you can use the "bind" macro to set
 * the current pagination data that subsequent calls to other macros will use.
 *
 * Either way, the pagination data object is expected to contain (at least) the following properties:
 *
 * pageNumber -> The current page number
 * pageSize -> The number of items in each page
 * pagesAvailable -> The total number of pages
 * sortDirection -> The sorting direction (ascending or descending)
 * sortField -> The field currently sorted by
 *
 *
 * Page links consist of the current request url and a query string that looks like:
 *
 * ?field=&page=&size=&direction=
 *
 *
 * Localization messages are looked up using the following keys:
 *
 * pagination.first -> Text for the first page link (default "« First")
 * pagination.last -> Text for the last page link (default "Last »")
 * pagination.next -> Text for the next page link (default "Next »")
 * pagination.previous -> Text for the previous page link (default "« Previous")
 * pagination.counter -> Text for the page counter (default "{0} of {1}")
-->

<#--
 * Assign the current data to the object called "paginationData" if set.
-->
<#--<#if paginationData??>-->
<#--<#assign data = paginationData />-->
<#--</#if>-->

<#--
 * Bind pagination data to the current data set these macros are using.
-->
<#macro bind paginationData>
    <#assign data = paginationData />
</#macro>

<#macro default>
    <div class="row">
        <div class="col-lg-6">
            <div class="btn-group dropdown">
                <button data-toggle="dropdown" class="btn btn-default dropdown-toggle">
                    <i class="fa fa-list-ol"></i>
                    <span class="caret"></span>
                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-sm dropdown-menu-left">
                    <#list 1..6 as i>
                        <#assign viewSize = i * 5/>
                        <li><a href="<@getQueryString viewSize/>">${viewSize}</a></li>
                    </#list>
                </ul>
            </div>
        </div>
        <div class="col-lg-6 text-right">
            <ul class="pagination pagination-sm">
                <@first />
                <@previous />
                <@numbers />
                <@next />
                <@last />
            </ul>
        </div>
    </div>
    <!--Pagination with disabled and active states-->
    <!--===================================================-->
<#--    <div class="btn-group">-->
<#--    <button class="btn btn-default"><i class="fa fa-search fa-lg"></i></button>-->

<#--    </div>-->

    <!--===================================================-->
    <!--End Pagination with disabled and active states-->
</#macro>

<#--
 * Outputs the first page link
-->
<#macro first>
    <#if (data.number < 1)>
        <#local classAttr = "class=\"disabled\"" />
    <#else>
        <#local classAttr = "" />
    </#if>
<#--    <#local text>-->
<#--        <@spring.messageText "pagination.first", "« First" />-->
<#--    </#local>-->
    <@page 0, "", classAttr, "class='pli-arrow-left-2'"/>
</#macro>

<#--
 * Outputs the last page link
-->
<#macro last>
    <#if (data.number >= data.totalPages - 1)>
        <#local classAttr = "class=\"disabled\"" />
    <#else>
        <#local classAttr = "" />
    </#if>
<#--    <#local text>-->
<#--        <@spring.messageText "pagination.last", "Last »" />-->
<#--    </#local>-->
    <@page data.totalPages - 1, "", classAttr, "class='pli-arrow-right-2'"/>
</#macro>

<#--
 * Outputs the next page link
-->
<#macro next>
    <#if (data.number >= data.totalPages - 1)>
        <#local pageNumber = data.number />
        <#local classAttr = "class=\"disabled\"" />
    <#else>
        <#local pageNumber = data.number + 1 />
        <#local classAttr = "" />
    </#if>
<#--    <#local text>-->
<#--        <@spring.messageText "pagination.next", "Next »" />-->
<#--    </#local>-->
    <@page pageNumber, "", classAttr, "class='pli-arrow-right'"/>
</#macro>

<#--
 * Outputs the previous page link
-->
<#macro previous>
    <#if (data.number < 1)>
        <#local pageNumber = data.number />
        <#local classAttr = "class=\"disabled\"" />
    <#else>
        <#local pageNumber = data.number - 1 />
        <#local classAttr = "" />
    </#if>
<#--    <#local text>-->
<#--        <@spring.messageText "pagination.previous", "« Previous" />-->
<#--    </#local>-->
    <@page pageNumber, "", classAttr, "class='pli-arrow-left'"/>
</#macro>

<#--
 * Outputs the page numbers and links
 *
 * @param maxPages (Optional) The maximum number of page links to show
 * @param separator (Optional) The separator between page links
-->
<#macro numbers maxPages = 9 separator = " | ">
    <#local pagesBefore = (maxPages / 2)?floor />
    <#local pagesAfter = (maxPages / 2)?floor />
    <#if maxPages % 2 == 0>
        <#local pagesBefore = pagesBefore - 1 />
    </#if>
    <#local pageNumMin = data.number - pagesBefore />
    <#local pageNumMax = data.number + pagesBefore />
    <#if (pageNumMin < 0)>
        <#local pageNumMax = pageNumMax + (0 - pageNumMin) />
        <#local pageNumMin = 0 />
    </#if>
    <#if (pageNumMax >= data.totalPages)>
        <#local pageNumMin = pageNumMin - (pageNumMax - data.totalPages) />
        <#local pageNumMax = data.totalPages - 1 />
        <#if (pageNumMin < 0)>
            <#local pageNumMin = 0 />
        </#if>
        <#if (pageNumMax < 0)>
            <#local pageNumMax = 0 />
        </#if>
    </#if>
    <#list pageNumMin..pageNumMax as pageNumber>
        <#if pageNumber == data.number>
            <#local classAttr = "class=\"active\"" />
        <#else>
            <#local classAttr = "" />
        </#if>
        <@page pageNumber, "", classAttr/>
<#--        <#if pageNumber_has_next>${separator}</#if>-->
    </#list>
</#macro>

<#macro getQueryString viewSize=0>?page=${data.number}&amp;size=${(viewSize == 0)?then(data.size, viewSize)}<#list data.sort.iterator() as sort>&sort=${sort.property},${sort.direction}</#list></#macro>
<#--
 * Outputs a link to a specific page.
 *
 * @param pageNumber To page number ot link to
 * @param text (Optional) The link text (Defaults to page number if not set)
 * @param attributes (Optional) Any HTML attributes to add to the element
-->
<#macro page pageNumber text = "" attributes = "" className = "">
    <#if text == "">
        <#local text = (pageNumber + 1)?string />
    </#if>
    <#if (attributes != "" && attributes?starts_with(" ") == false)>
        <#local attributes = " " + attributes />
    </#if>
<#--<a href="?field=${data.sortField?url}&amp;page=${pageNumber}&amp;size=${data.pageSize}&amp;direction=${data.sortDirection?url}"${attributes}>${text?html}</a>-->
    <li${attributes}><a href="?page=${pageNumber}&amp;size=${data.size}<#list data.sort.iterator() as sort>&sort=${sort.property},${sort.direction}</#list>"${className}><#if className?has_content == false>${text?html}</#if></a></li>
<#--    <li class="disabled"><a href="#" class="demo-pli-arrow-left"></a></li>-->
</#macro>

<#--
 * Outputs the current page number and the total pages
-->
<#macro counter>
    <#if data.totalPages == 0>
        <#local pagesAvailable = 1 />
    <#else>
        <#local pagesAvailable = data.totalPages />
    </#if>
    <#assign messageArgs = ["${data.number + 1}", "${pagesAvailable}"]/>
<#--${data.number + 1} of ${pagesAvailable}-->
    <@spring.messageArgsText "pagination.counter", messageArgs, "{0} of {1}" />
<#--<#assign args = ["${data.number?string}"]/>-->
<#--${messageArgs?is_indexable?string} ${messageArgs?size}-->
</#macro>

<#macro sortField label fieldName>
    <#assign direction = "asc"/>
    <#assign matched = false/>
    <#assign s = ""/>
    <#list data.sort.iterator() as sort>
    <#if sort?is_first>
    <#assign s = "?"/>
    <#else>
    <#assign s += "&"/>
    </#if>
    <#if sort.property == fieldName>
        <#assign matched = true/>
        <#assign direction=(sort.direction?upper_case == 'DESC')?then('asc', 'desc')/>
        <#assign s += "sort=${fieldName},${direction}"/>
    <#else>
        <#assign s += "sort=${sort.property},asc"/>
    </#if>
    </#list>

    <#if matched == false>
        <#if s?has_content>
            <#assign s = "?sort=${fieldName},desc"/>
        <#else>
            <#assign s = "?sort=${fieldName},desc"/>
        </#if>
    </#if>

    <a href="${s}">${label} <i class="fa fa-sort<#if matched == true>-${(direction == 'asc')?then('desc', 'asc')}<#else> text-muted</#if>"></i></a>


</#macro>
<#--
 * Outputs a link to sort by a field.
 * @param field The field to sort by. If field is different to the current sort field, the link will change the sort
 * field but not the sort direction. If the field is the same as the current sort field, the link will change the sort
 * direction.
 * @param text (Optional) The link text. If no text is specified the field name is used with a upper case first letter.
 * @param attributes (Optional) Any HTML attributes to add to the element
 * @param directions (Optional) An array of two items. The words being used in data.sortDirection to describe
 * the sorting direction of ascending or descending. Default: ["Asc", "Desc"]. So we can compare the current sorting
 * direction and switch to the converse.
-->


<#macro sort field text = "" attributes = "" directions = ["Asc", "Desc"]>
    <#if field == data.sortField>
    <#-- Change sort direction -->
        <#if data.sortDirection?lower_case == directions[0]?lower_case>
            <#local direction = directions[1] />
        <#else>
            <#local direction = directions[0] />
        </#if>
    <#else>
    <#-- Change sort field (leave sort direction) -->
        <#local direction = data.sortDirection />
    </#if>
    <#if text == "">
        <#local text = field?cap_first />
    </#if>
    <#if (attributes != "" && attributes?starts_with(" ") == false)>
        <#local attributes = " " + attributes />
    </#if>
    <a href="?field=${field?url}&amp;page=${data.number}&amp;size=${data.pageSize}&amp;direction=${direction?url}"${attributes}>${text?html}</a>
</#macro>