<%@ tag body-content="empty" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<%@ attribute name="restaurante" rtexprvalue="true" required="true" type="br.com.mogav.bluesoft.model.Restaurante" description="CSS com a imagem de fundo do restaurante" %> 

<c:choose>
	<c:when test="${restaurante.name() eq 'MCDONALDS'}">images/mcdonalds_rest.png</c:when>
	<c:when test="${restaurante.name() eq 'SUBWAY'}">images/subway_rest.png</c:when>
	<c:when test="${restaurante.name() eq 'WENDYS'}">images/wendys_rest.png</c:when>
	<c:when test="${restaurante.name() eq 'OUTBACK'}">images/outback_rest.png</c:when>
	<c:when test="${restaurante.name() eq 'GIRAFFAS'}">images/giraffas_rest.png</c:when>
	<c:otherwise>undefined</c:otherwise>
</c:choose>