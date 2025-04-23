
//recupero elementi dal DOM per rimepimento input TIPOLOGIA
const contenutoFormStatistiche = document.getElementById('contenuto-form-statistiche');
const selectionTariffaOraria = document.getElementById('selectionTariffaOraria');
const budgetInserito = document.getElementById('budget-input');

//recupero elementi dal DOM per validazione tipologia
const inputSelectTipologia = document.getElementById('tipologia-select');
const inputSelectTipologiaError = document.getElementById('inputSelectTipologiaError');

//recupero elementi dal DOM per validazione form
const formProgetto = document.getElementById('formProgetto');

//recupero elementi dal DOM per ottimizzazione layout validazione dati 
//(anticipazione errori backend per armonia layout)
const nomeProgetto = document.getElementById('name');
const nomeProgettoError = document.getElementById('nomeProgettoError');
const descrizioneProgetto = document.getElementById('textarea-cinquecento');
const descrizioneProgettoError = document.getElementById('textarea-cinquecento-error');
const clienteProgetto = document.getElementById('cliente');
const clienteProgettoError = document.getElementById('clienteProgettoError');



let tariffaOrariaInserita = 0;
let tettoMaxOreInserito = 0;
let risultatoBudget = 0;
let budgetInseritoValore = 0;



//funzioni per intercettare il form progetto e validare la tipologia
formProgetto.addEventListener('submit', validation);

