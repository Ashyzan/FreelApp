
//recupero elementi dal DOM
const contenutoFormStatistiche = document.getElementById('contenuto-form-statistiche');
const selectionTariffaOraria = document.getElementById('selectionTariffaOraria');
const budgetInserito = document.getElementById('budget-input');

let valueTemporaneoDaCancellare = "left-[0%]";
let tariffaOrariaInserita = 0;
let tettoMaxOreInserito = 0;
let risultatoBudget = 0;
let budgetInseritoValore = 0;

	



//funzioni
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
					<input type="number" name="valore-budget" class="h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"
						step="0.01" min="0" value="0.00" id="budget-input" oninput="eventLIstenerBudget(value)">
						<span> €</span>
				</div>
			</div>
			<div class="grid md:grid-cols-3 grid-cols-4 mb-2 py-3">
				<div class="col">
					<label for="valore-tariffa-oraria">Tariffa oraria</label>
				</div>
				<div class="col relative md:col-span-2 col-span-3">
					<input type="range" name="valore-tariffa-oraria" class="w-full accent-[#FFE641]" id="selectionTariffaOraria"
						value="0" min="0" max="100" oninput="eventListenerTariffaOraria(value)">
					<span id="input-range-value"
						class="absolute left-[0%] p-[3px] mt-[-32px] ms-[-20px] bg-white border-2 border-[#0057A5] rounded-lg text-[12px]">0€</span>
				</div>
			</div>
			<div class="text-center p-3">Per questo progetto sono diponibili: <strong><span id="risultato-ore-da-budget">--
				</span></strong> ore e <strong><span id="risultato-minuti-da-budget">--</span></strong> minuti.
			</div>`;
										
										 



											

}

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

function eventListenerTariffaOraria(value){
	const risultatoOreDaBudget = document.getElementById('risultato-ore-da-budget');
	const risultatoMinutiDaBudget = document.getElementById('risultato-minuti-da-budget');
	const inputRangeValue = document.getElementById('input-range-value');
	tariffaOrariaInserita = Number(value);
	inputRangeValue.classList.remove(valueTemporaneoDaCancellare);
	inputRangeValue.classList.add(`left-[${tariffaOrariaInserita}%]`);
	valueTemporaneoDaCancellare = "left-["+ tariffaOrariaInserita +"%]";
	inputRangeValue.innerHTML = tariffaOrariaInserita + "€";
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
			<div class="grid md:grid-cols-3 grid-cols-4 mb-4 py-3">
				<div class="col">
					<label for="valore-tariffa-oraria">Tariffa oraria</label>
				</div>
				<div class="col relative md:col-span-2 col-span-3">
					<input type="range" name="valore-tariffa-oraria" class="w-full accent-[#FFE641]" id="selectionTariffaOraria"
						value="0" min="0" max="100" oninput="selezioneTariffaOraria(value)">
						<span id="input-range-value"
							class="absolute left-[0%] p-[3px] mt-[-32px] ms-[-20px] bg-white border-2 border-[#0057A5] rounded-lg text-[12px]">0€</span>
				</div>
			</div>
			<div class="text-center p-3">Per questo progetto sarà impiegata una tariffa oraria di €: <strong>
					<span id="tariffa-oraria-selezionata-per-progetto">00</span></strong>
			</div>`;
}

function selezioneTariffaOraria(value){
	const inputRangeValue = document.getElementById('input-range-value');
	const tariffaOrariaSelezionataPerProgetto = document.getElementById('tariffa-oraria-selezionata-per-progetto');
	tariffaOrariaInserita = Number(value);
	inputRangeValue.classList.remove(valueTemporaneoDaCancellare);
	inputRangeValue.classList.add(`left-[${tariffaOrariaInserita}%]`);
	valueTemporaneoDaCancellare = "left-["+ tariffaOrariaInserita +"%]";
	inputRangeValue.innerHTML = tariffaOrariaInserita + "€";
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
						<input type="number" name="valore-budget" class="h-8  text-[#0057A5] rounded-lg bg-gray-100 ps-4"
							step="1" min="0" value="00" id="limite-ore-input" oninput="eventLIstenerInputOreMax(value)">
								<span> €</span>
					</div>
				</div>
				<div class="grid md:grid-cols-3 grid-cols-4 mb-4 py-3">
					<div class="col">
						<label for="valore-tariffa-oraria">Tariffa oraria</label>
					</div>
					<div class="col relative md:col-span-2 col-span-3">
						<input type="range" name="valore-tariffa-oraria" class="w-full accent-[#FFE641]" id="selectionTariffaOraria"
							value="0" min="0" max="100" oninput="selezioneTariffaOrariaMaxOre(value)">
							<span id="input-range-value"
								class="absolute left-[0%] p-[3px] mt-[-32px] ms-[-20px] bg-white border-2 border-[#0057A5] rounded-lg text-[12px]">0€</span>
					</div>
				</div>
				<div class="text-center p-3">Per questo progetto il guadagno massimo sarà di €: <strong>
						<span id="guadagno-max-per-progetto">00</span></strong>
				</div>`;
	
}

function eventLIstenerInputOreMax(value){
	const guadagnoMaxPerProgetto = document.getElementById('guadagno-max-per-progetto');
	tettoMaxOreInserito = Number(value);
	guadagnoMaxPerProgetto.innerHTML = tettoMaxOreInserito * tariffaOrariaInserita;
	
	
}

function selezioneTariffaOrariaMaxOre(value){
	const guadagnoMaxPerProgetto = document.getElementById('guadagno-max-per-progetto');
	const inputRangeValue = document.getElementById('input-range-value');
	tariffaOrariaInserita = Number(value);
	inputRangeValue.classList.remove(valueTemporaneoDaCancellare);
	inputRangeValue.classList.add(`left-[${tariffaOrariaInserita}%]`);
	valueTemporaneoDaCancellare = "left-["+ tariffaOrariaInserita +"%]";
	inputRangeValue.innerHTML = tariffaOrariaInserita + "€";
	guadagnoMaxPerProgetto.innerHTML = tettoMaxOreInserito * tariffaOrariaInserita;
}