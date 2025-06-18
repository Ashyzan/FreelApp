console.log("sono in dettaglioTaskStatstiche");

//recupero elementi dal DOM per descrizione task
const descrizioneContratta = document.getElementById('descrizioneContratta');
const descrizioneEspansa = document.getElementById('descrizioneEspansa');
const buttonEspandiDescrizione = document.getElementById('buttonEspandiDescrizione');
const buttonRiduciDescrizione = document.getElementById('buttonRiduciDescrizione');


//recupero elementi dal DOM per giorni chiusura stimata
const contenitoreGiorniChiusuraStimata = document.getElementById('contenitoreGiorniChiusuraStimata');


//recupero elementi dal DOM per aggiornamento statistiche ( no grafici)
//const dettaglioTaskOreLavorateOnload = document.getElementById('dettaglio-task-ore-lavorate-onload');
const dettaglioTaskOreLavorateAfterContatoreApi = document.getElementById('dettaglio-task-ore-lavorate-after-contatore-api');
const dettaglioTaskPauseOnload = document.getElementById('dettaglio-task-pause-onload');
const dettaglioTaskPauseAfterContatoreApi = document.getElementById('dettaglio-task-pause-after-contatore-api');
//const guadagnoAttualeTask = document.getElementById('guadagnoAttualeTask');
const dettaglioTaskGuadagnoAptferContatoreApi = document.getElementById('dettaglio-task-guadagno-after-contatore-api');
const buttonTopPauseContatoreIsRun = document.getElementById('pause-top-after-api');
const buttonBottomPauseContatoreIsRun = document.getElementById('pause-bottom-after-api')


// ************* logica di espansione e riduzione descrizione
if(buttonEspandiDescrizione != null){
	buttonEspandiDescrizione.addEventListener('click', mostraDescrizioneCompleta);	
}
buttonRiduciDescrizione.addEventListener('click', mostraDescrizioneRidotta);

function mostraDescrizioneCompleta(){
	descrizioneContratta.classList.add('hidden');
	descrizioneEspansa.classList.remove('hidden');
}

function mostraDescrizioneRidotta(){
	descrizioneContratta.classList.remove('hidden');
	descrizioneEspansa.classList.add('hidden');
}


//aggirnamento dati con eventlistener su button pause contatore top
if(buttonTopPauseContatoreIsRun != null){
	buttonTopPauseContatoreIsRun.addEventListener('click',function(){
		//cancello il precedente grafico dal template per poi creare il nuovo aggiornato
		let chartStatus = Chart.getChart("myChart"); // <canvas> id
		if (chartStatus != undefined) {
		  chartStatus.destroy();
		}
		apiStatisticheJson(descrizioneTaskId);
		
	});
}

//aggirnamento dati con eventlistener su button pause contatore bottom
if(buttonBottomPauseContatoreIsRun != null){
	buttonBottomPauseContatoreIsRun.addEventListener('click',function(){
		//cancello il precedente grafico dal template per poi creare il nuovo aggiornato
		let chartStatus = Chart.getChart("myChart"); // <canvas> id
		if (chartStatus != undefined) {
		  chartStatus.destroy();
		}
		apiStatisticheJson(descrizioneTaskId);
	});
}



//************** logica chiamata API per grafici statistiche */
const url_apiStatisticheTask = '/api/statistiche-dettaglio-task/';

async function apiStatisticheJson(idTask){
	console.log("statoTask: " + statoTask)
	//esegue il fetch solo se in task è attivo
	if(statoTask != "inattivo"){
		
		console.log("url statistiche: " + url_apiStatisticheTask)
		
		await fetch(url_apiStatisticheTask + idTask)
					   .then(response => {
					     if (!response.ok) {
					       throw new Error('Network response was not ok');
					     }
					     return response.json();
					   })
					   .then(datajson => {
						
						
							if(datajson.giorniChiusuraStimata != null){
								if(datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata > 0){
									contenitoreGiorniChiusuraStimata.innerHTML = `<div class="text-red-600 text-center mb-3">Chiusura stimata superata di ${datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata} giorni</div>`
								}
								
								if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili > 0){
									contenitoreGiorniChiusuraStimata.innerHTML = `	<div class="text-center font-bold pe-1 py-1">Countdown chiusura stimata</div>
																					<div class="md:hidden text-center mb-2"> giorni rimanenti ${datajson.giorniChiusuraStimata.giorniAncoraDisponibili }</div>
																					<canvas class="m-auto  mb-2" id="chiusuraStimata"></canvas>`
									creaGraficoGiorniStimati(datajson);	 
								} else if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili == 0 && datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata  == 0){
									contenitoreGiorniChiusuraStimata.innerHTML = `<div class="text-center mb-3">Oggi è il giorno di chiusura Stimata</div>`
								}							
							}
							
							tempoBudgetParzialeDaTipologia(datajson);
								 
							//aggiornamento ore lavorate
							dettaglioTaskOreLavorateAfterContatoreApi.innerHTML = `<div class="text-center">${datajson.oreLavorate}</div>`;	
							
							//aggiornamento pause
							dettaglioTaskPauseAfterContatoreApi.innerHTML = `<div class="text-center">${datajson.pauseTask}</div>`;										
								
							//aggiornamento guadagno
							dettaglioTaskGuadagnoAptferContatoreApi.innerHTML =`<div class="bg-[#FFE541]  rounded-lg py-4 px-7 text-[#0057A5] text-3xl font-bold ">${datajson.guadagnoAttualeTask}</div>`
					   })
					   .catch(error => {
					     console.error('Error:', error);
					   });
					   
	}
}




