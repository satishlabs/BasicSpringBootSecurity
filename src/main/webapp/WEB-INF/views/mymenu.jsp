<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

 <div class="container" align="right">
<h2>
| <a href="<c:url value='/'/>"> Home</a>
| Welcome : ${pageContext.request.userPrincipal.name}|${MyRoles}
 
<sec:authorize access="! isAuthenticated()"> 
 | <a href='<c:url value="login"/>'>Login</a> 
 | <a href='<c:url value="showRegister"/>'>Register</a> 
</sec:authorize> 

<sec:authorize access="isAuthenticated()"> 
|<a href="<c:url value='logout'/>"> Logout</a> 
</sec:authorize>
 </h2>
</div> 