function validation(e){
	e.preventDefault()
	let hasProgettoErrors = false;
	let hasBudgetErrors = false;
	let hasLimiteTempoErrors = false;
	let hasTariffaOrariaErrors = false;
	
	//anticipazione validazione nomeProgetto
		if(nomeProgetto.value.trim().length === 0){
			nomeProgettoError.innerHTML = "Inserimento nome progetto obbligatorio";
			nomeProgetto.classList.add('border');
			hasProgettoErrors = true;
		}else {
			nomeProgettoError.innerHTML = "";
			nomeProgetto.classList.remove('border');
			hasProgettoErrors = false;
		}
		
	//anticipazione validazione descrizioneProgetto
		if(descrizioneProgetto.value.trim().length === 0){
			descrizioneProgettoError.innerHTML = "Inserimento descrizione obbligatorio";
			descrizioneProgetto.style.height = "100px";
			descrizioneProgetto.classList.add('border');
			hasProgettoErrors = true;
		}else {
			descrizioneProgettoError.innerHTML = "";
			descrizioneProgetto.classList.remove('border');
			hasProgettoErrors = false;
		}
	
	//anticipazione validazione clienteProgetto
		if(clienteProgetto.value === ""){
			clienteProgettoError.innerHTML = "Selezione cliente obbligatoria";
			clienteProgetto.classList.add('border');
			hasProgettoErrors = true;
		}else {
			clienteProgettoError.innerHTML = "";
			clienteProgetto.classList.remove('border');
			hasProgettoErrors = false;
		}
	
	//validazione selezione tipologia
	if(inputSelectTipologia.value === ""){
		inputSelectTipologiaError.innerHTML = "Selezione tipologia obbligatoria"
		inputSelectTipologia.classList.add('border');
		hasProgettoErrors = true;
	} else{
		inputSelectTipologiaError.innerHTML = "";
		inputSelectTipologia.classList.remove('border');
		hasProgettoErrors = false;
	}
	
	//validazione tipologia budget
	if(inputSelectTipologia.value == "budget"){
		const budgetInput = document.getElementById('budget-input');
		const inputBudgetError = document.getElementById('inputBudgetError');
		const selectionTariffaOraria = document.getElementById('selectionTariffaOraria');
		const inputTariffaOrariaError = document.getElementById('inputTariffaOrariaError');
		if(budgetInput.value == 0.00 ){
			budgetInput.classList.add('border');
			inputBudgetError.innerHTML = "Inserimento budget obbligatorio"
			hasBudgetErrors = true;
		}else{
			budgetInput.classList.remove('border');
			inputBudgetError.innerHTML = ""
			hasBudgetErrors = false;
		}
		if(selectionTariffaOraria.value == 0){
			//selectionTariffaOraria.classList.remove('accent-[#FFE641]');
			selectionTariffaOraria.classList.add('border');
			inputTariffaOrariaError.innerHTML = "Scegliere la tariffa oraria";
			hasBudgetErrors = true;
		}else{
			inputTariffaOrariaError.innerHTML = "";
			selectionTariffaOraria.classList.remove('border');
			//selectionTariffaOraria.classList.add('accent-[#FFE641]');
			hasBudgetErrors = false;
		}
	}
	
	//validazione tipologia limite-tempo
		if(inputSelectTipologia.value == "limite-tempo"){
			const limiteOreInput = document.getElementById('limite-ore-input');
			const limiteOreInputError = document.getElementById('limiteOreInputError');
			const selectionTariffaOraria = document.getElementById('selectionTariffaOraria');
			const inputTariffaOrariaError = document.getElementById('inputTariffaOrariaError');
			if(limiteOreInput.value == 0 ){
				limiteOreInput.classList.add('border');
				limiteOreInputError.innerHTML = "Inserimento ore obbligatorio"
				hasLimiteTempoErrors = true;
			}else{
				limiteOreInput.classList.remove('border');
				limiteOreInputError.innerHTML = ""
				hasLimiteTempoErrors = false;
			}
			if(selectionTariffaOraria.value == 0){
				//selectionTariffaOraria.classList.remove('accent-[#FFE641]');
				selectionTariffaOraria.classList.add('border');
				inputTariffaOrariaError.innerHTML = "Scegliere la tariffa oraria";
				hasLimiteTempoErrors = true;
			}else{
				inputTariffaOrariaError.innerHTML = "";
				selectionTariffaOraria.classList.remove('border');
				//selectionTariffaOraria.classList.add('accent-[#FFE641]');
				hasLimiteTempoErrors = false;
			}
		}
		
	//validazione tipologia tariffa oraria
		if(inputSelectTipologia.value == "tariffa-oraria"){
			const selectionTariffaOraria = document.getElementById('selectionTariffaOraria');
			const inputTariffaOrariaError = document.getElementById('inputTariffaOrariaError');
			if(selectionTariffaOraria.value == 0){
				//selectionTariffaOraria.classList.remove('accent-[#FFE641]');
				selectionTariffaOraria.classList.add('border');
				inputTariffaOrariaError.innerHTML = "Scegliere la tariffa oraria";
				hasLimiteTempoErrors = true;
			}else{
				inputTariffaOrariaError.innerHTML = "";
				selectionTariffaOraria.classList.remove('border');
				//selectionTariffaOraria.classList.add('accent-[#FFE641]');
				hasLimiteTempoErrors = false;
				}
			}
	//verifico se il form non ha errori il submit prosegue
		if(hasBudgetErrors == false && hasLimiteTempoErrors == false && 
				hasTariffaOrariaErrors == false && hasProgettoErrors == false){
		formProgetto.submit();
		}
}	 


//funzione che gestisce il select della tipologia
function tipologiaStatisticaSelect(value){
	switch(value){
		case "budget":
			creaContenutoBudget();
			tariffaOrariaInserita = 0;
			tettoMaxOreInserito = 0;
			break;
		
		case "limite-tempo":
			creaContenutoLimiteTempo();
			tariffaOrariaInserita = 0;
			tettoMaxOreInserito = 0;
			break;
			
		case "tariffa-oraria":
			creaContenutoTariffaOraria();
			tariffaOrariaInserita = 0;
			tettoMaxOreInserito = 0;
			break;
			
		default:
			contenutoFormStatistiche.innerHTML = "";
	}
}

