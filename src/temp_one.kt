import kotlin.random.Random
import java.util.Scanner

//牌类
open class Poker {
    private val pokerSuit: Array<Char> = arrayOf('♦', '♣', '♥', '♠')
    private val pokerNumber: Array<String> = arrayOf("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A")
    private val standard = mutableListOf<String>()
    val library = mutableListOf<String>()

    init {
        for (number in pokerNumber)
            for (suit in pokerSuit) {
                library.add("$suit$number")
                standard.add("$suit$number")
            }
    }

    fun myGetValue(contrast: String): Int {
        var myTarget = 0
        for ((i, num) in standard.withIndex()) {
            if (num == contrast) {
                myTarget = i
                break
            }
        }
        return myTarget
    }
}

//手牌类
open class HandPoker(private val cardNumber: Int) : Poker() {
    val handPoker = mutableListOf<String>()

    init {
        for (i in 1..cardNumber) {
            handPoker.add(library.removeAt(Random.nextInt(library.size)))
        }
    }
}

//玩家类
class Player(cardNumber: Int) : HandPoker(cardNumber) {
    var score: Int = 0
    var card: String = ""
    private val input = Scanner(System.`in`)

    //展示玩家手牌
    fun display() {
        println("序号 手牌")
        for ((i, n) in this.handPoker.withIndex()) {
            println("${i + 1}  $n")
        }
    }

    //玩家出牌
    fun playerOutOfCard() {
        print("选择一张要出的牌:")
        var choices: Int = input.nextInt()
        card = this.handPoker.removeAt(choices - 1)
    }

    //电脑出牌
    fun computerOutOfCard() {
        card = this.handPoker.removeAt(Random.nextInt(this.handPoker.size))
    }
}

fun main() {
    val input = Scanner(System.`in`)
    print("请输入每个人的手牌数：")
    val cardNumber: Int = input.nextInt()
    val gameRule = Poker()
    val player = Player(cardNumber)
    val computer = Player(cardNumber)

    //主运行
    for (i in 1..cardNumber) {
        player.display()
        player.playerOutOfCard()
        computer.computerOutOfCard()
        if (gameRule.myGetValue(player.card) > gameRule.myGetValue(computer.card)){
            println("${player.card}  ${computer.card}")
            println("${player.card} > ${computer.card}")
            player.score += 1
        }else{
            println("${player.card}  ${computer.card}")
            println("${player.card} < ${computer.card}")
            computer.score += 1
        }
    }
    println("\n游戏结束\n比分为：${player.score}  ${computer.score}")
}