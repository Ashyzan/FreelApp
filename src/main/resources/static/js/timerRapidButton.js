
// recupero elementi dal DOM per modalTimerRapidButton
const modalRapidButton =document.getElementById('modalRapidButton');
const rapidButton = document.getElementById('rapidButton');
const modalRapidCloseButton = document.getElementById('modalRapidCloseButton');
const taskResumeTable = document.getElementById('taskResumeTable');		
const formTask = document.getElementById('rapid-task');
const timerResumeTask = document.getElementById('timerResumeTask');
const searchTaskButton = document.getElementById('search-task-button');
const selectTaskButton = document.getElementById('select-task-button')
const selectMode = document.getElementById('select-mode');
const searchMode = document.getElementById('search-mode');
let selectTaskList = document.querySelectorAll('form-select-input').value, selectTaskListValues = [];
const backTaskRapidButton = document.getElementById('back-task-rapid-button')
const contatoreTaskRapidButton = document.getElementById('contatore-task-rapid-button');
const backTaskRapidButtonContainer = document.getElementById('back-task-rapid-button-container')
const searchAndSelectButtonsContainer = document.getElementById('search-and-select-buttons-container');
const contatoreTaskRapid = document.getElementById('contatore-task-rapid');
const searchAndSelectTitle = document.getElementById('search-and-select-title');

const oreLavorateTaskRapidContainer = document.getElementById('ore-lavorate-task-rapid-container');
const oreLavorateTaskRapidButton = document.getElementById('ore-lavorate-task-rapid-button');

//id del task in uso
let taskAttualmenteInUso = 0;

//da cambiare con url definitivi
const api_urlTaskSelected = 'http://localhost:8080/api/task/'
const api_urlTaskListSearch = 'http://localhost:8080/api/searchMode/taskList?input='

// apertura modale timerRapidButton
rapidButton.addEventListener('click', function(){
	modalRapidButton.classList.remove('scale-0');
})

//chiusure modale timerRapidButton
modalRapidCloseButton.addEventListener('click', function(){
	modalRapidButton.classList.add('scale-0');
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
			
		document.getElementById('taskNome').textContent = data.nome.toUpperCase();	
		document.getElementById('taskProgetto').textContent = data.progetto;
		document.getElementById('taskProgetto').href = `/Progetti/${data.progettoId}` ;	
		document.getElementById('taskCliente').textContent = data.cliente;	
		document.getElementById('taskCliente').href = `/Clienti/${data.clienteId}` ;	
		document.getElementById('taskLogoPath').src = data.logoCliente;	
		document.getElementById('task-stato').src = assegnaSvgStatoItem(data.stato);	
		
		
		stampaContatore(data.finalTime, data.taskAttualmenteInUso, id)
		
	}
}

