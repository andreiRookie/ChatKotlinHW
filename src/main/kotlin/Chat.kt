import kotlin.random.Random

data class Chat(
    val chatId: Int = 1,
    val userId: Int = Random.nextInt(0, 3000)    //var userId: Int = user.userId

) {
    val chatTitle: String = "Chat(id: $chatId) with User(userId: $userId)"
    var messages: MutableList<Message> = mutableListOf()

    override fun toString(): String {
        return "$chatTitle"
    }
}
