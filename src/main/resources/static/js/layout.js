
// recupero elementi dal DOM per navBar	
const navBar = document.querySelector('.nav-bar');
const navOptionsMobile = document.querySelector('#options-button-mobile');
const navOptionsDesktop = document.querySelector('.nav-option-desktop');

// recupero elementi dal DOM per modale	
const modal = document.querySelector('#modal');
const nuovoProgettoButton = document.querySelector('#nuovoProgettoButton');
const modalCloseButton = document.getElementById('modalCloseButton');

//recupero elementi dal Dom per modalDelete
const modalDelete =document.getElementById('modalDelete');
const deleteButton = document.getElementById('deleteButton');
const modalDeleteCloseButton = document.getElementById('modalDeleteCloseButton');


//recupero elementi dal DOM per modalSTOP
const modalStopButton = document.getElementById('modalStopButton');
const stopButtonBottom = document.getElementById('stopButtonBottom');
const stopButtonTop = document.getElementById('stopButtonTop');
const modalStopCloseButton = document.getElementById('modalStopCloseButton')
  

//recupero elementi dal DOM per modalPAUSE&START
const modalPauseStartButton = document.getElementById('modalPauseStartButton');
const modalStartTopButton = document.getElementById('modalStartTopButton');
const modalStartBottomButton = document.getElementById('modalStartBottomButton');
const modalPauseTopButton = document.getElementById('modalPauseTopButton');
const modalPauseBottomButton = document.getElementById('modalPauseBottomButton');
const modalPauseStartCloseButton = document.getElementById('modalPauseStartCloseButton');
const formStartPause = document.getElementById('form-start-pause');


// funzioni per navBar 		
function onToggleMenu(e){
	navBar.classList.toggle('top-[-100%]')
	navBar.classList.toggle('top-14')
}

function onToggleOptionsMobile(e){
	navOptionsMobile.classList.toggle('hidden')
}

function onToggleOptionsDesktop(e){
	navOptionsDesktop.classList.toggle('top-[-100%]')
	navOptionsDesktop.classList.toggle('top-14')
}

// funzioni per modale

//apertura modalDelete
if(deleteButton != null){
	deleteButton.addEventListener('click', function(){
		modalDelete.classList.remove('scale-0');
	})	
}

//chiusura modalDelete
if(modalDeleteCloseButton != null){
	modalDeleteCloseButton.addEventListener('click', function(){
		modalDelete.classList.add('scale-0');
	})	
}



//chiusura modale nuovoProgetto
if(modalCloseButton != null){
	modalCloseButton.addEventListener('click', function(){
		closeModal()
	})	
}

//apertura modale nuovoProgetto
if(nuovoProgettoButton != null){
	nuovoProgettoButton.addEventListener('click', function(){
		openModal()
	})	
}

//apertura modale STOP BUTTON top
if(stopButtonTop != null){
	stopButtonTop.addEventListener('click', function(){
		modalStopButton.classList.remove('scale-0');
	})
}
//apertura modale STOP BUTTON bottom
if(stopButtonBottom != null){
	stopButtonBottom.addEventListener('click', function(){
		modalStopButton.classList.remove('scale-0');
	})	
}

//chiusura modale STOP BUTTON
if(modalStopCloseButton != null){
	modalStopCloseButton.addEventListener('click', function(){
		modalStopButton.classList.add('scale-0')
	})	
}

//apertura modale PAUSE-START BUTTON (START top)
if(modalStartTopButton != null){
	modalStartTopButton.addEventListener('click', function(){
		formStartPause.action = `/start/${taskInUsoId}`;
		modalPauseStartButton.classList.remove('scale-0');
	})	

}

//apertura modale PAUSE-START BUTTON (START bottom)
if(modalStartBottomButton != null){
	modalStartBottomButton.addEventListener('click', function(){
		formStartPause.action = `/start/${taskInUsoId}`;
		modalPauseStartButton.classList.remove('scale-0');
	})	
}

//apertura modale PAUSE-START BUTTON (PAUSE top)
if(modalPauseTopButton != null){
	modalPauseTopButton.addEventListener('click', function(){
		formStartPause.action = `/Contatore/pause/${taskInUsoId}`;
		modalPauseStartButton.classList.remove('scale-0');
	})	
}

//apertura modale PAUSE-START BUTTON (PAUSE bottom)
if(modalPauseBottomButton != null){
	modalPauseBottomButton.addEventListener('click', function(){
		formStartPause.action = `/Contatore/pause/${taskInUsoId}`;
		modalPauseStartButton.classList.remove('scale-0');
	})	
}

//chiusura modale PAUSE-START BUTTON
if(modalPauseStartCloseButton != null){
	modalPauseStartCloseButton.addEventListener('click', function(){
		modalPauseStartButton.classList.add('scale-0')
	})	
}

//funzione per aprire la modale
function openModal(){
	modal.classList.remove('scale-0');
}

//funzione per chiudere la modale
function closeModal(){
	modal.classList.add('scale-0');
}		  
		 

