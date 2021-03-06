

object ChatService {

    private var chatList: MutableList<Chat> = mutableListOf()

    fun createChat(userId: Int, message: String): Chat {

        var newChat = if (chatList.isNotEmpty()) Chat(chatId = chatList.last().chatId + 1 ,userId = userId) else Chat(userId = userId)

        val msg = Message(userId = userId, chatId = newChat.chatId, text = message)
        newChat.messages += msg
        chatList += newChat
        return newChat
    }

    fun createMessage(userId: Int, chatId: Int, text: String): Message {

        val newMessage = Message(userId = userId, chatId = chatId, text = text)

        getChat(chatId)?.messages?.add(newMessage)
        return newMessage
    }


    fun deleteChat(chatId: Int): Chat? {
        val chatToDelete = getChat(chatId)
        chatList.remove(getChat(chatId))
        return chatToDelete
    }

    fun deleteMessage(chatId: Int, messageId: Int): Message {
        val messages = getChat(chatId)?.messages
        val messageToDelete = getMessage(chatId, messageId)

        messages?.remove(getMessage(chatId, messageId))

        if (messages?.isEmpty() == true) deleteChat(chatId)
        return messageToDelete
    }

    fun getMessage(chatId: Int, messageId: Int): Message {
        return getChat(chatId)?.messages?.find {message: Message -> message.messageId == messageId}
            ?: throw NoMessageFoundException("no message with id: $messageId in chat $chatId")
    }

    fun getChat(chatId: Int): Chat? {
        val result = chatList.find {  chat: Chat -> chat.chatId == chatId }
            ?: throw NoChatFoundException("no chat with id: $chatId")
        return result
    }

    fun readMessage(message: Message) {
        getMessage(message.chatId, message.messageId).isRead = true
    }

    fun getUnreadChatsCount(): Int {
        val predicate = fun(msg: Message) = msg.isRead == false

        return chatList.count {  chat: Chat -> chat.messages.any(predicate) }

    }

    fun getChatMessages(chatId: Int, lastMessageId: Int, messageCount: Int): MutableList<Message> {
        val messages = getChat(chatId)?.messages ?: mutableListOf()

        val lastMessage = getMessage(chatId, lastMessageId)
        val indexOfLastMessage = messages.indexOf(lastMessage)

        messages.forEach { message: Message -> readMessage(message) }

        return if ((indexOfLastMessage + messageCount) > messages.size) messages.subList(indexOfLastMessage, messages.size)
        else messages.subList(indexOfLastMessage, indexOfLastMessage + messageCount)

    }


    fun getChatList(): MutableList<Chat> {
        return chatList
    }

    fun getChats(userId: Int): List<Chat> {
        return chatList.filter {chat: Chat -> chat.userId == userId}
            .filter { chat: Chat -> chat.messages.any() }
    }

    fun makeChatListEmptyAgain() {
        chatList.clear()
    }



}



