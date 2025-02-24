
// recupero elementi dal DOM per modalTimerRapidButton
const modaleOreLavorate =document.getElementById('modaleOreLavorate');
const oreLavorateButton = document.getElementById('oreLavorateButton');
const modaleOreLavorateCloseButton = document.getElementById('modaleOreLavorateCloseButton');
const taskResumeTableOreLavorate = document.getElementById('taskResumeTableOreLavorate');		
const formTaskOrelavorate = document.getElementById('form-task-select-orelavorate');

console.log("modaleOreLavorate " + modaleOreLavorate+ " " + "oreLavorateButton " + oreLavorateButton + " " + "modaleOreLavorateCloseButton " + modaleOreLavorateCloseButton + " " + "taskResumeTableOreLavorate " + taskResumeTableOreLavorate);

//da cambiare con url definitivo
const api_url_1= 'http://localhost:8080/api/task/'

// apertura modale oreLavorateButton/timerRapidButton
oreLavorateButton.addEventListener('click', function(){
	modaleOreLavorate.classList.remove('scale-0');
})

//chiusure modale timerRapidButton
modaleOreLavorateCloseButton.addEventListener('click', function(){
	modaleOreLavorate.classList.add('scale-0');
})

// funzione che fa l'autosubmit sul form di selezione
document.getElementById('form-select-input-ore').addEventListener("change", (event) => {
			//document.getElementById('form-task-select-orelavorate').submit()
			recapTaskOreLavorate(event);
		})

//function openModalSelectedTask(){
//	if (taskSelectedId != null){
//		
//		modalTimerRapidButton.classList.remove('scale-0');
//		formTaskOrelavorate.classList.add('hidden');
//									
//	}
//}

async function getJsonTaskOre(id){
	const response = await fetch(api_url_1+id);
	const data = await response.json();
		console.log("nome " + data.nome);
	document.getElementById('taskNomeOre').textContent = data.nome;
		console.log("progetto " + data.progetto);
	document.getElementById('taskProgettoOre').textContent = data.progetto;
		console.log("cliente " + data.cliente);
	document.getElementById('taskClienteOre').textContent = data.cliente;
		console.log("logoCliente " + data.logoCliente);
	document.getElementById('taskLogoPathOre').src = data.logoCliente;
}

function recapTaskOreLavorate(event){
	event.preventDefault()
	
	const valueInput = document.getElementById('form-select-input-ore').value;
	getJsonTask(valueInput);
	
	if(valueInput != 0){
		taskResumeTableOreLavorate.classList.remove('hidden')
	} else if(valueInput == 0){
		taskResumeTableOreLavorate.classList.add('hidden')
	}
	
	// assegna endpoint dettaglio task in base al task scelto
	const taskDetailHrefOre = document.getElementById('taskDetailHrefOre')
	taskDetailHrefOre.href = `/Task/${valueInput}`;


}