
//recupero elementi dal DOM
const tasks = document.getElementsByClassName('lista-ultimi-task');
const contenitoreAvanzamentoGoal = document.getElementById('contenitoreAvanzamentoGoal');




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


//*************** funzioni per creazione grafico raggiungimento goal ************************/

function creaProgressBar(datajson){
	
	var progressBarUno = document.querySelector('.progress-bar-uno');
	var progressBarDue = document.querySelector('.progress-bar-due');
	  var maxWidth = document.querySelector('.progress').offsetWidth; // Larghezza del contenitore
	  var widthUno = 0;
	  var widthDue = 0;
	  var interval = setInterval(function() {
	    if (widthUno >= 70 && widthUno >= 40){
	      clearInterval(interval);
	    } 
		if(widthUno < 70){
	      widthUno += 1; // Incrementa la larghezza di 1%
	      progressBarUno.style.width = widthUno + '%';
	    }
		if(widthDue < 40){
		  widthDue += 1
		  progressBarDue.style.width = widthDue + '%';
		  }
	  }, 10); // Aggiorna ogni 10 millisecondi

	
}