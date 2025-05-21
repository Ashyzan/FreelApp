// recupero elementi dal DOM per errore dimensione input
const errorMaxFileSizeContainer = document.getElementById('errorMaxFileSize');
let errorMaxFileSize;

  
// recupero elementi dal DOM per manipolazione input fatturazione per tipologia
const aziendaInput = document.getElementById('aziendaInput');
const privatoInput = document.getElementById('privatoInput');
const contattiAzienda = document.getElementById('contattiAzienda');
const tipologiaEdit = document.getElementById('tipologia');
const ragioneSociale = document.getElementById('ragioneSociale');
const partitaIva = document.getElementById('PartitaIva');


//recupero elementi dal DOM per ottimizzazione layout validazione dati 
//(anticipazione errori backend per armonia layout)
const labelCliente = document.getElementById('labelCliente');
const labelClienteError = document.getElementById('labelClienteError');
const email = document.getElementById('email');
const emailError = document.getElementById('emailError');

//recuper elementi dal dom per validazione form (azienda o privato)
const formCliente = document.getElementById('formCliente');
const ragioneSocialeError = document.getElementById('ragioneSocialeError');
const partitaIvaError = document.getElementById('partitaIvaError');
const nome = document.getElementById('nome');
const nomeError = document.getElementById('nomeError');
const cognome = document.getElementById('cognome');
const cognomeError = document.getElementById('cognomeError');
const telefono = document.getElementById('telefono');
const telefonoError = document.getElementById('telefonoError');


	
//gestione visualizzazione campi se tipologia == azienda in caso di modifica
if(tipologiaEdit.value == "azienda"){
	aziendaInput.classList.remove('hidden');
	contattiAzienda.classList.remove('hidden');
	privatoInput.classList.add('hidden');
	ragioneSociale.classList.add('required');
}
	
// funzione per cambiare input fatturazione cliente in base alla tipologia
function tipologiaSelect(value){
	if(value = "azienda"){
		aziendaInput.classList.toggle('hidden');
		contattiAzienda.classList.toggle('hidden');
	} if(value = "privato") {
		privatoInput.classList.toggle('hidden');
	}

}


//funzione che visualizza anteprima immagine selezionata prima di fare upload e fa il controllo frontend delle dimensioni
		var previewUploadFile = function(event){
				errorMaxFileSizeContainer.innerHTML = "";
				inputFile.classList.remove('border')
				const fileSize = inputFile.files[0].size
				var previewLogoCliente = document.getElementById('previewLogoCliente');
				console.log(fileSize)
				previewLogoCliente.src = URL.createObjectURL(event.target.files[0]);//riempe la memoria temporanea con il file
				previewLogoCliente.onload = function(){
				URL.revokeObjectURL(previewLogoCliente.src) //svuota la memoria temporanea dal file
				if (fileSize > 500000){
					errorMaxFileSize = "";
					errorMaxFileSizeContainer.innerHTML = "Non è possibile fare l'upload di file superiori a 500KB"
					inputFile.classList.add('border')
				}	
			} 
		}
		

//intercettazione form prima del submit per validazione
formCliente.addEventListener('submit', validation)
		
//funzione che aggiunge validazione se azienda o privato prima di inoltrare il submit del form
function validation(e){
	e.preventDefault()
	let hasPrivatoErrors = false;
	let hasAziendaErrors = false;
	let hasContattiErrors = false;
	
	//anticipazione validazione label cliente
	if(labelCliente.value.trim().length === 0){
		labelClienteError.innerHTML = "Inserimento nome di etichetta obbligatorio";
		labelCliente.classList.add('border');
	}else {
		labelClienteError.innerHTML = "";
		labelCliente.classList.remove('border');
	}
	
	//anticipazione validazione email
	if(email.value.trim().length === 0 ){
		emailError.innerHTML = " Inserimento email obbligatorio";
		email.classList.add('border');
		hasContattiErrors = true
	}else{
		emailError.innerHTML = "";
		email.classList.remove('border');
	}
	
	//anticipazione validazione telefono
	if(telefono.value.trim().length === 0){
		telefonoError.innerHTML = "Inserimento numero telefono obbligatorio";
		telefono.classList.add('border');
		hasContattiErrors = true;
	}else{
		telefonoError.innerHTML = "";
		telefono.classList.remove('border');
	}
	
	//validazione se tipologia == privato
	if(tipologiaEdit.value == "privato"){
		//trim.() toglie gli spazi vuoti dalla stringa
		if(nome.value.trim().length === 0){		
			nomeError.innerHTML = "Inserimento obbligatorio nome";
			nome.classList.add('border', 'border-red-600');
			hasPrivatoErrors = true;
		} else {
			nomeError.innerHTML = "";
			nome.classList.remove('border', 'border-red-600');
			hasPrivatoErrors = false;
		}
				
		if(cognome.value.trim().length === 0){
			cognomeError.innerHTML = "Inserimento obbligatorio cognome";
			cognome.classList.add('border', 'border-red-600');
			hasPrivatoErrors = true;
		} else{
			cognomeError.innerHTML = "";
			cognome.classList.remove('border', 'border-red-600');
			hasPrivatoErrors = false;
		}		
		
		//validazione se tipologia == azienda
	} else if(tipologiaEdit.value == "azienda"){
		
		//trim.() toglie gli spazi vuoti dalla stringa
		if(ragioneSociale.value.trim().length === 0){
			ragioneSocialeError.innerHTML = "Inserimento obbligatorio ragione sociale";
			ragioneSociale.classList.add('border', 'border-red-600');
			hasAziendaErrors = true;
		} else {
			ragioneSocialeError.innerHTML = "";
			ragioneSociale.classList.remove('border', 'border-red-600');
			hasAziendaErrors = false;
		}
		
		if(partitaIva.value.trim().length === 0){
			partitaIvaError.innerHTML = "Inserimento obbligatorio partita iva";
			partitaIva.classList.add('border', 'border-red-600');
			hasAziendaErrors = true;
		} else{
			partitaIvaError.innerHTML = "";
			partitaIva.classList.remove('border', 'border-red-600');
			hasAziendaErrors = false;
		}
	}
	
	//verifico se il form non ha errori il submit prosegue
	if(hasPrivatoErrors == false && hasAziendaErrors == false ){
		formCliente.submit();
	} else {
		toggleAccordion(2);
	}
	
	if(hasContattiErrors == true){
		toggleAccordion(3);
	}
}

//funzione per accordion
function toggleAccordion(index) {
    const content = document.querySelector(`#content-${index}`);
    const line = document.querySelector(`.line-${index}`);
      line.classList.toggle('hidden');
      content.classList.toggle('hidden');
    }
