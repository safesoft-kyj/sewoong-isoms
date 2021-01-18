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
        <a href="#" aria-expanded="false">
            <i class="pli-folder-with-document"></i>
            <span class="menu-title">ISO Management</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <%--            <li><a href="/admin/SOP/management/development">Development</a></li>--%>
            <%--            <li><a href="/admin/SOP/management/revision">Revision</a></li>--%>
            <li><a href="/admin/ISO/management/approved">Approved ISO</a></li>
            <li><a href="/admin/ISO/management/effective">Effective ISO</a></li>
            <li><a href="/admin/ISO/management/superseded">Superseded ISO</a></li>
            <li><a href="/admin/ISO/management/retirement">Retirement ISO</a></li>
            <li><a href="/admin/ISO/category">ISO Category</a></li>
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
            <%--            <li><a href="/admin/SOP/management/development">Development</a></li>--%>
            <%--            <li><a href="/admin/SOP/management/revision">Revision</a></li>--%>
            <li><a href="/admin/SOP/management/approved">Approved SOP</a></li>
            <li><a href="/admin/SOP/management/effective">Effective SOP</a></li>
            <li><a href="/admin/SOP/management/superseded">Superseded SOP</a></li>
            <li><a href="/admin/SOP/management/retirement">Retirement SOP</a></li>
            <li><a href="/admin/SOP/category">SOP Category</a></li>
        </ul>
    </li>
    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-file"></i>
            <span class="menu-title">RF Management</span>
            <i class="arrow"></i>
        </a>
        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <%--            <li><a href="/admin/RD/management/development">Development</a></li>--%>
            <%--            <li><a href="/admin/RD/management/revision">Revision</a></li>--%>
            <li><a href="/admin/RF/management/approved">Approved RF</a></li>
            <li><a href="/admin/RF/management/effective">Effective RF</a></li>
            <li><a href="/admin/RF/management/superseded">Superseded RF</a></li>
            <li><a href="/admin/RF/management/retirement">Retirement RF</a></li>
        </ul>

    </li>
    <li>
        <a href="#" aria-expanded="false">
            <i class="pli-monitor-3"></i>
            <span class="menu-title">Training Management</span>
            <i class="arrow"></i>
        </a>

        <!--Submenu-->
        <ul class="collapse" aria-expanded="false">
            <li>
                <a href="#" aria-expanded="false">
                    <span class="menu-title">SOP Training Management</span>
                    <i class="arrow"></i>
                </a>
                <!--Submenu-->
                <ul class="collapse" aria-expanded="false">
                    <li><a href="/admin/training/SOP/trainingLog">SOP Training Log</a></li>
                    <li><a href="/admin/training/SOP/offline-training">Off-line Training</a></li>
                    <li><a href="/admin/training/SOP/refresh-training">Refresh Training</a></li>
                    <li><a href="/admin/training/SOP/matrix">SOP Training Matrix(Upload)</a></li>
                </ul>
            </li>
            <li>
                <a href="#" aria-expanded="false">
                    <span class="menu-title">ISO Training Management</span>
                    <i class="arrow"></i>
                </a>
                <!--Submenu-->
                <ul class="collapse" aria-expanded="false">
                    <li><a href="/admin/training/ISO/trainingLog">ISO Training Log</a></li>
                    <li><a href="/admin/training/ISO/offline-training">Off-line Training</a></li>
                    <li><a href="/admin/training/ISO/refresh-training">Refresh Training</a></li>
                    <li><a href="/admin/training/ISO/matrix">ISO Training Matrix(Upload)</a></li>
                </ul>
            </li>
        </ul>
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
            <li><a href="/admin/department">부서 관리</a></li>
            <li><a href="/admin/role">ROLE 관리</a></li>
            <li><a href="/admin/authority/agreement-to-collect-and-use">개인정보 활용동의</a></li>
            <li><a href="/admin/authority/confidentiality-pledge">기밀 유지 서약동의</a></li>
            <li><a href="/admin/authority/non-disclosure-agreement-for-sop">SOP 비공개 동의</a></li>
            <li><a href="/admin/authority/agreements-withdrawal">철회 신청 내역</a></li>
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
            <li><a href="/admin/document/accessLog">System Access Log</a></li>
            <li><a href="/admin/change-control">Change Control</a></li>
        </ul>

    </li>
</ul>
