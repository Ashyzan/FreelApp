// recupero elementi dal DOM per navBar	
const navBar = document.querySelector('.nav-bar');
const navOptionsMobile = document.querySelector('#options-button-mobile');
const navOptionsDesktop = document.querySelector('.nav-option-desktop');

// recupero elementi dal DOM per modale	
const modal = document.querySelector('#modal');
const nuovoProgettoButton = document.querySelector('#nuovoProgettoButton');
const modalCloseButton = document.querySelector('#modalCloseButton');
		  
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

//chiusura modale nuovoProgetto
modalCloseButton.addEventListener('click', function(){
	closeModal()
})

//apertura modale nuovoProgetto
nuovoProgettoButton.addEventListener('click', function(){
	openModal()
})

//funzione per aprire la modale
function openModal(){
	modal.classList.remove('scale-0');
}

//funzione per chiudere la modale
function closeModal(){
	modal.classList.add('scale-0');
}		  
		  