//funzioni creazione contenuto statistiche per BUDGET
function creaContenutoBudget(){
	contenutoFormStatistiche.innerHTML = `<hr>
		<div class="text-center font-bold py-2">Inserire budget e tariffa oraria :</div>
			<div class="grid md:grid-cols-3 grid-cols-4 mb-4 mt-2 py-2">
					<div class="col">
						<label for="valore-budget">Budget</label>
					</div>
				<div class="col md:col-span-2 col-span-3">
					<input type="number" name="budgetMonetario" class="border-red-600 h-8 h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"
						th:field="*{budgetMonetario}" step="0.01" min="0" value="${budgetMonetario}" id="budget-input" oninput="eventLIstenerBudget(value)">
						<span> €</span>
				<div id="inputBudgetError" class="text-red-600 text-[12px]  w-full"></div>	
				</div>

			</div>
			<div class="grid md:grid-cols-3 grid-cols-4 mb-2 py-2">
				<div class="col">
					<label for="valore-tariffa-oraria">Tariffa oraria</label>
				</div>
				<div class="col relative md:col-span-2 col-span-3">
				<input type="number" name="tariffaOraria" class="border-red-600 h-8 h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"	
					 id="selectionTariffaOraria"
						th:field="*{tariffaOraria}" value="${tariffaOraria}" min="0" step="0.01"  oninput="eventListenerTariffaOraria(value)">
						<span> €</span>
					<div id="inputTariffaOrariaError" class="text-red-600 text-[12px] w-full"></div>
				</div>
			</div>
			<div class="text-center p-3">Per questo progetto sono diponibili: <strong><span id="risultato-ore-da-budget">--
				</span></strong> ore e <strong><span id="risultato-minuti-da-budget">--</span></strong> minuti.
			</div>`;
}

//funzione che calcola le ore disponibile in tipologia BUDGET MONETARIO	al variare del budget
function eventLIstenerBudget(value){
	const risultatoOreDaBudget = document.getElementById('risultato-ore-da-budget');
	const risultatoMinutiDaBudget = document.getElementById('risultato-minuti-da-budget');
	budgetInseritoValore = Number(value);
	if(tariffaOrariaInserita != 0){
		risultatoBudget = budgetInseritoValore / tariffaOrariaInserita;
		risultatoOreDaBudget.innerHTML = parseInt(risultatoBudget);
		risultatoMinutiDaBudget.innerHTML = parseInt((risultatoBudget-parseInt(risultatoBudget))*60); 		
	} else{
		risultatoOreDaBudget.innerHTML = "--";
		risultatoMinutiDaBudget.innerHTML = "--";
	}
};	
//funzione che calcola le ore disponibile in tipologia BUDGET MONETARIO	al variare della tariffa oraria
function eventListenerTariffaOraria(value){
	const risultatoOreDaBudget = document.getElementById('risultato-ore-da-budget');
	const risultatoMinutiDaBudget = document.getElementById('risultato-minuti-da-budget');
	const inputRangeValue = document.getElementById('input-range-value');
	tariffaOrariaInserita = Number(value);
	if(tariffaOrariaInserita != 0){
		risultatoBudget = budgetInseritoValore / tariffaOrariaInserita;
		risultatoOreDaBudget.innerHTML = parseInt(risultatoBudget);
		risultatoMinutiDaBudget.innerHTML = parseInt((risultatoBudget-parseInt(risultatoBudget))*60); 		
	}else{
		risultatoOreDaBudget.innerHTML = "--";
		risultatoMinutiDaBudget.innerHTML = "--";
	}
}	


