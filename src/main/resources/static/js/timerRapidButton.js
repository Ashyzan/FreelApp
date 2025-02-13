
// recupero elementi dal DOM per modalTimerRapidButton
const modalTimerRapidButton =document.getElementById('modalTimerRapidButton');
const timerRapidButton = document.getElementById('timerRapidButton');
const modalTimerRapidCloseButton = document.getElementById('modalTimerRapidCloseButton');
const taskResumeTable = document.getElementById('taskResumeTable');		
const formTask = document.getElementById('form-task');

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
			document.getElementById('form-task-select').submit()
		})

function openModalSelectedTask(){
	if (taskSelectedId != null){
		
		modalTimerRapidButton.classList.remove('scale-0');
		formTask.classList.add('hidden');
									
	}
}