// *********** CREAZIONE GRAFICO LINEARE GIORNI STIMATI **********************

function creaGraficoGiorniStimati(datajson){
	
	//determinazione colore barra giorni residui in base alla percentuale 
	//tra il 100% ed il 25% dei giorni residui colore verde
	//tra il 21% ed il penultimo giorno arancione
	//ultimo giorno rosso
	let coloreProgressBar = 'rgba(60, 156, 108, 1)';
	if((datajson.giorniChiusuraStimata.giorniAncoraDisponibili/datajson.giorniChiusuraStimata.giorniTotaliStimati) < 0.25 && 
				(datajson.giorniChiusuraStimata.giorniAncoraDisponibili != 1) ){
		coloreProgressBar = 'rgba(255, 165, 0, 1)';
	} else if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili <= 1){
		coloreProgressBar = 'rgba(255, 0, 0, 1)';
	}
	
	// horizontal bar
	const data = {
		labels: ['Giorni rimanenti'],
		datasets: [{
			label: [],
			data: [datajson.giorniChiusuraStimata.giorniAncoraDisponibili],
			backgroundColor: [ coloreProgressBar],
			borderColor: ['rgba(219, 220, 221, 0.2)'],
			borderWidth: 0,
			borderSkipped: false,
			borderRadius: 4,
			barPercentage: 0.3
		}]
	};

	//creo blocco ProgressBar che verrà richiamato più in basso nelle config del grafico
	const progressBar = {
		id: 'progressBar',
		beforeDatasetsDraw(chart, args, pluginOptions) {
			const { ctx, data, chartArea: { top, bottom, left, right, width, height },
				scales: { x, y } } = chart;

			ctx.save();

			//etichetta barra sinistra
			ctx.font = '12px sans-serif';
			ctx.fillStyle = 'rgba(102, 102, 102, 1)';
			ctx.teztAlign = 'left';
			ctx.textBaseline = 'middle';
			ctx.fillText(dataInizioTask, left + 5, y.getPixelForValue(0) - 20);

			//etichetta barra destra
			ctx.font = '12px sans-serif';
			ctx.fillStyle = 'rgba(102, 102, 102, 1)';
			ctx.teztAlign = 'right';
			ctx.textBaseline = 'middle';
			ctx.fillText(dataFineStimata, right - 70, y.getPixelForValue(0) - 20);

			ctx.fillStyle = 'rgba(219, 220, 221, 1)';
			ctx.fillRect(left, y.getPixelForValue(0) - 8, width, 17)

			
		}
	}

	// richiamo id grafico chiusura stimata file html
	const chiusuraStimata = document.getElementById('chiusuraStimata');
	// inserisco i data e configuro	 	
	 new Chart(chiusuraStimata, {
		type: 'bar',
		data: data,
		options: {
			aspectRatio: 4,
			responsive: false,
			indexAxis: 'y',
			plugins: {
				legend: {
					display: false
				}
			},
			scales: {
				x: {
					grace:  (datajson.giorniChiusuraStimata.giorniTotaliStimati - datajson.giorniChiusuraStimata.giorniAncoraDisponibili),
					reverse: true,
					grid: {
						display: false,
					},
					border: {
						display: false
					},
					ticks: {
						display: false,
						min: 0,
						max: datajson.giorniChiusuraStimata.giorniTotaliStimati,
						stepSize: 1,
					}
				},
				y: {
					beginAtZero: true,
					grace: 0,
					grid: {
						display: false,
					},
					border: {
						display: false
					},
					ticks: {
						display: false,
					}
				}
			},

		},
		plugins: [progressBar]
	});
}


//******************************* FUNZIONE CREAZIONE GRAFICO TEMPO/BUDGET PARZIALE IMPIEGATO IN BASE TIPOLOGIA **********************/

function tempoBudgetParzialeDaTipologia(datajson){
	
	
	let titoloGrafico = null;
	let unitaLabel = null;
	if(datajson.tipologiaProgetto ==  "budget"){
		titoloGrafico ="Budget totale progetto: " + datajson.budgetTotaleProgetto + " €"
		unitaLabel = " €";
	}
	
	if(datajson.tipologiaProgetto ==  "ore"){
			titoloGrafico ="Monte ore progetto: " + datajson.budgetTotaleProgetto + " ore"
			unitaLabel = " ore";
		}
	
	const data = {
		labels: [
			'Residuo',
			'Task',
			'Altri task'
		],
		datasets: [{
			label: unitaLabel,
			data: [(datajson.budgetTotaleProgetto - datajson.budgetImpiegatoDalTask), datajson.budgetImpiegatoDalTask, datajson.budgetImpiegatoDaAltriTask],
			backgroundColor: [
				'rgba(0, 87, 165, 0.3)',
				'rgb(255, 87, 165)',
				'rgb(0, 87, 165)'
			],
			hoverOffset: 4
		}]
	};

	// richiamo id ciambella nel file html
	const donut = document.getElementById('myChart');
	// inserisco i data	 
	
		 new Chart(donut, {
		type: 'doughnut',
		data: data,
		options: {
			responsive: false,
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: titoloGrafico
				}
			}
		},
	});

}


//***************************** FUNZIONE AGGIORNAMENTO DATI STATISTICI (NO GRAFICI) al click del pause ************************/





