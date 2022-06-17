import kotlin.random.Random

data class Message(
    val messageId: Int = Random.nextInt(0, 1000),
    val userId: Int = Random.nextInt(2000, 3000),

    val senderId: Int = Random.nextInt(2000, 3000),

    val chatId: Int,
    val text: String = "message (id: $messageId) text",
    var isRead: Boolean = false
    )