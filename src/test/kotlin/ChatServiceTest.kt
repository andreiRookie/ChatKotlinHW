import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ChatServiceTest {

    @Test
    fun createChat_test() {
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val actualResult = testChat.chatId != 0

        assertTrue(actualResult)
    }

    @Test
    fun createMessage_test(){
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val expectedMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act
        val actualResult = testChat.messages[1] === expectedMessage    // == or === ??

        //assert

        assertTrue(actualResult)
    }

    @Test(expected = NoChatFoundException::class)
    fun createMessage_shouldThrowNoChat(){
        ChatService.makeChatListEmptyAgain()

        val testMessage = ChatService.createMessage(userId = 123, 1, "test msg")
    }

    @Test
    fun deleteChat_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")

        //act
        val deletedChat = ChatService.deleteChat(testChat.chatId)

        //assert
        assertEquals(testChat, deletedChat)
    }

    @Test(expected = NoChatFoundException::class)
    fun deleteChat_shouldThrowNoChat() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")

        //act
        val deletedChat = ChatService.deleteChat(testChat.chatId +1)
    }

    @Test
    fun deleteMessage_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act

        val deletedMsg = ChatService.deleteMessage(testChat.chatId, testMessage.messageId)

        //assert
        assertEquals(testMessage, deletedMsg)
    }

    @Test(expected = NoChatFoundException::class)
    fun deleteMessage_shouldThrowNoChatFound() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act

        val deletedMsg = ChatService.deleteMessage(testChat.chatId + 1, testMessage.messageId)

        //assert
        assertEquals(testMessage, deletedMsg)
    }

    @Test(expected = NoMessageFoundException::class)
    fun deleteMessage_shouldThrowNoMessageFound() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act

        val deletedMsg = ChatService.deleteMessage(testChat.chatId, testMessage.messageId - 1)

        //assert
        assertEquals(testMessage, deletedMsg)
    }

    @Test
    fun getMessage_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act
        val result = ChatService.getMessage(testChat.chatId,testMessage.messageId)

        //assert
        assertEquals(testMessage,result)
    }

    @Test(expected = NoChatFoundException::class)
    fun getMessage_shouldThrowNoChat() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act
        ChatService.getMessage(testChat.chatId + 1,testMessage.messageId)

    }

    @Test(expected = NoMessageFoundException::class)
    fun getMessage_shouldThrowNoMessage() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act
        ChatService.getMessage(testChat.chatId,testMessage.messageId + 1)

    }

    @Test
    fun getChat_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")

        //act
        val result = ChatService.getChat(testChat.chatId)

        //assert
        assertEquals(testChat,result)
    }

    @Test(expected = NoChatFoundException::class)
    fun getChat_shouldThrowNoChat() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")

        //act
        ChatService.getChat(testChat.chatId + 1)

    }

    @Test
    fun readMessage_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")

        //act
        ChatService.readMessage(testMessage)

        //assert
        assertTrue(testMessage.isRead)
    }

    @Test
    fun getUnreadChatsCount_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testChat2 = ChatService.createChat(userId = 123, message = "hello")
        val testChat3 = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")
        val testMessage2 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 2")
        ChatService.readMessage(testChat.messages[0])
        ChatService.readMessage(testMessage)
        ChatService.readMessage(testMessage2)

        val actualResult = ChatService.getUnreadChatsCount() == 2

        assertTrue(actualResult)
    }

    @Test
    fun getChatMessages_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")
        val testMessage2 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 2")
        val testMessage3 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 3")

        val expectedResult = mutableListOf<Message>(testMessage, testMessage2, testMessage3)
        //act
        val actualResult = ChatService.getChatMessages(testChat.chatId, testMessage.messageId, 7)

        //assert

        assertEquals(expectedResult, actualResult)

    }

    @Test(expected = NoChatFoundException::class)
    fun getChatMessages_shouldThrowNoChat() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")
        val testMessage2 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 2")
        val testMessage3 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 3")

        //act
        val actualResult = ChatService.getChatMessages(testChat.chatId + 1, testMessage.messageId, 7)


    }

    @Test(expected = NoMessageFoundException::class)
    fun getChatMessages_shouldThrowNoMessage() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "hello")
        val testMessage = ChatService.createMessage(userId = 123, testChat.chatId, "test msg")
        val testMessage2 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 2")
        val testMessage3 = ChatService.createMessage(userId = 123, testChat.chatId, "test msg 3")

        //act
        val actualResult = ChatService.getChatMessages(testChat.chatId, testMessage2.messageId + 1, 7)


    }

    @Test
    fun getChats_test() {
        //arrange
        ChatService.makeChatListEmptyAgain()

        val testChat = ChatService.createChat(userId = 123, message = "chat1")
        val testChat2 = ChatService.createChat(userId = 123, message = "chat2")
        val testChat3 = ChatService.createChat(userId = 122, message = "chat3")
        val testChat4 = ChatService.createChat(userId = 123, message = "chat4")

        ChatService.createMessage(userId = 122, testChat.chatId, "test msg")
        ChatService.createMessage(userId = 123, testChat.chatId, "test msg 2")
        ChatService.createMessage(userId = 1233, testChat.chatId, "test msg 3")

        val expectedResult = listOf(testChat, testChat2, testChat4)

        //act
        val actualResult = ChatService.getChats(123)

        //assert
        assertEquals(expectedResult, actualResult)

    }
}