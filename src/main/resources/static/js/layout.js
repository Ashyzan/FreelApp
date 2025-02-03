
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

//recupero elementi dal DOM per accordion
function toggleAccordion(index) {
    const content = document.querySelector(`#content-${index}`);
    const line = document.querySelector(`.line-${index}`);
      line.classList.toggle('hidden');
      content.classList.toggle('hidden');
    }
  
// recuper elementi dal DOM per manipolazione input fatturazione per tipologia
const aziendaInput = document.getElementById('aziendaInput');
const privatoInput = document.getElementById('privatoInput');

		  
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
deleteButton.addEventListener('click', function(){
	modalDelete.classList.remove('scale-0');
})

//chiusura modalDelete
modalDeleteCloseButton.addEventListener('click', function(){
	modalDelete.classList.add('scale-0');
})

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
		 

// funzione per cambiare input fatturazione cliente in base alla tipologia

function tipologiaSelect(value){
	if(value = "azienda"){
		aziendaInput.classList.toggle('hidden');
	} if(value = "privato") {
		privatoInput.classList.toggle('hidden');
	}

}