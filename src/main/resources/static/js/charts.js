
//API 
const api_urlProjectsArchived = 'http://localhost:8080/api/statistics/achived-projects'

async function getProjectsArchived(){
	
		const response = await fetch(api_urlProjectsArchived);
		const projectsList = await response.json();
		
		//creo due array vuoti che conterrano i dati degli assi del grafico e verrano utilizzati come etichette degli assi
		const projectNameList = [];
		const coutnTaskOfProject = [];
		
		projectsList.forEach(item =>{
		
			projectNameList.push(item.nome);
			coutnTaskOfProject.push(item.countTaskProgetto);
		
		}) 
		
		//creazione grafico
			  const ctx = document.getElementById('myChart');

			  new Chart(ctx, {
			    type: 'bar',
			    data: {
			      labels: projectNameList,
			      datasets: [{
			        label: 'N. task',
			        data: coutnTaskOfProject,
			        borderWidth: 1
			      }]
			    },
			    options: {
			      scales: {
			        y: {
			          beginAtZero: true
			        }
			      }
			    }
			  });
			 }
		
			 // TEST ROSA

			 const api_rosaTest = 'http://localhost:8080/api/statistiche-test'

			 // Make a GET request
		 fetch(api_rosaTest)
			   .then(response => {
			     if (!response.ok) {
			       throw new Error('Network response was not ok');
			     }
			     return response.json();
			   })
			   .then(datajson => {
							 
				 // donuts
				 		 const data = {
				 		   labels: [
				 		     'Tempo Stimato',
				 		     'Tempo lavorato',
				 		     		  ],
				 		   datasets: [{
				 		     label: 'Percentuale',
				 		     data: [datajson.tempostimato, datajson.tempolavorato],
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


			

		
		
		

	