//funzioni creazione contenuto statistiche per TARIFFA-ORARIA
function creaContenutoTariffaOraria(){
	contenutoFormStatistiche.innerHTML = `<hr>
			<div class="text-center font-bold py-2 mb-3">Inserire la tariffa oraria :</div>
			<div class="grid md:grid-cols-3 grid-cols-4 mb-2 py-2">
					<div class="col">
						<label for="valore-tariffa-oraria">Tariffa oraria</label>
					</div>
					<div class="col relative md:col-span-2 col-span-3">
					<input type="number" name="tariffaOraria" class="border-red-600 h-8 h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"	
						 id="selectionTariffaOraria"
							th:field="*{tariffaOraria}" value="${tariffaOraria}" min="0" step="0.01"  oninput="eventListenerTariffaOraria(value)">
							<span> €</span>
						<div id="inputTariffaOrariaError" class="text-red-600 text-[12px] w-full"></div>
					</div>
			</div>
			<div class="text-center p-3">Per questo progetto sarà impiegata una tariffa oraria di €: <strong>
					<span id="tariffa-oraria-selezionata-per-progetto">00</span></strong>
			</div>`;
}
//funzione che stampa e aggiorna la tariffa oraria selezionata nella tipologia TARIFFA ORARIA
function selezioneTariffaOraria(value){
	const inputRangeValue = document.getElementById('input-range-value');
	const tariffaOrariaSelezionataPerProgetto = document.getElementById('tariffa-oraria-selezionata-per-progetto');
	tariffaOrariaInserita = Number(value);
	tariffaOrariaSelezionataPerProgetto.innerHTML = tariffaOrariaInserita;
}


//funzioni creazione contenuto statistiche per LIMITE-TEMPO
function creaContenutoLimiteTempo(){
	contenutoFormStatistiche.innerHTML = `<hr>
				<div class="text-center font-bold py-2 mb-3">Inserire limite massimo di ore progetto e tariffa oraria:</div>
				<div class="grid md:grid-cols-3 grid-cols-4 mb-4 mt-2 py-2">
					<div class="col">
						<label for="limite-ore-input">Ore</label>
					</div>
					<div class="col md:col-span-2 col-span-3">
						<input type="number" name="budgetOre" class="border-red-600 h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"
							th:field="*{budgetOre}" step="1" min="0" value="${budgetOre}" id="limite-ore-input" oninput="eventLIstenerInputOreMax(value)">
								<span> €</span>
						<div id="limiteOreInputError" class="text-red-600 text-[12px]  w-full"></div>	
					</div>
				</div>
				<div class="grid md:grid-cols-3 grid-cols-4 mb-2 py-2">
					<div class="col">
						<label for="valore-tariffa-oraria">Tariffa oraria</label>
					</div>
					<div class="col relative md:col-span-2 col-span-3">
						<input type="number" name="tariffaOraria" class="border-red-600 h-8 h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"	
							 id="selectionTariffaOraria"
							th:field="*{tariffaOraria}" value="${tariffaOraria}" min="0" step="0.01"  oninput="eventListenerTariffaOraria(value)">
							<span> €</span>
						<div id="inputTariffaOrariaError" class="text-red-600 text-[12px] w-full"></div>
					</div>
				</div>
				<div class="text-center p-3">Per questo progetto il guadagno massimo sarà di €: <strong>
						<span id="guadagno-max-per-progetto">00</span></strong>
				</div>`;
	
}
//funzione che genera il guadagno massimo del progetto in tipologia LIMITE-TEMPO al cambiare del tetto massimo di ore
function eventLIstenerInputOreMax(value){
	const guadagnoMaxPerProgetto = document.getElementById('guadagno-max-per-progetto');
	tettoMaxOreInserito = Number(value);
	guadagnoMaxPerProgetto.innerHTML = tettoMaxOreInserito * tariffaOrariaInserita;
	
	
}
//funzione che genera il guadagno massimo del progetto in tipologia LIMITE-TEMPO al cambiare della tariffa oraria
function selezioneTariffaOrariaMaxOre(value){
	const guadagnoMaxPerProgetto = document.getElementById('guadagno-max-per-progetto');
	const inputRangeValue = document.getElementById('input-range-value');
	tariffaOrariaInserita = Number(value);
	guadagnoMaxPerProgetto.innerHTML = tettoMaxOreInserito * tariffaOrariaInserita;
}

//funzione che mostra al caricamento pagina di edi progetto i campi del form relativi alla tipologia salvata a db
function showTipologia(){
	if(inputSelectTipologia.value == "budget"){
		creaContenutoBudget();
	}
	
	if(inputSelectTipologia.value == "limite-tempo"){
		creaContenutoLimiteTempo();
	}
	
	if(inputSelectTipologia.value == "tariffa-oraria"){
		creaContenutoTariffaOraria();
	}
}