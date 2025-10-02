
//recupero elementi dal DOM
const tasks = document.getElementsByClassName('lista-ultimi-task');

//elementi dal DOM per progressBar
const etichettaProgressBarGoal = document.getElementById('etichetta-progress-bar-goal');
const legendaProgressBarGoal = document.getElementById('legenda-progress-bar-goal');
const guadagnoAttualeDiv = document.getElementById('guadagno-attuale');
const tooltipProgressBarGoalAnnuale = document.getElementById('tooltip-progress-bar');

//elementi dal DOM per statistiche task
const containerGraficiStatisticheTask = document.getElementById('container-task-bars');
const etichettaTaskBarCreati = document.getElementById('etichetta-task-creati');
const etichettaTaskBarAttivi = document.getElementById('etichetta-task-incorso');
const etichettaTaskBarChiusi = document.getElementById('etichetta-task-chiusi');
const etichettaTaskTotali = document.getElementById('etichetta-task-totali');
const etichettaTaskParzialiTotali = document.getElementById('etichetta-parziali-task-totali');

//elementi dal DOM per statistiche progetti
const containerStatisticheProgetti = document.getElementById('container-statistiche-progetti');


//creazione data di oggi
const timeElapsed = Date.now();
const today = new Date(timeElapsed);


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
							creaGraficiStatisticheTask(datajson)
							creaGraficoStatisticheProgetti(datajson) 
					   })
					   .catch(error => {
					     console.error('Error:', error);
					   });
					   
}


//*************** funzioni per creazione grafico raggiungimento goal progress bar************************/

