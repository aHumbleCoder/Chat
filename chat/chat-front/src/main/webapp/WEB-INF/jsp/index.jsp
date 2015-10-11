<html>
<head>
<title>Let's chat!</title>
<script src="/chat-front/resources/js/sockjs.min.js"></script>
<script src="/chat-front/resources/js/stomp.min.js"></script>

<script type="text/javascript">
	var socket = new SockJS("/chat-front/ws");
	var stompClient = Stomp.over(socket);

	function onConnect() {
		stompClient.subscribe('/channel/public', onMessage);
	};

	function onMessage(frame) {
		var msg = JSON.parse(frame.body).content;
		var $msgDiv = document.createElement("div");
		$msgDiv.appendChild(document.createTextNode(msg));
		document.getElementById("msg-history").appendChild($msgDiv);
	}

	stompClient.connect("guest", "guest", onConnect);

	function sendMsg() {
		var message = document.getElementById("msg-input").value;
		stompClient.send("/app/sendMessage", {}, JSON.stringify({
			"channelId" : "public",
			"userName" : "",
			"content" : message
		}));

		document.getElementById("msg-input").value = "";
	}

	function onKeyup(event) {
		if (13 === event.keyCode) {
			sendMsg();
		}
	}
</script>
</head>
<body>
	<input id="msg-input" onkeyup="onKeyup(event);" />
	<button onclick="sendMsg();">Send</button>
	<div id="msg-history"></div>
</body>
</html>