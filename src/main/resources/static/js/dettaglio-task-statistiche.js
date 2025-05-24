console.log("sono in dettaglioTaskStatstiche");

//recupero elementi dal DOM
const guadagnoAttualeTask = document.getElementById('guadagnoAttualeTask');
const pauseTask = document.getElementById('pauseTask');

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
								
								 guadagnoAttualeTask.innerHTML = datajson.guadagnoAttualeTask
								 pauseTask.innerHTML = datajson.pauseTask
								 
					 // donuts
					 		 const data = {
					 		   labels: [
					 		     'Tempo Stimato',
					 		     'Tempo lavorato',
					 		     		  ],
					 		   datasets: [{
					 		     label: 'Percentuale',
					 		     data: [datajson.finaltime, datajson.tempolavorato],
					 		     backgroundColor: [
					 		       'rgb(0, 87, 165)',
					 		       'rgb(255, 205, 86)'
					 		     ],
					 		     hoverOffset: 4
					 		   }]
					 		 };
							 
							 // richiamo id ciambella nel file html
							 const donut = document.getElementById('donut');
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
							 	         text: 'Satistica del task'
							 	       }
							 	     }
							 	   },
							 	 });
							 
				   })
				   .catch(error => {
				     console.error('Error:', error);
				   });
}