function creaProgressBar(datajson){
	
	const progressBarGuadagno = document.querySelector('.progress-bar-uno');
	const progressBarFatturato = document.querySelector('.progress-bar-due');
	let widthGuadagno = 0;
	let widthFatturato = 0;
	let interval = setInterval(function() {
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
	  creaGraficoStatisticheProgetti(datajson)
}

function creazioneEtichettaLegendaProgressBarGoal(datajson){
	guadagnoAttualeDiv.innerHTML = datajson.guadagnoAnnoCorrente.toFixed(2).replace(".",",") + " €";
	etichettaProgressBarGoal.innerHTML = datajson.goalAnnualeUtente + "€";
		legendaProgressBarGoal.innerHTML = `<div class="flex items-center gap-2">
												<div class="h-3 w-3 bg-fuchsia-500 rounded-full"></div>
												<span class="text-[#0057A5] text-xs"><strong>Fatturato: </strong>${datajson.fatturatoAnnoCorrente.toFixed(2).replace(".",",")}€</span>
											</div>
											<div class="flex items-center gap-2">
												<div class="h-3 w-3 bg-fuchsia-300 rounded-full"></div>
												<span class="text-[#0057A5] text-xs"><strong>Guadagno: </strong>${datajson.guadagnoAnnoCorrente.toFixed(2).replace(".",",")}€</span>
											</div>
											<div class="flex items-center gap-2">
												<div class="h-3 w-3 bg-white rounded-full"></div>
												<span class="text-[#0057A5] text-xs"><strong>Goal annuale: </strong>${datajson.goalAnnualeUtente}€</span>
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


//******************* funzioni per creazione grafici statistiche task ****************************
function creaGraficiStatisticheTask(datajson){
	
	const taskBarCreati = document.getElementById('task-bar-creati');
	const taskBarInCorso = document.getElementById('task-bar-incorso');
	const taskBarChiusi = document.getElementById('task-bar-chiusi');
	const taskBarTotaliAC = document.getElementById('task-bar-totali-ac');
	const taskBarTotaliAP = document.getElementById('task-bar-totali-ap');
	
	//dimensioni bar dei task totali in basso
	taskBarTotaliAC.style.width = ((datajson.statisticheTask.taskApertiAnnoCorrente_Attivi/datajson.statisticheTask.taskTotali_Attivi)*100)+ '%'
	taskBarTotaliAP.style.width = ((datajson.statisticheTask.taskApertiAnniPrecedenti_Attivi/datajson.statisticheTask.taskTotali_Attivi)*100)+ '%'
	
	//dimensionamento con animazione delle bar dei task dell'anno in corso
	let widthTaskBarCreati = 0;
	let widthTaskBarInCorso = 0;
	let widthTaskBarChiusi = 0;
	
		let interval = setInterval(function() {
		    if (widthTaskBarCreati >= (datajson.statisticheTask.taskApertiAnnoCorrente/datajson.statisticheTask.taskApertiAnnoCorrente)*100 
						&& widthTaskBarInCorso >= ((datajson.statisticheTask.taskApertiAnnoCorrente_Attivi/datajson.statisticheTask.taskApertiAnnoCorrente)*100)
						&& widthTaskBarChiusi >= ((datajson.statisticheTask.taskApertiAnnoCorrente_NonAttivi/datajson.statisticheTask.taskApertiAnnoCorrente)*100)){
		      clearInterval(interval);
		    } 
			if(widthTaskBarCreati < (datajson.statisticheTask.taskApertiAnnoCorrente/datajson.statisticheTask.taskApertiAnnoCorrente)*100){
		      widthTaskBarCreati += 1; // Incrementa la larghezza di 1%
		      taskBarCreati.style.width = widthTaskBarCreati + '%';
		    }
			if(widthTaskBarInCorso < ((datajson.statisticheTask.taskApertiAnnoCorrente_Attivi/datajson.statisticheTask.taskApertiAnnoCorrente)*100)){
			  widthTaskBarInCorso += 1
			  taskBarInCorso.style.width = widthTaskBarInCorso + '%';
			}
			if(widthTaskBarChiusi < ((datajson.statisticheTask.taskApertiAnnoCorrente_NonAttivi/datajson.statisticheTask.taskApertiAnnoCorrente)*100)){
			  widthTaskBarChiusi += 1
			  taskBarChiusi.style.width = widthTaskBarChiusi + '%';
			}
		  }, 10); // Aggiorna ogni 10 millisecondi
 	riempimentoetichetteTaskBars(datajson)
}

function riempimentoetichetteTaskBars(datajson){
	
	
	etichettaTaskBarCreati.innerHTML = datajson.statisticheTask.taskApertiAnnoCorrente;
	etichettaTaskBarAttivi.innerHTML = datajson.statisticheTask.taskApertiAnnoCorrente_Attivi;
	etichettaTaskBarChiusi.innerHTML = datajson.statisticheTask.taskApertiAnnoCorrente_NonAttivi;
	etichettaTaskTotali.innerHTML = datajson.statisticheTask.taskTotali_Attivi + " task attivi al " + today.toLocaleDateString()
	etichettaTaskParzialiTotali.innerHTML = `	<div class="flex items-center">
													<div class="h-3 w-3 bg-orange-400 rounded-full"></div>
													<span class="text-[#0057A5] text-[10px] ps-2"><strong>Anno corrente: </strong>${datajson.statisticheTask.taskApertiAnnoCorrente_Attivi}</span>
												</div>
												<div class="flex items-center ">
													<div class="h-3 w-3 bg-yellow-400 rounded-full"></div>
													<span class="text-[#0057A5] text-[10px] ps-2"><strong>Anni precedenti: </strong>${datajson.statisticheTask.taskApertiAnniPrecedenti_Attivi}</span>
												</div>`
}





//************** funzioni per la creazione dei grafici delle statistiche progetti ******************/
function creaGraficoStatisticheProgetti(datajson) {
	
	creaLegendaStatisticheProgetti(datajson)
	
	const progettiTotaliAttivi = document.getElementById('progetti-totali-attivi')
	progettiTotaliAttivi.innerHTML = datajson.statisticheProgetti.progettiTotali_Attivi + " progetti attivi al " + today.toLocaleDateString()
	
		let titoloGrafico ="Progetti del "+ today.getFullYear() + ": ";
		
		
		const data = {
			labels: [
				'Creati nel ' + today.getFullYear(),
				'Chiusi',
				'Residui anni scorsi',
			],
			datasets: [{
				data: [
					datajson.statisticheProgetti.progettiApertiAnnoCorrente,
					0,
					datajson.statisticheProgetti.progettiApertiAnniPrecedenti_Attivi
				],
				backgroundColor: [
					'rgb(56, 189, 249)',
					'rgb(255, 255, 255)',
					'rgb(250, 204, 20)'
				],
				hoverOffset: 4
			},
			{label: [],
						data: [
							datajson.statisticheProgetti.progettiApertiAnnoCorrente_Attivi,
							datajson.statisticheProgetti.progettiApertiAnnoCorrente_NonAttivi,
							datajson.statisticheProgetti.progettiApertiAnniPrecedenti_Attivi
						],
						backgroundColor: [
							'rgb(74, 223, 129)',
							'rgb(248, 113, 113)',
							'rgb(255, 255, 255)'
						],
						hoverOffset: 4
					}]
		};

		//distruggo eventuali generazioni precedenti di questo grafico
		let chartStatus = Chart.getChart("grafico-statistiche-progetti"); // <canvas> id
		if (chartStatus != undefined) {
				  chartStatus.destroy();
				}
				
				
		// richiamo id ciambella nel file html
		const donutProgetti = document.getElementById('grafico-statistiche-progetti');
		// inserisco i data	 
		
			 new Chart(donutProgetti, {
			type: 'doughnut',
			data: data,
			options: {
				responsive: true,
				plugins: {
					legend: {
						display: false,
						position: 'top',
					},
					labels:{
					datalabels:['in corso']	
					},
					title: {
						display: false,
						text: titoloGrafico
					}
				}
			},
		});
}

//funzione che crea  legenda grafico progetti
function creaLegendaStatisticheProgetti(datajson){
	const legendaGraficoProgetti = document.getElementById('legenda-grafico-progetti');
	legendaGraficoProgetti.innerHTML = `	<ul class="w-full text-[#0057A5]">
												<li class="flex gap-2 mb-2">
													<div class="h-3 w-3 bg-sky-400 rounded-full"></div>
													<span class="text-[10px]">Creati: ${datajson.statisticheProgetti.progettiApertiAnnoCorrente}</span>
												</li>
												<li class="flex gap-2 mb-2">
													<div class="h-3 w-3 bg-yellow-400 rounded-full"></div>
													<span class="text-[10px]">Attivi anni scorsi: ${datajson.statisticheProgetti.progettiApertiAnniPrecedenti_Attivi}</span>
												</li>
												<li class="flex gap-2 mb-2">
													<div class="h-3 w-3 bg-green-400 rounded-full"></div>
													<span class="text-[10px]">In corso: ${datajson.statisticheProgetti.progettiApertiAnnoCorrente_Attivi}</span>
												</li>
												<li class="flex gap-2 mb-2">
													<div class="h-3 w-3 bg-red-400 rounded-full"></div>
													<span class="text-[10px]">Chiusi: ${datajson.statisticheProgetti.progettiApertiAnnoCorrente_NonAttivi}</span>
												</li>
											</ul>`;
}	
	

