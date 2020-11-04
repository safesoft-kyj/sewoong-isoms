<%--
  Created by IntelliJ IDEA.
  User: JHSEO
  Date: 2019-02-21
  Time: 오전 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<ul id="mainnav-menu" class="list-group" style="margin-top:10px !important;">
    <li>
        <a href="/sop" aria-expanded="false">
            <i class="pli-folder-binder"></i>
            <span class="menu-title">SOP</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li><a href="/external/sop/effective">Effective SOP</a></li>
            <li><a href="/external/sop/superseded">Superseded SOP</a></li>
        </ul>
    </li>
    <li>
        <a href="/external/digital-binder">
            <i class="pli-box-with-folders"></i>
            <span class="menu-title">Digital Binder</span>
<%--            <i class="arrow"></i>--%>
        </a>
    </li>
</ul>