<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
function onClickVentes() {
	$.get('services/dataservice/machines/ventes', '', function(data) {
		console.log(data);
		let txt = "";
		let table = $('#table');
		table.empty();
		table.append("<th><td>ID</td><td>Numéro de série</td><td>Type</td><td>Adresse</td><td>Ventes</td></th>")
		$.each(data.results, function(index, result) {
			txt = "<tr><td></td><td>"+result.machine.id+"</td>";
			txt += "<td>"+result.machine.serialNb+"</td>";
			txt += "<td>"+result.machine.type+"</td>";
			txt += "<td>"+result.machine.installationAddress.streetNumber+" "+
				result.machine.installationAddress.street+"<br>"+
				result.machine.installationAddress.postalCode+" "+
				result.machine.installationAddress.city+", "+
				result.machine.installationAddress.country;
			txt += "<td>"+result.report.sumOfSales+"</td>";
			txt += "</tr>"
			table.append(txt);
		});
	});
}
function onClickHorsService() {
	$.get('services/dataservice/machines/hors-service', '', function(data) {
		console.log(data);
		let txt = "";
		let table = $('#table');
		table.empty();
		table.append("<th><td>ID</td><td>Numéro de série</td><td>Type d'erreur</td><td>Erreur</td><td>Date et heure</td></th>")
		let prec = null;
		$.each(data.results, function(index, result) {
			if (prec === result.machine.id) {
				txt = "<tr><td></td><td></td><td></td>"
			} else {
				prec = result.machine.id;
				txt = "<tr><td></td><td>"+result.machine.id+"</td>";
				txt += "<td>"+result.machine.serialNb+"</td>";
			}
			txt += "<td>"+result.error.type+"</td>";
			txt += "<td>"+result.error.description+"</td>";
			txt += "<td>"+(new Date(result.error.time))+"</td>";
			txt += "</tr>"
			table.append(txt);
		});
	});
}
function onClickReapprovisionner() {
	$.get('services/dataservice/machines/reapprovisionner', '', function(data) {
		console.log(data);
		let txt = "";
		let table = $('#table');
		table.empty();
		table.append("<th><td>ID</td><td>Numéro de série</td><td>Température</td><td>Type du produit</td>")
		table.append("<td>Produit</td><td>Quantité restante</th>")
		let prec = null;
		$.each(data.results, function(index, result) {
			if (prec === result.machine.id) {
				txt = "<tr><td></td><td></td><td></td><td></td>"
			} else {
				prec = result.machine.id;
				txt = "<tr><td></td><td>"+result.machine.id+"</td>";
				txt += "<td>"+result.machine.serialNb+"</td>";
				txt += "<td>"+result.report.temperature+"</td>";
			}
			txt += "<td>"+result.product.type+"</td>";
			txt += "<td>"+result.product.name+"</td>";
			txt += "<td>"+result.productReport.quantity+"</td>";
			txt += "</tr>"
			table.append(txt);
		});
	});
}
function onClickSurveiller() {
	$.get('services/dataservice/machines/surveiller', '', function(data) {
		console.log(data);
		let txt = "";
		let table = $('#table');
		table.empty();
		table.append("<th><td>ID</td><td>Numéro de série</td><td>État actuel</td><td>État du monnayeur</td>");
		table.append("<td>État terminal de payement</td><td>État payement sans contact</td></th>")
		let prec = null;
		$.each(data.results, function(index, result) {
			if (prec === result.machine.id) {
				txt = "<tr><td></td><td></td><td></td>"
			} else {
				prec = result.machine.id;
				txt = "<tr><td></td><td>"+result.machine.id+"</td>";
				txt += "<td>"+result.machine.serialNb+"</td>";
			}
			txt += "<td>"+result.report.currState+"</td>";
			txt += "<td>"+result.report.changeState+"</td>";
			if (result.report.chipCardError) {
				txt += "<td>Erreur</td>";
			} else {
				txt += "<td>Normal</td>"
			}
			if (result.report.contactlessCardError) {
				txt += "<td>Erreur</td>";
			} else {
				txt += "<td>Normal</td>"
			}
			txt += "</tr>"
			table.append(txt);
		});
	});
}
</script>
</head>
<body>
<div id="buttons">
	<button type="button" onclick="onClickVentes()">Ventes</button>
	<button type="button" onclick="onClickReapprovisionner()">À réapprovisionner</button>
	<button type="button" onclick="onClickSurveiller()">À surveiller</button>
	<button type="button" onclick="onClickHorsService()">Hors-service</button>
</div>
<table id="table">
</table>
</body>
</body>
</html>