async function getJsonTaskListSearch(input){
	console.log("input: " + input)
	
	document.getElementById('items-ore-lavorate').innerHTML =` `;
	if(input != "" || input != " "){
		const response = await fetch(api_urlTaskListSearch+input);
		const list = await response.json();
		
		list.forEach(item =>{
			const statoItem = assegnaSvgStatoItem(item.stato);
			document.getElementById('items-from-search').innerHTML += 
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
	
	//assegna action pausa contatore in baseal task scelto
	const formPauseContatore = document.getElementById('form-pause-contatore');
	formPauseContatore.action = `/Contatore/pause/${valueInput}`;
	
	// assegna action avvio contatore in base al task scelto
	const taskDetailHrefOre = document.getElementById('taskDetailHrefOre');
		taskDetailHrefOre.action = `/orelavorate/${valueInput}`;

}

function stampaContatore(finalTime,taskAttualmenteInUso, id){
	//const valueInput = document.getElementById('form-select-input').value;
	const valueInput = id;
	
	if(taskAttualmenteInUso == valueInput && contatoreIsRun == true){
		stampaTaskSelezionatoInRun();
		
	} else if((taskAttualmenteInUso != valueInput && contatoreIsRun != true)||(taskAttualmenteInUso != valueInput && contatoreIsRun == true)) {
		if(document.getElementById('start-button').pointerEvents == "none"){
			document.getElementById('start-button').classList.remove('pointer-events-none');
		}
		if(document.getElementById('stopButtonTaskRapid').pointerEvents != "none"){
			document.getElementById('stopButtonTaskRapid').classList.add('pointer-events-none')
		}
		if(document.getElementById('pause-button').pointerEvents != "none"){
			document.getElementById('pause-button').classList.add('pointer-events-none')
		}			
		let hours = finalTime/3600;
		let minutes = (finalTime % 3600) / 60;
		let seconds = (minutes - Math.floor(minutes)) * 60;
		document.getElementById('timerResumeTask').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);
		document.getElementById('work-time').innerHTML = ('0' + Math.floor(hours)).slice(-4) + ":" + ('0' + Math.floor(minutes)).slice(-2) + ":" + ('0' + Math.floor(seconds)).slice(-2);				
	}
}

//funzione che stampa il contatore e il resoconto del tempo del task selezionato
//se lo stato è in corso
function stampaTaskSelezionatoInRun(){
	document.getElementById('work-time').textContent = "Task in corso";
	document.getElementById('work-time').classList.add('text-[#ffe541]');
	document.getElementById('start-button').classList.add('pointer-events-none');
	document.getElementById('timerResumeTask').textContent = "IN CORSO";
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
	
	//assegna action pausa contatore in baseal task scelto
	const formPauseContatore = document.getElementById('form-pause-contatore');
		formPauseContatore.action = `/Contatore/pause/${valueInput}`;
	
	// assegna action avvio contatore in base al task scelto
	const taskDetailHrefOre = document.getElementById('taskDetailHrefOre');
		taskDetailHrefOre.action = `/orelavorate/${valueInput}`;

}

//funzione che assegna l'svg dello stato all'item
function assegnaSvgStatoItem(stato){
	let svg = "";

		if(stato == "in corso" ){
				svg = "/img/sources/icons/contatore-on-start.svg";
			} else if(stato == "in pausa" ){
				svg = "/img/sources/icons/contatore-on-pause.svg";
			}else if(stato == "chiuso"){
				svg = "/img/sources/icons/contatore-on-stop.svg";
			} else {
				svg = "/img/sources/icons/contatore-inattivo.svg";
			}
		return svg				
}

//attivazione del pulsante back
if(backTaskRapidButton != null){
	backTaskRapidButton.addEventListener('click', function(){
		
		clickOnBackButton()
		
	});
}

//attivazione del pulsante contatore
if(contatoreTaskRapidButton != null){
	contatoreTaskRapidButton.addEventListener('click', function(){
		
		clickOnContatoreOrOreLavorate()
		
		contatoreTaskRapid.classList.remove('hidden');
		
		if(oreLavorateTaskRapidContainer.display != "none"){
			oreLavorateTaskRapidContainer.classList.add('hidden');
		}
		
	});
}

//attivazione del pulsante ore lavorate
if(oreLavorateTaskRapidButton != null){
	oreLavorateTaskRapidButton.addEventListener('click', function(){
		
		clickOnContatoreOrOreLavorate()
		oreLavorateTaskRapidContainer.classList.remove('hidden');
		
		if(contatoreTaskRapid.display != "none"){
			contatoreTaskRapid.classList.add('hidden');
		}
	});
}


//funzione che al click dell'icona contatore o oreLavorate fa sparire
//la sezione di ricerca selezione e apparire il pulsante back
function clickOnContatoreOrOreLavorate(){
	
	//vede quale delle due modalità non è hidden e gli assegna 
			//la classe hidden per nasconderla
		if(searchMode.display != "none"){
				searchMode.classList.add('hidden');
				searchTaskButton.classList.remove('opacity-50', 'pointer-events-none');		
		} 
		if(selectMode.display != "none") {
				selectMode.classList.add('hidden');	
				selectTaskButton.classList.remove('opacity-50', 'pointer-events-none');	
		}
		
		searchAndSelectTitle.classList.add('hidden');
		searchAndSelectButtonsContainer.classList.add('hidden');
		backTaskRapidButtonContainer.classList.remove('hidden');
			
}

//funzione che al click del backButton riprestina le modalità
//di ricerca o selezione task
function clickOnBackButton(){
	
	taskResumeTable.classList.add('hidden');
	searchAndSelectTitle.classList.remove('hidden');
	searchAndSelectButtonsContainer.classList.remove('hidden');
	backTaskRapidButtonContainer.classList.add('hidden');
	contatoreTaskRapid.classList.add('hidden');
	oreLavorateTaskRapidContainer.classList.add('hidden');
				
	

}

