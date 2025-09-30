
//recupero elementi dal DOM
const tasks = document.getElementsByClassName('lista-ultimi-task');
const etichettaProgressBarGoal = document.getElementById('etichetta-progress-bar-goal');
const legendaProgressBarGoal = document.getElementById('legenda-progress-bar-goal');
const guadagnoAttualeDiv = document.getElementById('guadagno-attuale');
const tooltipProgressBarGoalAnnuale = document.getElementById('tooltip-progress-bar');




//*************** funzioni per attivazione/disattivazione animazione per task in uso da lista task recenti ************************/

function attivaElementoInTaskList(){
	for(i=0; i<tasks.length; i++){
		console.log('tasks[i].id -> ' + tasks[i].id)
		if(tasks[i].id == taskInUsoId && contatoreIsRun == true){
			
			const taskUtilizzato = document.getElementById('task-in-uso-' + taskInUsoId)
			taskUtilizzato.innerHTML = `<img src="/img/sources/icons/clock.gif" alt="task-in-uso" class="size-8 inline float-right rounded-full">`
		}
	}
	
}


function disattivaElementoInTaskList(){
	for(i=0; i<tasks.length; i++){
		console.log('tasks[i].id -> ' + tasks[i].id)
		if(tasks[i].id == taskInUsoId){
			
			const taskUtilizzato = document.getElementById('task-in-uso-' + taskInUsoId)
			taskUtilizzato.innerHTML = ``
		}
	}
	
}








//************** logica chiamata API per grafici statistiche */
const url_apiStatisticheDashBoard = '/api/statistiche-dashboard';


function apiStatisticheDashboard(){
	
		 fetch(url_apiStatisticheDashBoard)
					   .then(response => {
					     if (!response.ok) {
					       throw new Error('Network response was not ok');
					     }
					     return response.json();
					   })
					   .then(datajson => {
							creaProgressBar(datajson);	 
					   })
					   .catch(error => {
					     console.error('Error:', error);
					   });
					   
}


//*************** funzioni per creazione grafico raggiungimento goal progress bar************************/

function creaProgressBar(datajson){
	
	var progressBarGuadagno = document.querySelector('.progress-bar-uno');
	var progressBarFatturato = document.querySelector('.progress-bar-due');
	  //var maxWidth = document.querySelector('.progress').offsetWidth; // Larghezza del contenitore
	  var widthGuadagno = 0;
	  var widthFatturato = 0;
	  var interval = setInterval(function() {
	    if (widthGuadagno >= ((datajson.guadagnoAnnoCorrente/datajson.goalAnnualeUtente)*100) && widthGuadagno >= ((datajson.fatturatoAnnoCorrente/datajson.goalAnnualeUtente)*100)){
	      clearInterval(interval);
	    } 
		if(widthGuadagno < ((datajson.guadagnoAnnoCorrente/datajson.goalAnnualeUtente)*100)){
	      widthGuadagno += 1; // Incrementa la larghezza di 1%
	      progressBarGuadagno.style.width = widthGuadagno + '%';
	    }
		if(widthFatturato < ((datajson.fatturatoAnnoCorrente/datajson.goalAnnualeUtente)*100)){
		  widthFatturato += 1
		  progressBarFatturato.style.width = widthFatturato + '%';
		  }
	  }, 10); // Aggiorna ogni 10 millisecondi

	  creazioneEtichettaLegendaProgressBarGoal(datajson);
	  creaTooltipProgressBar(datajson);
}

function creazioneEtichettaLegendaProgressBarGoal(datajson){
	guadagnoAttualeDiv.innerHTML = datajson.guadagnoAnnoCorrente.toFixed(2).replace(".",",") + " €";
	etichettaProgressBarGoal.innerHTML = datajson.goalAnnualeUtente + "€";
	legendaProgressBarGoal.innerHTML = `<div class="flex items-center gap-2 pt-2">
												<div class="h-3 w-3 border border-black bg-white rounded-full"></div>
												<span class="text-[#0057A5] text-xs"><strong>Goal annuale: </strong>${datajson.goalAnnualeUtente}€</span>
											</div>
											<div class="flex items-center gap-2">
												<div class="h-3 w-3 border border-black bg-fuchsia-500 rounded-full"></div>
												<span class="text-[#0057A5] text-xs"><strong>Fatturato: </strong>${datajson.fatturatoAnnoCorrente.toFixed(2).replace(".",",")}€</span>
											</div>
											<div class="flex items-center gap-2">
												<div class="h-3 w-3 border border-black bg-fuchsia-300 rounded-full"></div>
												<span class="text-[#0057A5] text-xs"><strong>Guadagno: </strong>${datajson.guadagnoAnnoCorrente.toFixed(2).replace(".",",")}€</span>
											</div>`;
	
}

//funzione per la creazione del tooltip della prograssBar raggiungimento goal annuale
function creaTooltipProgressBar(datajson){
	tooltipProgressBarGoalAnnuale.innerHTML = "Fatturati " + datajson.fatturatoAnnoCorrente.toFixed(2).replace(".",",") +"€ su un guadagno di " + datajson.guadagnoAnnoCorrente.toFixed(2).replace(".",",") + "€";
	console.log("tooltip creato")
}

//tooltip PROGRESSbar
function avviaTooltipProgressBar(event){
	console.log("tooltip attivato")
	tooltipProgressBarGoalAnnuale.classList.remove('hidden')
	tooltipProgressBarGoalAnnuale.style.left = (event.clientX -20)+'px'
	tooltipProgressBarGoalAnnuale.style.top = (event.clientY - 25)+'px'
	
}
function terminaTooltipProgressBar(){
	tooltipProgressBarGoalAnnuale.classList.add('hidden')
	tooltipProgressBarGoalAnnuale.classList.remove('text-opacity-25')
}
