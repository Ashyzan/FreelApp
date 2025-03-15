
// recupero elementi dal DOM per modalTimerRapidButton
const modalTimerRapidButton =document.getElementById('modalTimerRapidButton');
const timerRapidButton = document.getElementById('timerRapidButton');
const modalTimerRapidCloseButton = document.getElementById('modalTimerRapidCloseButton');
const taskResumeTable = document.getElementById('taskResumeTable');		
const formTask = document.getElementById('form-task-select');
const timerResumeTask = document.getElementById('timerResumeTask');

//id del task in uso
let taskAttualmenteInUso = 0;

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
	if(id != 0){
		const response = await fetch(api_url+id);
		const data = await response.json();
			
		document.getElementById('taskNome').textContent = data.nome;	
		document.getElementById('taskProgetto').textContent = data.progetto;	
		document.getElementById('taskCliente').textContent = data.cliente;	
		document.getElementById('taskLogoPath').src = data.logoCliente;	
		document.getElementById('taskChiusuraStimata').textContent = data.chiusuraStimata;
		//taskAttualmenteInUso = data.taskAttualmenteInUso;	
		
			stampaContatore(data.finalTime, data.taskAttualmenteInUso)
		
	}
			
	//document.getElementById('timerResumeTask').textContent = data.finalTime;
}

function recapTask(event){
	event.preventDefault()
	
	const valueInput = document.getElementById('form-select-input').value;
	
	if(valueInput != 0){
		taskResumeTable.classList.remove('hidden')
		getJsonTask(valueInput);		
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

function stampaContatore(finalTime,taskAttualmenteInUso){
	const valueInput = document.getElementById('form-select-input').value;
	if(taskAttualmenteInUso == valueInput){
		document.getElementById('timerResumeTask').textContent = "Task in corso"
	} else{
		let hours = finalTime/3600;
		let minutes = (finalTime % 3600) / 60;
		let seconds = (minutes - Math.floor(minutes)) * 60;
		document.getElementById('timerResumeTask').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);				
	}
}