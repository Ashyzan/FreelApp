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

    // Bottone avanti dal primo step
    document.getElementById('nextBtn').addEventListener('click', function() {
        let selected = document.querySelector('input[name="chatType"]:checked').value;
        document.getElementById('chatStep1').style.display = 'none';
        if (selected === 'message') {
            document.getElementById('chatStepMessage').style.display = 'block';
        } else {
            document.getElementById('chatStepFeedback').style.display = 'block';
        }
    });

    // Invio messaggio
    document.getElementById('sendMessageBtn').addEventListener('click', function() {
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
    document.getElementById('sendFeedbackBtn').addEventListener('click', function() {
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
    }
});
