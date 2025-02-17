
// recupero elementi dal DOM per modalTimerRapidButton
const modalTimerRapidButton =document.getElementById('modalTimerRapidButton');
const timerRapidButton = document.getElementById('timerRapidButton');
const modalTimerRapidCloseButton = document.getElementById('modalTimerRapidCloseButton');
const taskResumeTable = document.getElementById('taskResumeTable');		
const formTask = document.getElementById('form-task-select');

//da cambiare con url definitivo
const api_url= 'http://localhost:8080/api/task/'

// apertura modale timerRapidButton
timerRapidButton.addEventListener('click', function(){
	modalTimerRapidButton.classList.remove('scale-0');
})

//chiusure modale timerRapidButton
modalTimerRapidCloseButton.addEventListener('click', function(){
	modalTimerRapidButton.classList.add('scale-0');
})

// funzione che fa l'autosubmit sul form di selezione
document.getElementById('form-select-input').addEventListener("change", (event) => {
			//document.getElementById('form-task-select').submit()
			recapTask(event);
		})

//function openModalSelectedTask(){
//	if (taskSelectedId != null){
//		
//		modalTimerRapidButton.classList.remove('scale-0');
//		formTask.classList.add('hidden');
//									
//	}
//}

async function getJsonTask(id){
	const response = await fetch(api_url+id);
	const data = await response.json();
		console.log("nome: " + data.nome);
	document.getElementById('taskNome').textContent = data.nome;
		console.log("progetto: " + data.progetto);
	document.getElementById('taskProgetto').textContent = data.progetto;
		console.log("cliente: " + data.cliente);
	document.getElementById('taskCliente').textContent = data.cliente;
		console.log("logoCliente: " + data.logoCliente);
	document.getElementById('taskLogoPath').src = data.logoCliente;
		console.log("chiusuraStimata: " + data.chiusuraStimata);
	document.getElementById('taskChiusuraStimata').textContent = data.chiusuraStimata;
}

function recapTask(event){
	event.preventDefault()
	
	const valueInput = document.getElementById('form-select-input').value;
	getJsonTask(valueInput);
	
	if(valueInput != 0){
		taskResumeTable.classList.remove('hidden')
	} else if(valueInput == 0){
		taskResumeTable.classList.add('hidden')
	}
	
	// assegna endpoint dettaglio task in base al task scelto
	const taskDetailHref = document.getElementById('taskDetailHref')
	taskDetailHref.href = `/Task/${valueInput}`;
	
	// assegna action avvio contatore in base al task scelto
	const formStartContatore = document.getElementById('form-start-contatore');
	formStartContatore.action = `/start/${valueInput}`;

}