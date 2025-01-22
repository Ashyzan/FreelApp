
// recupero elementi dal DOM per navBar	
const navBar = document.querySelector('.nav-bar');
const navOptionsMobile = document.querySelector('#options-button-mobile');
const navOptionsDesktop = document.querySelector('.nav-option-desktop');

// recupero elementi dal DOM per modale	
const modalDelete = document.querySelector('#modalDelete');
const deleteButton = document.querySelector('#deleteButton');
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

//chiusura modale 
modalCloseButton.addEventListener('click', function(){
	closeModal()
})

//apertura modale 
deleteButton.addEventListener('click', function(){
	openModal()
})

//funzione per aprire la modale
function openModal(){
	modalDelete.classList.remove('scale-0');
}

//funzione per chiudere la modale
function closeModal(){
	modalDelete.classList.add('scale-0');
}		  

