<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<ul id="mainnav-menu" class="list-group" style="margin-top:10px !important;">

    <li>
        <a href="/admin/dashboard" aria-expanded="false">
            <i class="pli-dashboard"></i>
            <span class="menu-title">Dashboard</span>
        </a>
    </li>
    <li>
        <a href="/admin/authority" aria-expanded="false">
            <i class="pli-checked-user"></i>
            <span class="menu-title">User Management</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li><a href="/admin/authority/users">내부사용자</a></li>
            <li><a href="/admin/authority/accounts">외부사용자</a></li>
            <li><a href="/admin/authority/agreement-to-collect-and-use">개인정보 활용동의</a></li>
            <li><a href="/admin/authority/non-disclosure-agreement-for-sop">SOP 비공개 동의</a></li>
        </ul>
    </li>
    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-folder-with-document"></i>
            <span class="menu-title">SOP Management</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li><a href="/admin/SOP/management/development">Development</a></li>
            <li><a href="/admin/SOP/management/revision">Revision</a></li>
            <li><a href="/admin/SOP/management/approved">Approved</a></li>
            <li><a href="/admin/SOP/management/effective">Effective</a></li>
            <li><a href="/admin/SOP/management/superseded">Superseded</a></li>
            <li><a href="/admin/SOP/management/retirement">Retirement</a></li>
            <li><a href="/admin/sop/category">SOP Category</a></li>
        </ul>

    </li>
    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-file"></i>
            <span class="menu-title">RD Management</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li><a href="/admin/RD/management/development">Development</a></li>
            <li><a href="/admin/RD/management/revision">Revision</a></li>
            <li><a href="/admin/RD/management/approved">Approved</a></li>
            <li><a href="/admin/RD/management/effective">Effective</a></li>
            <li><a href="/admin/RD/management/superseded">Superseded</a></li>
            <li><a href="/admin/RD/management/retirement">Retirement</a></li>
        </ul>

    </li>

    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-university"></i>
            <span class="menu-title">Training Management</span>
            <i class="arrow"></i>
        </a>
        <ul class="collapse" aria-expanded="false">
                <li><a href="/admin/training/trainingLog">Employee Training Log</a></li>
                <li><a href="/admin/training/refresh-training">Refresh Training</a></li>
                <li><a href="/admin/training/offline-training">Off-line Training</a></li>
                <li><a href="/admin/training/matrix">SOP Training Matrix(Upload)</a></li>
        </ul>
    </li>
    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-check"></i>
            <span class="menu-title">전자결재</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li><a href="/admin/approval">전체문서함</a></li>
            <li><a href="/admin/approval/request">요청함</a></li>
            <li><a href="/admin/approval/progress">진행함</a></li>
            <li><a href="/admin/approval/approved">완료함</a></li>
            <li><a href="/admin/approval/rejected">반려함</a></li>
        </ul>
    </li>
    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-security-camera"></i>
            <span class="menu-title">Security</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li><a href="/admin/document/accessLog">SOP/RD Access Log</a></li>
        </ul>

    </li>
</ul>
