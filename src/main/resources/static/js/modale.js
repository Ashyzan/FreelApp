const modale = document.getElementById('modale');

function apriModale() {
	modale.classList.remove('scale-0');
console.log("ho aperto la modale");
	
}

function chiudiModale() {
	modale.classList.add('scale-0');	
}

// per attivare la modale è necessario inserire nell'html le seguenti funzioni al click:
//onclick="apriModale()" per aprire la modale
//onclick="chiudiModale()" per chiudere la modale
//dare alla modale id "modale" e classe "scale-0"