// chat-widget.js
document.addEventListener('DOMContentLoaded', function() {
    var widget = document.getElementById('chatWidget');
    var button = document.getElementById('openChat');

    // Apri/chiudi tramite icona (sempre toggla!)
    button.addEventListener('click', function(event) {
        event.stopPropagation();
        if(widget.style.display === 'block') {
            closeWidget();
        } else {
            widget.style.display = 'block';
        }
    });

    // Chiudi se clicchi fuori
    document.addEventListener('click', function(event) {
        if (widget.style.display === 'block' &&
            !widget.contains(event.target) &&
            event.target !== button) {
            closeWidget();
        }
    });

	document.querySelectorAll('input[name="chatType"]').forEach(function(radio) {
	    radio.addEventListener('change', function() {
	        let selected = this.value;
	        document.getElementById('chatStep1').style.display = 'none';
	        if (selected === 'message') {
	            document.getElementById('chatStepMessage').style.display = 'block';
	        } else {
	            document.getElementById('chatStepFeedback').style.display = 'block';
	        }
	        
	        // Nascondi la scritta "Il Team ti ascolta!" negli step successivi
	        document.querySelector('.text-bar').style.display = 'none';
	    });
	});


    // Invio messaggio
    document.getElementById('sendMessageBtn').addEventListener('click', function(e) {
        e.preventDefault();
        
        // Validazione
        if (!validateTextarea('messageText')) {
            return false;
        }
        
        let text = document.getElementById('messageText').value;
        fetch('/api/support/message', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({message:text})
        }).then(() => {
            document.getElementById('chatStepMessage').style.display = 'none';
            document.getElementById('chatConfirmationMessage').style.display = 'block';
        });
    });

    // Invio feedback
    document.getElementById('sendFeedbackBtn').addEventListener('click', function(e) {
        e.preventDefault();
        
        // Validazione
        if (!validateTextarea('feedbackText')) {
            return false;
        }
        
        let text = document.getElementById('feedbackText').value;
        fetch('/api/support/feedback', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({feedback:text})
        }).then(() => {
            document.getElementById('chatStepFeedback').style.display = 'none';
            document.getElementById('chatConfirmationFeedback').style.display = 'block';
        });
    });

    // Chiudi chat tramite pulsanti "Chiudi"
    document.querySelectorAll('.closeChatBtn').forEach(function(btn){
        btn.addEventListener('click', function() {
            closeWidget();
        });
    });
	
	// Nel file JavaScript della chat-widget
	function resetChatForm() {
	    document.getElementById('radioMessage').checked = false;
	    document.getElementById('radioFeedback').checked = false;
	    
	    // Reset anche dei textarea
	    document.getElementById('messageText').value = '';
	    document.getElementById('feedbackText').value = '';
	    
	    // Reset contatori caratteri
	    document.getElementById('messageCount').textContent = '0';
	    document.getElementById('feedbackCount').textContent = '0';
	    document.getElementById('messageCount').style.color = '#999';
	    document.getElementById('feedbackCount').style.color = '#999';
	    
	    
	    // Nascondi tutti gli step
	    document.getElementById('chatStepMessage').style.display = 'none';
	    document.getElementById('chatStepFeedback').style.display = 'none';
	    document.getElementById('chatConfirmationMessage').style.display = 'none';
	    document.getElementById('chatConfirmationFeedback').style.display = 'none';
	    
	    // Mostra solo il primo step
	    document.getElementById('chatStep1').style.display = 'block';
	    
	    // Mostra di nuovo la scritta "Il Team ti ascolta!"
	    document.querySelector('.text-bar').style.display = 'block';
	}

	// Chiama la funzione quando si apre la chat
	document.getElementById('openChat').addEventListener('click', function() {
	    resetChatForm();
	});

    // Funzione di reset/chiusura
    function closeWidget() {
        widget.style.display = 'none';
        document.getElementById('chatStep1').style.display = 'block';
        document.getElementById('chatStepMessage').style.display = 'none';
        document.getElementById('chatStepFeedback').style.display = 'none';
        document.getElementById('chatConfirmationMessage').style.display = 'none';
        document.getElementById('chatConfirmationFeedback').style.display = 'none';
        document.getElementById('messageText').value = '';
        document.getElementById('feedbackText').value = '';
        
        // Reset contatori
        document.getElementById('messageCount').textContent = '0';
        document.getElementById('feedbackCount').textContent = '0';
        document.getElementById('messageCount').style.color = '#999';
        document.getElementById('feedbackCount').style.color = '#999';
        
        // Mostra di nuovo la scritta "Il Team ti ascolta!"
        document.querySelector('.text-bar').style.display = 'block';
    }
});

// Validazione textarea
function validateTextarea(textareaId) {
    const textarea = document.getElementById(textareaId);
    const value = textarea.value.trim();
    
    if (value.length < 40) {
        textarea.style.borderColor = '#ff4444';
        return false;
    } else {
        textarea.style.borderColor = '#ccc';
        return true;
    }
}


// VALIDAZIONE FORM
// Funzione per aggiornare il contatore caratteri
function updateCharacterCounter(textareaId, counterId, minLength = 40) {
    const textarea = document.getElementById(textareaId);
    const counter = document.getElementById(counterId);
    const count = textarea.value.length;
    
    counter.textContent = count;
    
    // Cambia colore in base al raggiungimento del minimo
    if (count >= minLength) {
        counter.style.color = '#28a745'; // Verde
    } else {
        counter.style.color = '#999'; // Grigio
    }
}

// Event listener per validazione in tempo reale
document.getElementById('messageText').addEventListener('input', function() {
    validateTextarea('messageText');
    updateCharacterCounter('messageText', 'messageCount', 40);
});

document.getElementById('feedbackText').addEventListener('input', function() {
    validateTextarea('feedbackText');
    updateCharacterCounter('feedbackText', 'feedbackCount', 40);
});
