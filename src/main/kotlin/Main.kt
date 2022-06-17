
    fun main() {
        println("hello!")
        val testChat = ChatService.createChat(userId = 111, message = "TEST!111")
        val testChat2 = ChatService.createChat(userId = 111, message = "TEST!22222")
        val testChat3 = ChatService.createChat(userId = 222, message = "TEST!333")

        val testMsg = ChatService.createMessage(userId = 111, chatId = testChat.chatId, text = "TEST MSG")
        val testMsg2 = ChatService.createMessage(userId = 111, chatId = testChat.chatId, text = "TEST MSG 2")

//        println(ChatService.getChatList())
//
        println("Message id(${testMsg2.messageId}) from chat(${testChat.chatId}): ${ChatService.getMessage(testChat.chatId,testMsg2.messageId)}")
//
//        println(ChatService.getChat(testChat.chatId))
//
//        //println(ChatService.deleteChat(testChat.chatId))
//
        println(ChatService.getChatList())
        ChatService.readMessage(testMsg)
        ChatService.readMessage(testMsg2)
        ChatService.readMessage(testChat.messages[0])
//        println(testMsg)
//        println(testChat.messages)

        println(ChatService.getUnreadChatsCount())
        println(ChatService.getChats(111))

        println(ChatService.getChatMessages(ChatService.getChatList()[0].chatId,ChatService.getChatList()[0].messages[0].messageId, 5))
        println(ChatService.getChatMessages(ChatService.getChatList()[0].chatId,testMsg.messageId, 5))
    }
