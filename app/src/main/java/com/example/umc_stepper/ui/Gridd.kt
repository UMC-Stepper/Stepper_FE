package com.example.umc_stepper.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseActivity
import com.example.umc_stepper.databinding.ActivityGriddBinding

class Gridd : BaseActivity<ActivityGriddBinding>(R.layout.activity_gridd) {

    private var message: String = ""

    override fun setLayout() {
        setGrid()
    }

    private fun setGrid() {


        // 각 버튼에 onClickListener 추가
        findViewById<AppCompatButton>(R.id.button1).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "아리")
            go("아리")
            intent.putExtra("message", message)
            startActivity(intent)
        }

        findViewById<AppCompatButton>(R.id.button2).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "루피")
            go("루피")
            intent.putExtra("message", message)
            startActivity(intent)
        }

        findViewById<AppCompatButton>(R.id.button3).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "빈트")
            go("빈트")
            intent.putExtra("message", message)
            startActivity(intent)

        }

        findViewById<AppCompatButton>(R.id.button4).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "채리")
            go("채리")
            intent.putExtra("message", message)
            startActivity(intent)

        }

        findViewById<AppCompatButton>(R.id.button5).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "예니")
            go("예니")
            intent.putExtra("message", message)
            startActivity(intent)
        }

        findViewById<AppCompatButton>(R.id.button6).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "미니")
            go("미니")
            intent.putExtra("message", message)
            startActivity(intent)
        }

        findViewById<AppCompatButton>(R.id.button7).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "마야")
            go("마야")
            intent.putExtra("message", message)
            startActivity(intent)
        }

        findViewById<AppCompatButton>(R.id.button8).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "호준")
            go("호준")
            intent.putExtra("message", message)
            startActivity(intent)

        }

        findViewById<AppCompatButton>(R.id.button9).setOnClickListener {
            val intent = Intent(this@Gridd, ResultActivity::class.java)
            intent.putExtra("name", "비모")
            go("비모")
            intent.putExtra("message", message)
            startActivity(intent)

        }


    }


    private fun go(s: String) {
        when (s) {
            "루피" -> {
                message ="예원님께,\n예원님 안녕하세요~ 프론트 엔드 헤더로 이번 프로젝트 진행했던 박지원입니다.\n글씨가,,악필이어서!! 텍스트로 쓰게 된 점 양해부탁드립니다ㅠㅠ\n프로젝트 내내 계속 프론트 백 챙기시고, 저희 한테 힘든 거 해주셔서 감사하다고 무한 칭찬 해주시는 것에\n힘입어 더 열심히 해야지 하는 마음이 생겼던 것 같아요. 물론, 모두가 열심히 해주셨지만 오늘의 \n기획을 위해 4-5개월간 힘쓴 예원님도 그만큼 열심히 하셨기 때문에 매번 백, 프론트 분들이 \n잠도 못자고 너무 열심히 해주셔서 죄송하다는 말이 너무나도 감사했던 한 편, 그러지 않으셔도 된다고 \n생각하였던 것 같습니다. 그리고, 저희도 매번 맞지 않는다고 하셨던 PM의 역할을 완벽하게 해주신 예원님께 \n너무 감사드리고, 멋진 기획을 망치지 않도록 하기위해 다같이 더 노력했던 것 같아요. \n옆에서 힘들어 하시는 모습 전부 지켜보고 힘 되시라고 몇 마디 드렸었는데, 잘난 것 없는 저한테 되게 많이 \n도움 받았다고 말씀해주셔서 더 감사했었습니다. \n정말 많이 배운 프로젝트 였던 것 같아요. 정말 팀원 분들 모두 너무 좋은 분들 이었고, 예원님도 기획 별로라고 \n막 욕하셨지만, 정말 재밌고 가치있다고 생각하며 진행했던 프로젝트 였고, \n이렇게 프로젝트 잘 마무리할 수 있게 옆에서 많이 도와주셔서 감사했습니다. \n열정을 쏟아부었던 프로젝트인 만큼 끝나고도 여운이 많이 남을 것 같아요. \n제가 본 예원님은 본인이 정한 것은 어찌됐든 확실하게 끝내는 사람..(다이어트, 프로젝트 모두에 있어..), \n배려심 깊고, 책임감이 강한사람.. \n앞으로도 예원님이 정한 선택이 있다면 항상 자신을 가지고 조금이라도 맞다고 생각하는 것에 확신하며 \n진행한다면 어떤 것이든 성공할 것이라고 생각합니다. 그럼 이만 줄이겠습니다. 그동안 정말 고생많으셨고, 감사했습니다. 끝나고도 다같이 친하게 지냈으면 좋겠네요~ ㅋㅋㅋㅋㅋㅋ 감사했습니다~ \n"

            }

            "아리" -> {
                message = ""
            }

            "비모" -> {
                message = "두 달 동안 함께 프로젝트 할 수 있어서 너무 좋았어요 예원님 덕분에 잘 끝낼 수 있었어요 감사합니다☺ 다음에 학교에서 밥 먹어요!"
            }

            "빈트" -> {
                message = "안녕하세요! 경빈입니다!!\uD83D\uDE0A\n" +
                        "\n" +
                        "두 달간의 UMC 여정 동안 정말 고생 많으셨습니다. 많은 고민과 노력이 있었음을 옆에서 지켜보며 느낄 수 있었고, 그만큼 저희 팀에게도 큰 힘이 되었습니다!\n" +
                        "\n" +
                        "비록 다 같이 마무리 회식에 참석하지 못하셔서 아쉬운 마음이 큽니다만, 그동안의 헌신과 노력에 진심으로 감사드립니당. 개발자 입장에서 PM님의 요구사항을 최대한 다 넣으려구 노력했는데, 그 노력을 너무 잘 봐주시고 칭찬해주셔서 감동이였습니다 ㅠㅠ\n" +
                        "\n" +
                        "좋은 팀을 만나 함께 프로젝트를 완수할 수 있어 행복했고, 그 중심에 계셨던 PM님 덕분에 더욱 뜻깊은 경험이 되었습니다!\n" +
                        "\n" +
                        "다음에 기회가 된다면 꼭 다시 뵈용!!☺"
            }

            "채리" -> {
                message = ""
            }

            "마야" -> {
                message = "예원님 멋진 아이디어로 프로젝트 완성할 수 있어서 너무 좋았습니다! 프로젝트 진행하면서 너무 고생해주시고 이것저것 신경쓰느라 힘드셨을텐데 끝까지 프로젝트 잘 이끌어주셔서 감사해요ㅎㅎ! 정말 수고 많으셨습니다 예원님 덕분이에요! 예원님 최고예여 우리 PM 짱~~"
            }

            "호준" -> {
                message = "예원님 안녕하세요! 호준입니다 같이 회식할줄 알았는데 못해서 너무 아쉽네요..예원없는예원팀이 되어버렸네요...저희 그래도 아이디어톤에서부터 꽤 오래 뵀네요 저한테 첫 프로젝트의 pm님이셨는데 너무 완벽하게 여기까지 팀원들 이끌어주셔서 너무 수고 많으셨습니다 예원님은 리더십이 되게 좋으신분 같아요 저희 pm이 예원님이었기에 이렇게까지 순조롭게 잘 진행되지 않았을까 생각해요 비록 pm은 더 이상 안하신다 하셨지만, 앞으로 하시는 일마다 잘되시길 바라겠습니다! 지금까지 수고 많으셨습니다 스태퍼 화이팅!"
            }

            "미니" -> {
                message = ""
            }

            else -> {
                message = "자랑스러운 스테퍼 PM"
            }
        }
    }
}