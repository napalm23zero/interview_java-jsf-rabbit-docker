const wsUri = `ws://${window.location.host}/jsf-rabbitmq-consumer/websocket/messages`;
console.log('WebSocket URI:', wsUri);
let websocket;

function connect() {
    console.log('Attempting to connect to WebSocket...');
    websocket = new WebSocket(wsUri);
    
    websocket.onopen = function() {
        console.log('🔌 WebSocket Connected');
        updateStatus('Connected', 'success');
    };
    
    websocket.onmessage = function(evt) {
        console.log('📨 Message received:', evt.data);
        try {
            const message = JSON.parse(evt.data);
            appendMessage(message);
        } catch (error) {
            console.error('Error parsing message:', error);
        }
    };
    
    websocket.onerror = function(error) {
        console.error('💥 WebSocket Error:', error);
        updateStatus('Connection Error', 'error');
    };
    
    websocket.onclose = function(event) {
        console.log('🔌 WebSocket Disconnected. Code:', event.code, 'Reason:', event.reason);
        updateStatus('Disconnected - Reconnecting...', 'warning');
        setTimeout(connect, 5000);
    };
}

function appendMessage(message) {
    const messagesDiv = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.className = 'message';
    
    const formattedTimestamp = new Date(message.timestamp).toLocaleString();
    
    messageElement.innerHTML = `
        <div class="message-content">${message.content}</div>
        <div class="message-timestamp">${formattedTimestamp}</div>
    `;
    
    messagesDiv.insertBefore(messageElement, messagesDiv.firstChild);
    
    // Limit number of displayed messages
    while (messagesDiv.children.length > 100) {
        messagesDiv.removeChild(messagesDiv.lastChild);
    }
}

function updateStatus(status, type) {
    const statusDiv = document.getElementById('connection-status');
    if (statusDiv) {
        statusDiv.textContent = status;
        statusDiv.className = `status ${type}`;
    }
}

// Initialize connection when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    connect();
});

// Attempt to reconnect if page visibility changes
document.addEventListener('visibilitychange', function() {
    if (document.visibilityState === 'visible' && 
        (!websocket || websocket.readyState === WebSocket.CLOSED)) {
        connect();
    }
});