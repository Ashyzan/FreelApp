console.log("sono in dettaglioTaskStatstiche");

//recupero elementi dal DOM
const contenitoreGiorniChiusuraStimata = document.getElementById('contenitoreGiorniChiusuraStimata');



const url_apiStatisticheTask = '/api/statistiche-dettaglio-task/';

function apiStatisticheJson(idTask){
	
	console.log("url statistiche: " + url_apiStatisticheTask)
	
	fetch(url_apiStatisticheTask + idTask)
				   .then(response => {
				     if (!response.ok) {
				       throw new Error('Network response was not ok');
				     }
				     return response.json();
				   })
				   .then(datajson => {
						if(datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata > 0){
							contenitoreGiorniChiusuraStimata.innerHTML = `<div class="text-red-600 text-center mb-3">Chiusura stimata superata di ${datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata} giorni</div>`
						}
						
						if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili > 0){
							contenitoreGiorniChiusuraStimata.innerHTML = `	<div class="text-center font-bold pe-1 py-1">Countdown fine stimata</div>
																			<div class="md:hidden text-center mb-2"> giorni rimanenti ${datajson.giorniChiusuraStimata.giorniAncoraDisponibili }</div>
																			<canvas class="m-auto  mb-2" id="chiusuraStimata"></canvas>`
							creaGraficoGiorniStimati(datajson);	 
						} else if(datajson.giorniChiusuraStimata.giorniAncoraDisponibili == 0 && datajson.giorniChiusuraStimata.giorniOltreChiusuraStimata  == 0){
							contenitoreGiorniChiusuraStimata.innerHTML = `<div class="text-center mb-3">Oggi è il giorno di chiusura Stimata</div>`
						}
						
						tempoBudgetParzialeDaTipologia(datajson);
							 
				   })
				   .catch(error => {
				     console.error('Error:', error);
				   });
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
	
	const data = {
		labels: [
			'Bundget  residuo',
			'Budget impiegato',
		],
		datasets: [{
			label: 'Percentuale',
			data: ['20', '35'],
			backgroundColor: [
				'rgb(0, 87, 165)',
				'rgb(255, 205, 86)'
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
			responsive: true,
			plugins: {
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Utilizzo budget del progetto'
				}
			}
		},
	});

}