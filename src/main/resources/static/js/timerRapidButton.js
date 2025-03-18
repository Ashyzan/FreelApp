
// recupero elementi dal DOM per modalTimerRapidButton
const modalTimerRapidButton =document.getElementById('modalTimerRapidButton');
const timerRapidButton = document.getElementById('timerRapidButton');
const modalTimerRapidCloseButton = document.getElementById('modalTimerRapidCloseButton');
const taskResumeTable = document.getElementById('taskResumeTable');		
const formTask = document.getElementById('rapid-task');
const timerResumeTask = document.getElementById('timerResumeTask');
const searchTaskButton = document.getElementById('search-task-button');
const selectTaskButton = document.getElementById('select-task-button')
const selectMode = document.getElementById('select-mode');
const searchMode = document.getElementById('search-mode');
let selectTaskList = document.querySelectorAll('form-select-input').value, selectTaskListValues = [];

//id del task in uso
let taskAttualmenteInUso = 0;

//da cambiare con url definitivi
const api_urlTaskSelected = 'http://localhost:8080/api/task/'
const api_urlTaskListOreLavorate = 'http://localhost:8080/api/oreLavorate/taskList?input='

// apertura modale timerRapidButton
timerRapidButton.addEventListener('click', function(){
	modalTimerRapidButton.classList.remove('scale-0');
})

//chiusure modale timerRapidButton
modalTimerRapidCloseButton.addEventListener('click', function(){
	modalTimerRapidButton.classList.add('scale-0');
})

//funzione che dalla modalità select passa a quella di ricerca
searchTaskButton.addEventListener('click', function(){
	selectMode.classList.add('hidden');
	searchMode.classList.remove('hidden');
	searchTaskButton.classList.add('opacity-50', 'pointer-events-none')
	selectTaskButton.classList.remove('opacity-50', 'pointer-events-none')
	
})

//funzione che dalla modalità di ricerca passa a quella select
selectTaskButton.addEventListener('click', function(){
	selectMode.classList.remove('hidden');
	selectTaskButton.classList.add('opacity-50', 'pointer-events-none')
	searchMode.classList.add('hidden');
	searchTaskButton.classList.remove('opacity-50', 'pointer-events-none')
	
})


// funzione che fa l'autosubmit sul form di ricerca

document.getElementById('form-search-input').addEventListener("change", (event) =>{
			//document.getElementById('form-task-select').submit()
			
			recapSearchedTaskList(event)
		});

async function getJsonTask(id){
	if(id != 0){
		const response = await fetch(api_urlTaskSelected+id);
		const data = await response.json();
			
		document.getElementById('taskNome').textContent = data.nome;	
		document.getElementById('taskProgetto').textContent = data.progetto;
		document.getElementById('taskProgetto').href = `/Progetti/${data.progettoId}` ;	
		document.getElementById('taskCliente').textContent = data.cliente;	
		document.getElementById('taskCliente').href = `/Clienti/${data.clienteId}` ;	
		document.getElementById('taskLogoPath').src = data.logoCliente;	
		document.getElementById('task-stato').src = assegnaSvgStatoItem(data.stato);	
		
		
		stampaContatore(data.finalTime, data.taskAttualmenteInUso, id)
		
	}
}

async function getJsonTaskListOreLavorate(input){
	console.log("input: " + input)
	
	document.getElementById('items-ore-lavorate').innerHTML =` `;
	if(input != "" || input != " "){
		const response = await fetch(api_urlTaskListOreLavorate+input);
		const list = await response.json();
		
		list.forEach(item =>{
			const statoItem = assegnaSvgStatoItem(item.stato);
			document.getElementById('items-ore-lavorate').innerHTML += 
			`<button type="button" value="${item.id}" onclick="recapSelectedTask(${item.id})"
					class="border-b-1 shadow-md grid grid-cols-8 p-1 w-full hover:bg-[#FFE541]/50">
				<span class="col flex items-center justify-center">
					<img src="${item.logoCliente}" class="h-5 w-auto">
				</span>
				<span class="border-r col-span-2 text-center text-[#0057A5] px-1 truncate">${item.cliente}</span>
				<span class="border-r col-span-2 text-center text-[#0057A5] px-1 truncate">${item.progetto}</span>
				<span class="border-r col-span-2 text-center text-[#0057A5] px-1 truncate">${item.nome}</span>
				<span class="col text-center text-[#0057A5] flex items-center justify-center">
				  <img class="h-5" src=${statoItem}>
				</span>
			</button>`		
		});
		
	} 
		
		
	
}

function recapSelectedTask(id){
	//event.preventDefault()
	getJsonTask(id);
	valueInput = id;
	
	if(valueInput != null){
		taskResumeTable.classList.remove('hidden')
		getJsonTask(valueInput);		
	} else if(valueInput == null){
		taskResumeTable.classList.add('hidden')
	}
	
	
	
	// assegna endpoint dettaglio task in base al task scelto
	const taskDetailHref = document.getElementById('taskDetailHref')
	taskDetailHref.href = `/Task/${valueInput}`;
	
	// assegna action avvio contatore in base al task scelto
	const formStartContatore = document.getElementById('form-start-contatore');
	formStartContatore.action = `/start/${valueInput}`;
		

}

function stampaContatore(finalTime,taskAttualmenteInUso, id){
	//const valueInput = document.getElementById('form-select-input').value;
	const valueInput = id;
	if(taskAttualmenteInUso == valueInput){
		document.getElementById('timerResumeTask').textContent = "Task in corso"
	} else{
		let hours = finalTime/3600;
		let minutes = (finalTime % 3600) / 60;
		let seconds = (minutes - Math.floor(minutes)) * 60;
		document.getElementById('timerResumeTask').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);				
	}
}

//funzione che visualizza il dettaglio task selezionato e ne 
//assegna gli endpoint al dettaglio, contatore,ore lavoreate e archivio 
function recapSearchedTaskList(id){
	
	const valueInput = id;
	
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

//funzione che assegna l'isv dello stato all'item
function assegnaSvgStatoItem(stato){
	let svg = "";
	if(stato == "in corso"){
		svg = "/img/sources/icons/contatore-on-start.svg";
	} else if(stato == "in pausa"){
		svg = "/img/sources/icons/contatore-on-pause.svg";
	}else if(stato == "chiuso"){
		svg = "/img/sources/icons/contatore-on-stop.svg";
	} else {
		svg = "/img/sources/icons/contatore-inattivo.svg";
	}
	
	return svg
}
