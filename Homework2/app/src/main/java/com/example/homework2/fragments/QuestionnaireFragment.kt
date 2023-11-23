package com.example.homework2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.homework2.BaseFragment
import com.example.homework2.Helper
import com.example.homework2.R
import com.example.homework2.databinding.FragmentQuestionnaireBinding
import com.example.homework2.models.QuestionData

class QuestionnaireFragment : BaseFragment(R.layout.fragment_questionnaire) {

    private var viewBinding: FragmentQuestionnaireBinding? = null
    private var questions: MutableList<QuestionData> = getQuestionsData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentQuestionnaireBinding.inflate(inflater)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val position = arguments?.getInt("position") ?: 0
        val count = arguments?.getInt("count") ?: 0

        setQuestions(position, count)

        initListeners(position)
        viewBinding?.clickMeBtn?.isClickable = Helper.answerCheckedMutableList.size == count
    }

    private fun initListeners(position: Int) {
        val rbMutableList = getRadioButtons()
        rbMutableList.forEach {
            it?.setOnCheckedChangeListener { compoundButton, b ->
                Helper.answerCheckedMutableList.set(position, it.id)
                it.isEnabled = !it.isChecked
                viewBinding?.clickMeBtn?.apply {
                    val flag = Helper.isAllAns()
                    if (flag) {
                        visibility = View.VISIBLE
                        isClickable = true
                    }
                }
            }
        }

        viewBinding?.clickMeBtn?.setOnClickListener {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }


    }

    private fun setQuestions(position: Int, count: Int) {
        val lengthList = questions.size
        val question = position.let { questions[it % lengthList] }

        val fields = (question.javaClass.declaredFields).toMutableList()
        fields.removeIf {
            it.name == "question"
        }
        val textViews = getRadioButtons()

        var i = 0
        while (i < fields.size && i < textViews.size) {
            if (!fields[i].isAccessible) {
                fields[i].isAccessible = true
            }
            textViews[i]?.text = fields[i].get(question)?.toString()
            i += 1
        }

        viewBinding?.apply {
            titleTv.text = "Answer ${position.plus(1)}"
            questionTv.text = question.question
            if (position == count - 1) {
                clickMeBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun getRadioButtons(): MutableList<RadioButton?> {
        return mutableListOf(
            viewBinding?.fifthAnsRb,
            viewBinding?.firstAnsRb,
            viewBinding?.fourthAnsRb,
            viewBinding?.secondAnsRb,
            viewBinding?.thirdAnsRb,
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    private fun getQuestionsData(): MutableList<QuestionData> {
        return mutableListOf(
            QuestionData(
                "What is the capital of France?",
                "Paris",
                "Madrid",
                "London",
                "Rome",
                "Berlin",
            ),
            QuestionData(
                "What is the largest planet in our solar system?",
                "Jupiter",
                "Mars",
                "Venus",
                "Saturn",
                "Uranus",
            ),

            QuestionData(
                "Who painted the Mona Lisa?",
                "Leonardo da Vinci",
                "Pablo Picasso",
                "Vincent van Gogh",
                "Claude Monet",
                "Michelangelo",
            ),

            QuestionData(
                "What is the chemical symbol for gold?",
                "Au",
                "Ag",
                "Gd",
                "Pb",
                "Fe",
            ),

            QuestionData(
                "What year was the first iPhone released?",
                "2007",
                "2005",
                "2010",
                "2012",
                "2009",
            ),

            QuestionData(
                "Which country won the 2018 FIFA World Cup?",
                "France",
                "Germany",
                "Spain",
                "Brazil",
                "Argentina",
            ),

            QuestionData(
                "Who wrote the novel 'Pride and Prejudice'?",
                "Jane Austen",
                "Mark Twain",
                "F. Scott Fitzgerald",
                "Charlotte Brontë",
                "Charles Dickens",
            ),

            QuestionData(
                "What is the tallest mountain in the world?",
                "Mount Everest",
                "K2",
                "Kilimanjaro",
                "Matterhorn",
                "Mount Fuji",
            ),

            QuestionData(
                "What is the chemical symbol for water?",
                "H2O",
                "CO2",
                "NaCl",
                "O2",
                "CaCO3",
            ),

            QuestionData(
                "Who is the lead vocalist of the band 'Coldplay'?",
                "Chris Martin",
                "Adam Levine",
                "Bono",
                "Thom Yorke",
                "Dave Grohl",
            ),

            QuestionData(
                "What is the square root of 144?",
                "12",
                "9",
                "15",
                "10",
                "13",
            ),

            QuestionData(
                "Which planet is known as the 'Red Planet'?",
                "Mars",
                "Jupiter",
                "Venus",
                "Mercury",
                "Saturn",
            ),

            QuestionData(
                "Who is the current President of the United States?",
                "Joe Biden",
                "Donald Trump",
                "Barack Obama",
                "George W. Bush",
                "Bill Clinton",
            ),

            QuestionData(
                "What is the largest ocean on Earth?",
                "Pacific Ocean",
                "Indian Ocean",
                "Atlantic Ocean",
                "Arctic Ocean",
                "Southern Ocean",
            ),

            QuestionData(
                "Which country is famous for its tulips and windmills?",
                "Netherlands",
                "Italy",
                "Australia",
                "Canada",
                "Mexico",
            )
        )
    }


    companion object {
        fun newInstance(position: Int, count: Int) = QuestionnaireFragment().apply {
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putInt("count", count)
            arguments = bundle
        }
    }

}