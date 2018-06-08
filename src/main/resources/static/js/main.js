'use strict'

let usernamePage = document.querySelector('#username-page')
let chatPage = document.querySelector('#chat-page')
let usernameForm = document.querySelector('#usernameForm')
let messageForm = document.querySelector('#messageForm')
let messageInput = document.querySelector('#message')
let messageArea = document.querySelector('#messageArea')
let connectingElement = document.querySelector('.connecting')

let stompClient = null
let username = null

let colors = colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#name').value.trim()
    if (username) {
        usernamePage.classList.add('hidden')
        chatPage.classList.remove('hidden')
        let socket = new SockJS('ws')
        stompClient = Stomp.over(socket)
        stompClient.connect({}, onConnected, onError)
    }
    event.preventDefault()
}

function onConnected() {
    stompClient.subscribe('topic/public', onMessageReceived)
    stompClient.send('/app/chat.addUser',
        {},
        JSON.stringify({
            sender: username,
            type: 'JOIN'
        })
    )
    connectingElement.classList.add('hidden')
}

function onError() {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!'
    connectingElement.style.color = 'red'
}

function sendMessage(event) {
    let messageContent = messageInput.value
    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT'
        }
        stompClient.send('/app/chat.sendMessage', {}, JSON.stringify(chatMessage))
        messageInput.value = ''
    }
    event.preventDefault()
}

function onMessageReceived(payload) {
    let message = JSON.parse(payload.body)

}