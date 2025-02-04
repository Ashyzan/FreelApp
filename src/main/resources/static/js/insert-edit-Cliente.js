// recupero elementi dal DOM per errore dimensione input
const errorMaxFileSizeContainer = document.getElementById('errorMaxFileSize');
let errorMaxFileSize;

  
// recupero elementi dal DOM per manipolazione input fatturazione per tipologia
const aziendaInput = document.getElementById('aziendaInput');
const privatoInput = document.getElementById('privatoInput');
const contattiAzienda = document.getElementById('contattiAzienda');
const tipologiaEdit = document.getElementById('tipologia');

//funzione per accordion
function toggleAccordion(index) {
    const content = document.querySelector(`#content-${index}`);
    const line = document.querySelector(`.line-${index}`);
      line.classList.toggle('hidden');
      content.classList.toggle('hidden');
    }
	
//gestione visualizzazione campi se tipologia == azienda in caso di modifica
if(tipologiaEdit.value == "azienda"){
	aziendaInput.classList.remove('hidden');
	contattiAzienda.classList.remove('hidden');
	privatoInput.classList.add('hidden');
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