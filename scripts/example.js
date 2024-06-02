function test(param) {
	return `${param} - javaskrypt`;
}

function parse(param) {
	var x = JSON.parse(param);
	return x.abc;
}

function mapka() {
	return [1,2,3]
	.map((i) => i * 2)
	.reduce((acc, i) => acc + i, 0);
}

function data() {
	var date = new java.util.Date();
	return date;
}

function dataGraal() {
	var Date = Java.type('java.util.Date');
	var date = new Date();
	return date;
}

function callJava(param) {
	var random = Math.floor(Math.random(10) * 100);
	var przyklad = new PrzykladHosted(random);
	
	przyklad.test(przyklad.value);
	return 'called!';
}


function callJavaGraal(param) {
	var random = Math.floor(Math.random(10) * 100);
	var PrzykladHosted = Java.type('pl.prezentacja.demko.core.scripts.mozilla.PrzykladHosted');
	var przyklad = new PrzykladHosted(random);
	
	przyklad.funkcja();
	return 'called!';
}

function callJavaGraal2(param) {
	var PrzykladKlasa = Java.type('pl.prezentacja.demko.core.scripts.oracle.PrzykladKlasa');
	var przyklad = new PrzykladKlasa();
	
	var x = przyklad.getValue();
	var d = przyklad.processDane(x);
	var e = przyklad.processDane(123);
	var f = przyklad.processDane({a: 123});
	
	return `${x} - ${d} - ${e} - ${f}`;
}